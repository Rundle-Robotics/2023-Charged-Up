// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.MecanumSubsystem;
import frc.robot.subsystems.Limelight;


public class LimelightFollow extends CommandBase {
	/** Creates a new LimelightFollow. */

	private MecanumSubsystem meca;
	private Limelight limelight;

	private final double CENTER_DISTANCE = 1;
	private final double TARGET_AREA_CUTOFF = 10;

	double rotation = 0;
	double speed = 0;

	boolean closeToTargeta;
	boolean finite;

	public LimelightFollow(MecanumSubsystem meca, Limelight limelight) {
		this.meca = meca;
		this.limelight = limelight;

		

		addRequirements(meca);
		addRequirements(limelight);
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
		Limelight.enableLimelight();

		limelight.setPipeline(0); // Pipeline 0 is for AprilTag detection

		finite = false;
		
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		
		
			if (limelight.getTV() == 0) {
				System.out.println("No target found, trying to turn and find one..."); // debug
				// drivetrain.setSpeeds(0, 0.25);
				meca.setSpeeds(0, 0, 0.6, 0);
			} else {
				System.out.println("Target found");
				// If target is on the right, turn right
				if (limelight.getTX() > CENTER_DISTANCE) {
					System.out.println("Target on the right, trying to turn..."); // debug
					meca.setSpeeds(0,-0.5, 0, 0);
				}
				// If target is on the left, turn left
				else if (limelight.getTX() < -CENTER_DISTANCE) {
					System.out.println("Target on the left, trying to turn..."); // debug
					meca.setSpeeds(0,0.5, 0, 0);
				}
				// If target area is too small, move forward
				else if (limelight.getTA() < TARGET_AREA_CUTOFF) {
					System.out.println("Target too far, trying to move forward..."); // debug
					meca.setSpeeds(-0.5, 0, 0, 0);
				}
//Values are placeholders at the moment. 
			

		}

		System.out.println("DONE CENTEING"); // debug
	}
//why is there an error here---

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
		limelight.disableLimelight();
		
		limelight.setPipeline(2); // Pipeline 2 is for driver vision
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		boolean hasTarget = limelight.getTV() != 0;
		boolean isCentered = Math.abs(limelight.getTX()) < CENTER_DISTANCE;
		boolean isCloseEnough = limelight.getTA() > TARGET_AREA_CUTOFF;
		return hasTarget && isCentered && isCloseEnough;
		
	}

	
	
}
