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

	private final double CENTER_DISTANCE = 1;
	private final double TARGET_AREA_CUTOFF = 10;

	double rotation = 0;
	double speed = 0;

	public LimelightFollow(Drivetrain drivetrain, Limelight limelight) {
		this.drivetrain = drivetrain;
		this.limelight = limelight;

		addRequirements(drivetrain);
		addRequirements(limelight);
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
		Limelight.enableLimelight();
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		if (limelight.getTV() == 0) {
			System.out.println("No target found, trying to turn and find one..."); // debug
			drivetrain.setSpeeds(0, 0.25);
		} else {
			System.out.println("Target found");
			// If target is on the right, turn right
			if (limelight.getTX() > CENTER_DISTANCE) {
				System.out.println("Target on the right, trying to turn..."); // debug
				drivetrain.setSpeeds(0, 0.25);
			}
			// If target is on the left, turn left
			else if (limelight.getTX() < -CENTER_DISTANCE) {
				System.out.println("Target on the left, trying to turn..."); // debug
				drivetrain.setSpeeds(0, -0.25);
			}
			// If target area is too small, move forward
			else if (limelight.getTA() < TARGET_AREA_CUTOFF) {
				System.out.println("Target too far, trying to move forward..."); // debug
				drivetrain.setSpeeds(0.25, 0);
			}
		}
		System.out.println(); // debug
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
		limelight.disableLimelight();
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		return false;
	}

	/**
	 * This function is the same as calling Math.max(Math.min(value, max), min);
	 * 
	 * @param value the value to limit
	 * @param min   the minimum permissible value
	 * @param max   the maximum permissible value
	 * @return the value limited by the given constraints
	 */
	@SuppressWarnings("unused")
	private double cap(double value, double min, double max) {
		if (value < min) {
			return min;
		} else if (value > max) {
			return max;
		} else {
			return value;
		}
	}
}
