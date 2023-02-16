package frc.robot.commands;
import frc.robot.subsystems.GrabberLifter;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
public class GrabberLifterCommand extends CommandBase {
    private final GrabberLifter m_GrabberLifter;
    private double Speed;
  
    public GrabberLifterCommand(double speed, GrabberLifter subsystem, boolean tof) {
  
      m_GrabberLifter = subsystem;
      Speed = speed;

      if (tof) {Speed = -Speed;} 
      
  
      //addRequirements(m_Drivetrain);
  
      // Use addRequirements() here to declare subsystem dependencies
      
    }
  
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
  
      if (m_GrabberLifter.getTopSwitch() && Speed > 0)
      {
        Speed = 0;
      } 
      else if (m_GrabberLifter.getMiddleSwitch() && Speed < 0)
      {
        System.out.println("middle switch stop");
        Speed = 0;
      }
      else if (!m_GrabberLifter.getBottomSwitch() && m_GrabberLifter.getMiddleSwitch())
      {
        Speed = 0;
      }
      m_GrabberLifter.lift(Speed);

  
  
    }

  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
  
    }
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
  
    }
    @Override
    public boolean isFinished() {
        return true;
    }
}

