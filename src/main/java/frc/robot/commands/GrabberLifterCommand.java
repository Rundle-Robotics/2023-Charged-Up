package frc.robot.commands;
import frc.robot.subsystems.GrabberLifter;
import frc.robot.subsystems.Pneumatics;

import java.security.spec.MGF1ParameterSpec;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj2.command.CommandBase;
public class GrabberLifterCommand extends CommandBase {
    private final GrabberLifter m_GrabberLifter;
    private final Pneumatics m_Pneumatics;
    private double Speed;
  
    public GrabberLifterCommand(double speed, GrabberLifter subsystem, Pneumatics pneumatics) {
  
      m_GrabberLifter = subsystem;
      m_Pneumatics = pneumatics;
      Speed = speed;
      
  
      addRequirements(subsystem);
      //addRequirements(m_Drivetrain);
  
      // Use addRequirements() here to declare subsystem dependencies
      
    }
  
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {

    //can't move and lifter isn't main obstacle
    if (m_GrabberLifter.stopArm(Speed) && !m_GrabberLifter.lifterNeedsLowering(Speed))
    {
      Speed = 0;
    }
    //lifter is main reason for why arm can't move. Drop lifter
    else if (m_GrabberLifter.lifterNeedsLowering(Speed))
    {
      m_Pneumatics.toggleGrabberSolenoid();
    }

    
    m_GrabberLifter.lift(Speed);
  
  
    }
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

      //sub category of stopArm where lifter can be auto raised
      if (m_GrabberLifter.lifterNeedsRaising(Speed))
      {
        m_Pneumatics.toggleLifter();
      }
      //condition where arm has hit limit and needs to be stopped
      else if (m_GrabberLifter.stopArm(Speed))
      {
        Speed = 0;
        m_GrabberLifter.lift(Speed);
      }
  
    }
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
  
    }
    @Override
    public boolean isFinished() {
      //terminate early if speed set to zero, else wait till button press interruption
        return Speed == 0;
    }
}

