// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.Pneumatics;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

public class RobotContainer {
	public static final CommandXboxController driverController = new CommandXboxController(
			OperatorConstants.DRIVER_CONTROLLER_PORT);

	// Subsystems
	private static Compressor compressor;
	private final Pneumatics pneumatics;

	public RobotContainer() {
		compressor = new Compressor(PneumaticsModuleType.REVPH);
        compressor.enableDigital();

		pneumatics = new Pneumatics();
		
		// Configure the trigger bindings
		configureBindings();
	}

	private void configureBindings() {
		// Bind A button to climber solenoid (toggle)
		driverController.a().onTrue(pneumatics.toggleGrabberSolenoid());
	}

	public Command getAutonomousCommand() {
		return null;
	}
}
