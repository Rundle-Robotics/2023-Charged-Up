// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;

public class LimelightFollow extends CommandBase {
	/** Creates a new LimelightFollow. */

	private Drivetrain drivetrain;
	private Limelight limelight;

	private final double CENTER_DISTANCE = -10.2;
	private final double TARGET_AREA_CUTOFF = 1.2;
	private final double CENTER_DEADBAND = 5;
	private final double YAW_DEADBAND = 10;
	private final double TARGET_YAW = 0;
	private final double SPEED = 0.45;

	double rotation = 0;
	double speed = 0;

	boolean finite;

	public LimelightFollow(Drivetrain drivetrain, Limelight limelight) {
		this.drivetrain = drivetrain;
		this.limelight = limelight;

		addRequirements(drivetrain);
		addRequirements(limelight);
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
		limelight.enableLimelight();

		limelight.setPipeline(0); // Pipeline 0 is for AprilTag detection

		finite = false;

	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		limelight.putTargetPoseDataonSmartDashboard();

		if (limelight.getTV() == 0) {
			System.out.println("No target found, trying to turn and find one..."); // debug
			drivetrain.mecanumDrive(0, 0, SPEED);

		} else {
			System.out.println("Target found");

			double yaw = limelight.getTARGETPOSECAMERA()[5];

			boolean targetOnRight = limelight.getTX() > (CENTER_DISTANCE + CENTER_DEADBAND);
			boolean targetOnLeft = limelight.getTX() < (CENTER_DISTANCE - CENTER_DEADBAND);
			boolean targetTooFar = limelight.getTA() < TARGET_AREA_CUTOFF;
			boolean targetSkewed = Math.abs(yaw) > YAW_DEADBAND;

			// Positive forwardSpeed to move forward,
			double forwardSpeed = targetTooFar ? SPEED : 0;
			// Positive strafeSpeed to move left
			double strafeSpeed = targetOnRight ? -SPEED : targetOnLeft ? SPEED : 0;
			// Positive rotation to turn clockwise
			double rotationSpeed = targetSkewed ? (yaw - TARGET_YAW) / Math.abs(yaw - TARGET_YAW) * SPEED : 0;

			drivetrain.mecanumDrive(-strafeSpeed, forwardSpeed, -rotationSpeed);
			finite = !targetOnLeft && !targetOnRight && !targetTooFar && !targetSkewed;

			System.out.print("Target detected on right: " + targetOnRight);
			System.out.print("Target detected on left: " + targetOnLeft);
			System.out.print("Target detected too far: " + targetTooFar);
			System.out.print("Target detected at a skew: " + targetSkewed);

		}

		System.out.println(); // debug
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
		limelight.disableLimelight();

		limelight.setPipeline(2); // Pipeline 2 is for driver vision
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		return finite;
	}

}
