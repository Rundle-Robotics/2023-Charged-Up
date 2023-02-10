package frc.robot.commands;
import frc.robot.subsystems.GrabberLifter;
import edu.wpi.first.wpilibj2.command.CommandBase;
public class AutoLifterCommand extends CommandBase {
    private final GrabberLifter m_GrabberLifter;
    private double Height;
    private double Position;
    private boolean finished;
  
    public AutoLifterCommand(double height, GrabberLifter subsystem) {
  
      m_GrabberLifter = subsystem;
      Height = height;
      finished = false;

      
  
      //addRequirements(m_Drivetrain);
  
      // Use addRequirements() here to declare subsystem dependencies
      
    }
  
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        if (m_GrabberLifter.getPosOfLift() < Height) {
            m_GrabberLifter.lift(0.3);
        }
        else{
            m_GrabberLifter.lift(-0.3);
        }
  
      

  
  
    }
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        Position = m_GrabberLifter.getPosOfLift();
        if(Position > (Height - 10) && Position < (Height+10)) {
            m_GrabberLifter.lift(0);
            finished = true;
        }
  
  
  
  
    }
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
  
    }
    @Override
    public boolean isFinished() {
        return finished;
    }
}

