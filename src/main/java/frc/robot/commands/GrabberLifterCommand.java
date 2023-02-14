package frc.robot.commands;
import frc.robot.subsystems.GrabberLifter;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.Switch;
public class GrabberLifterCommand extends CommandBase {
    private final GrabberLifter m_GrabberLifter;
    private double Speed;
  
    public GrabberLifterCommand(double speed, GrabberLifter subsystem) {
  
      m_GrabberLifter = subsystem;
      Speed = speed;
      
  
      //addRequirements(m_Drivetrain);
  
      // Use addRequirements() here to declare subsystem dependencies
      
    }
  
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
  
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

