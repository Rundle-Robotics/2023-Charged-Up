package frc.robot.commands;
import frc.robot.subsystems.GrabberLifter;
import edu.wpi.first.wpilibj2.command.CommandBase;
public class GrabberLifterCommand extends CommandBase {
    private final GrabberLifter m_GrabberLifter;
  
    public GrabberLifterCommand(GrabberLifter subsystem) {
  
      m_GrabberLifter = subsystem;
      
  
      //addRequirements(m_Drivetrain);
  
      // Use addRequirements() here to declare subsystem dependencies
      
    }
  
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
  
      m_GrabberLifter.lift(true);
  
  
    }
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
  
  
  
  
    }
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
  
      m_GrabberLifter.lift(false);
  
    }
}

