package frc.robot.commands;

import frc.robot.subsystems.GrabberLifter;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class GrabberLifterCommand extends CommandBase {
  private final GrabberLifter m_GrabberLifter;
  private double speed;

  public GrabberLifterCommand(double speed, GrabberLifter subsystem, boolean tof) {

    m_GrabberLifter = subsystem;
    speed = this.speed;

    if (tof) {
      speed = -speed;
    }

    addRequirements(subsystem);

    // Use addRequirements() here to declare subsystem dependencies

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_GrabberLifter.lift(speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_GrabberLifter.lift(0);
  }

  @Override
  public boolean isFinished() {
    boolean top = m_GrabberLifter.getTopSwitch() && speed > 0;
    boolean middle = m_GrabberLifter.getMiddleSwitch() && speed < 0;
    boolean bottom = !m_GrabberLifter.getBottomSwitch() && m_GrabberLifter.getMiddleSwitch();
    return top || middle || bottom;
  }
}
