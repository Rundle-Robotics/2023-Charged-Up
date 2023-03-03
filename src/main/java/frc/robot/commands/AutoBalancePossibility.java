package frc.robot.commands;

import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.NAVX;
import edu.wpi.first.math.controller.PIDController;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoBalancePossibility extends CommandBase {

	private Drivetrain drivetrain;
	private NAVX navx;

	private PIDController backUpOnStationPID;

	private static final double DISTANCE = 20;
	private static final double MAX_SPEED = 0.3;
	private static final double ROLL_DEADBAND = 9;

	private boolean backingUp;

	/** Creates a new AutoBalanceNAvX. */
	public AutoBalancePossibility(Drivetrain drivetrain, NAVX navx) {
		this.drivetrain = drivetrain;
		this.navx = navx;

		addRequirements(drivetrain);
		addRequirements(navx);
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
		backUpOnStationPID = new PIDController(0.05, 0, 0);
		backUpOnStationPID.setTolerance(2); // 2 ticks (1/21 of a rotation)
		backUpOnStationPID.setSetpoint(DISTANCE);

		backingUp = true;
	}

	@Override
	public void execute() {
		// Check if we hit the charge station (and should stop backing up)
		if (Math.abs(navx.getRoll()) > ROLL_DEADBAND) {
			backingUp = false;
		}

		if (backingUp) {
			drivetrain.mecanumDrive(0, 0.3, 0);
		} else if (!backUpOnStationPID.atSetpoint()) {
			double currentPosition = (drivetrain.getFrontLeftPosition() + drivetrain.getFrontRightPosition()) / 2;
			double output = backUpOnStationPID.calculate(currentPosition);

			if (output > MAX_SPEED) {
				output = MAX_SPEED;
			} else if (output < -MAX_SPEED) {
				output = -MAX_SPEED;
			}

			drivetrain.mecanumDrive(0, output, 0);
		}
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
		drivetrain.mecanumDrive(0, 0, 0);
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		return backUpOnStationPID.atSetpoint();
	}
}

// Andrew's old execute code:
// Damian look at this code too, possibly finish the code lower too, not
// finished atm., this 1 is a placeholder value. ishan wants a lock on the lower
// code.
// Andrew what is this
// if (navx.getRoll()>9) {
//
// new BackupAutoMove(1, drive);
// if (navx.getRoll()<9){
// new BackupAutoMove(1, drive);
// if (navx.getRoll()<9){
// new BackupAutoMove(1, drive);
// if (navx.getRoll()<9){
// new BackupAutoMove(1, drive);
// if (navx.getRoll()<9){
// new BackupAutoMove(1, drive);
// }}}else {
// finite = true;
// }
// }

// }

// if (Math.abs(roll) > 9) {
// setpoint = currentPosition + 5;
// output = -.25;
// tracking = true;
// } else {
// output = 0;
// finite = true;
// }

// drive.mecanumDrive(0, output, 0);