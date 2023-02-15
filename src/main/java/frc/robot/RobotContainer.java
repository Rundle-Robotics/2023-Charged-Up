// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.Pneumatics;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.commands.GetClosePosition;
import frc.robot.commands.GrabberLifterCommand;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.GrabberLifter;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.FineTUNECommand;
import frc.robot.subsystems.NAVX;

public class RobotContainer {
	public static final CommandXboxController driverController = new CommandXboxController(
			OperatorConstants.DRIVER_CONTROLLER_PORT);
	private final XboxController controller  = driverController.getHID();

	public static final CommandXboxController secondaryController = new CommandXboxController(
			OperatorConstants.SECONDARY_CONTROLLER_PORT);

	private static UsbCamera mastCamera;
	private static UsbCamera armCamera;
	private static NetworkTableEntry cameraSelection;


	// Subsystems
	private static Compressor compressor;
	private final Pneumatics pneumatics;

	// The robot's subsystems and commands are defined here...
	private final GrabberLifter grabberLifter = new GrabberLifter();
	private final Drivetrain drivetrain = new Drivetrain();

	//declared NAVX
	public final NAVX navx = new NAVX();


	/**
	 * The container for the robot. Contains subsystems, OI devices, and commands.
	 */
	public RobotContainer() {
		compressor = new Compressor(PneumaticsModuleType.REVPH);
        compressor.enableDigital();

		pneumatics = new Pneumatics();

		mastCamera = CameraServer.startAutomaticCapture(OperatorConstants.MAST_CAMERA_PORT);
		armCamera = CameraServer.startAutomaticCapture(OperatorConstants.ARM_CAMERA_PORT);

		cameraSelection = NetworkTableInstance.getDefault().getTable("").getEntry("CameraSelection");
		
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
		secondaryController.y().onTrue(new GrabberLifterCommand(0.4, grabberLifter, pneumatics)).onFalse(new GetClosePosition(grabberLifter));

		secondaryController.a().onTrue(new GrabberLifterCommand(-0.4, grabberLifter, pneumatics)).onFalse(new GetClosePosition(grabberLifter));

		secondaryController.povUp().onTrue(new GrabberLifterCommand(0.2, grabberLifter, pneumatics)).onFalse(new GrabberLifterCommand(0, grabberLifter, pneumatics));

		secondaryController.povDown().onTrue(new GrabberLifterCommand(-0.2, grabberLifter, pneumatics)).onFalse(new GrabberLifterCommand(0, grabberLifter, pneumatics));
		// Example: Schedule `ExampleCommand` when `exampleCondition` changes to `true`
		// new Trigger(drivetrain::exampleCondition)
		// .onTrue(new ExampleCommand(m_exampleSubsystem));

		driverController.x().whileTrue(new FineTUNECommand(drivetrain));

		driverController.a().onTrue(pneumatics.toggleGrabberSolenoid());
		driverController.b().onTrue(pneumatics.toggleLifter());

		driverController.start().onTrue(
			new StartEndCommand(
				() -> cameraSelection.setString(armCamera.getName()),
				() -> cameraSelection.setString(mastCamera.getName())
			)
		);

		// Example: Schedule `exampleMethodCommand` when the Xbox controller's B button
		// is
		// pressed,
		// cancelling on release.
		// m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());
	}

	public Command getAutonomousCommand() {
		return null;
	}
}
