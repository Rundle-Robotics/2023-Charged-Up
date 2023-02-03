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
		if (limelight.getTV() != 0) {
			if (Math.abs(limelight.getTX()) > 1) { // TX distance from center > 1, want to center
				System.out.println("Trying to center...");
				rotation = cap(-limelight.getTX(), -0.3, 0.3);
			}
			if (limelight.getTA() < 10) { // TA too small, move forward
				System.out.println("Trying to move forward...");
				speed = cap(limelight.getTA(), 0, 0.3);
			}
			drivetrain.setSpeeds(speed, rotation);
		} else {
			drivetrain.setSpeeds(0, 0.3);
		}
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
