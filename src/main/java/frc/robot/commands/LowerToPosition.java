// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.GrabberLifter;
import frc.robot.subsystems.Pneumatics;

public class LowerToPosition extends CommandBase {
	/** Creates a new LowerToPosition. */

	private GrabberLifter grabberLifter;
	private Pneumatics pneumatics;

	public LowerToPosition(GrabberLifter grabberLifter, Pneumatics pneumatics) {
		this.grabberLifter = grabberLifter;
		this.pneumatics = pneumatics;

		addRequirements(grabberLifter);
		addRequirements(pneumatics);
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		grabberLifter.lift(-0.2);
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
		if (!interrupted) {
			pneumatics.toggleLifter();
		}
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		// Might need to be .getMiddleSwitch()
		return grabberLifter.getBottomSwitch() || grabberLifter.stopArm(-0.2);
	}
}
