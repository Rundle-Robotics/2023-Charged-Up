// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.Drivetrain; 

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
	// Replace with CommandPS4Controller or CommandJoystick if needed
	private final CommandXboxController driverController = new CommandXboxController(
			OperatorConstants.DRIVER_CONTROLLER_PORT);

		
	/*
	 * The bracket below is causing problems
	 * How can I tell? 
	 * 1) All the errors start directly below that line
	 * 2) Clicking on it highlights the RobotContainer bracket in line 22
	 * 3) the color matchs the bracket in line 22, and there are no brackets between it could be closing off
	 * 
	 * The RobotContainer bracket should encompass all of the code! After all, it is the class we are going to initialize in Robot.java
	 * 
	 * Therefore, this bracket should be deleted. Doing so removes all the errors (aside from the second set of comments below)
	 * 
	 * If you ever have bracket errors, look at where the errors start, the color, and the paried bracket when clicked on
	 */


	}	// THIS BRACKET SHOULD NOT BE HERE AAAHHHH
	// The robot's subsystems and commands are defined here...
	private final Drivetrain drivetrain = new Drivetrain(driverController);


	/**
	 * The container for the robot. Contains subsystems, OI devices, and commands.
	 */
	public RobotContainer() {
		// Configure the trigger bindings
		configureBindings();


		/*
		 * You haven't defined the kYButton variable. What button do we want? We want the Y button of the controller
		 * Do we have the controller? Yes - driverController!
		 * 
		 * Now, you imported the CommandXboxController class, not the XboxController class. The CommandXboxController lets you tie commands to buttons. 
		 * If you just want to change a variable (finetuned) to true, we only want to check the button status, not tie it to a command
		 * 
		 * To check status, we can't use a CommandXboxController object, we need an ordinary XboxController object.
		 * 
		 * Looking at the documentation (https://first.wpi.edu/wpilib/allwpilib/docs/release/java/edu/wpi/first/wpilibj2/command/button/CommandXboxController.html),
		 * the .getHID() command returns the XboxController that this is based off
		 * 
		 * so doing driverController.getHID() gives us an XboxController object we can poll the status for
		 * 
		 * to get the Y button of an XboxController Object? Check the documentation. It's .getYButton()
		 * 
		 * Therefore, in this case, as you don't want to tie an explicit command to the Y button, and just poll its status,
		 * we can do driverController.getHID().getYButton()
		 * 
		 * we can also avoid this mess by changing the object type of driverController to XboxController instead of CommandXboxController.
		 */

		if (kYButton = true) {
		boolean finetuned = true;

		}


	}



	/**
	 * Use this method to define your trigger->command mappings. Triggers can be
	 * created via the
	 * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with
	 * an arbitrary
	 * predicate, or via the named factories in {@link
	 * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
	 * {@link
	 * CommandXboxController
	 * Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
	 * PS4} controllers or
	 * {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
	 * joysticks}.
	 */
	private void configureBindings() {
		// Example: Schedule `ExampleCommand` when `exampleCondition` changes to `true`
		// new Trigger(drivetrain::exampleCondition)
		// .onTrue(new ExampleCommand(m_exampleSubsystem));

		// Example: Schedule `exampleMethodCommand` when the Xbox controller's B button
		// is
		// pressed,
		// cancelling on release.
		// m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());
	}

	/**
	 * Use this to pass the autonomous command to the main {@link Robot} class.
	 *
	 * @return the command to run in autonomous
	 */
	public Command getAutonomousCommand() {
		return null;
	}
}
