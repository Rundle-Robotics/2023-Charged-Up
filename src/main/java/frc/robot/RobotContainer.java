// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.GetClosePosition;
import frc.robot.commands.GrabberLifterCommand;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.GrabberLifter;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.FineTUNECommand;
import frc.robot.subsystems.NAVX;

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
	public static final CommandXboxController driverController = new CommandXboxController(
			OperatorConstants.DRIVER_CONTROLLER_PORT);
	private final XboxController controller  = driverController.getHID();
	


	// The robot's subsystems and commands are defined here...
	private final GrabberLifter grabberLifter = new GrabberLifter();
	private final Drivetrain drivetrain = new Drivetrain();

	//declared NAVX
	public final NAVX navx = new NAVX();


	/**
	 * The container for the robot. Contains subsystems, OI devices, and commands.
	 */
	public RobotContainer() {
		// Configure the trigger bindings
		configureBindings();
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
		driverController.y().onTrue(new GrabberLifterCommand(0.4, grabberLifter)).onFalse(new GetClosePosition(grabberLifter));

		driverController.a().onTrue(new GrabberLifterCommand(-0.4, grabberLifter)).onFalse(new GetClosePosition(grabberLifter));

		driverController.povUp().onTrue(new GrabberLifterCommand(0.2, grabberLifter)).onFalse(new GrabberLifterCommand(0, grabberLifter));

		driverController.povDown().onTrue(new GrabberLifterCommand(-0.2, grabberLifter)).onFalse(new GrabberLifterCommand(0, grabberLifter));
		// Example: Schedule `ExampleCommand` when `exampleCondition` changes to `true`
		// new Trigger(drivetrain::exampleCondition)
		// .onTrue(new ExampleCommand(m_exampleSubsystem));

		driverController.x().whileTrue(new FineTUNECommand(drivetrain));

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
