package frc.robot.commands;

import frc.robot.subsystems.GrabberLifter;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class GetClosePosition extends CommandBase {
	private final GrabberLifter m_GrabberLifter;
	private final double lowHeight;
	private final double midHeight;
	private final double highHeight;
	private double position;
	private boolean finished;
	private double speed;

	public GetClosePosition(GrabberLifter subsystem) {
		m_GrabberLifter = subsystem;
		lowHeight = 200;
		midHeight = 400;
		highHeight = 600;
		position = 0;
		finished = false;
		speed = 0;

		addRequirements(subsystem);
	}

	@Override
	public void initialize() {
		position = m_GrabberLifter.getPosOfLift();
		if (highHeight < position) {
			speed = -0.3;
		} else if (midHeight < position) {
			if ((position - midHeight) < (highHeight - position)) {
				speed = -0.3;
			} else {
				speed = 0.3;
			}
		} else if (lowHeight < position && midHeight > position) {
			if ((position - lowHeight) < (midHeight - position)) {
				speed = -0.3;
			} else {
				speed = 0.3;
			}
		} else {
			speed = 0.3;
		}
		m_GrabberLifter.lift(speed);

	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		position = m_GrabberLifter.getPosOfLift();
		if (position >= lowHeight - 10 && position <= lowHeight + 10
				|| position >= midHeight - 10 && position <= midHeight + 10
				|| position >= highHeight - 10 && position <= highHeight + 10) {
			m_GrabberLifter.lift(0);
			finished = true;
		}
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
		m_GrabberLifter.lift(0); //failsafe
	}

	@Override
	public boolean isFinished() {
		return finished || m_GrabberLifter.stopArm(speed);
	}
}
