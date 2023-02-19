package frc.robot.auto;
import frc.robot.subsystems.GrabberLifter;
import edu.wpi.first.wpilibj2.command.CommandBase;
public class AutoLifterCommand extends CommandBase {
    private final GrabberLifter m_GrabberLifter;
    private double Height;
    private double Position;
    private boolean finished;

    private double speed;

    public AutoLifterCommand(double height, GrabberLifter subsystem) {

      m_GrabberLifter = subsystem;
      Height = height;
      finished = false;

        addRequirements(subsystem);


      //addRequirements(m_Drivetrain);

      // Use addRequirements() here to declare subsystem dependencies

    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        if (m_GrabberLifter.getPosOfLift() <= Height) {
            speed = 0.3;
        }
        else{
            speed = -0.3;
        }


        m_GrabberLifter.lift(speed);

    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        Position = m_GrabberLifter.getPosOfLift();
        if(Position > (Height - 10) && Position < (Height+10)) {
            finished = true;
        }

    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_GrabberLifter.lift(0);
    }
    @Override
    public boolean isFinished() {
        return finished || m_GrabberLifter.stopArm(speed);
    }
}