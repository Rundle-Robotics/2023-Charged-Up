package frc.robot.commands;

import frc.robot.subsystems.GrabberLifter;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class GrabberLifterCommand extends CommandBase {
  private final GrabberLifter m_GrabberLifter;
  private double speed2;
  private boolean tof2;

  public GrabberLifterCommand(double speed, GrabberLifter subsystem, boolean tof) {

    m_GrabberLifter = subsystem;
    speed2 = speed;
    tof2 = tof;

    if (tof2) {
      speed2 = -speed;
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

    m_GrabberLifter.lift(speed2);

    SmartDashboard.putBoolean("middle", m_GrabberLifter.getMiddleSwitch());
    SmartDashboard.putBoolean("bottom", m_GrabberLifter.getBottomSwitch());
    SmartDashboard.putBoolean("top", m_GrabberLifter.getTopSwitch());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_GrabberLifter.lift(0);
  }

  @Override
  public boolean isFinished() {
    return m_GrabberLifter.stopArm(speed2);
  }
}
