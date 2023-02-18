// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.ControlConstants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.AutoBalanceNAvX;
import frc.robot.subsystems.Drivetrain;
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
	private final XboxController controller = driverController.getHID();

	public static final CommandXboxController secondaryController = new CommandXboxController(
			OperatorConstants.SECONDARY_CONTROLLER_PORT);

	private static UsbCamera mastCamera;
	private static UsbCamera armCamera;
	private static NetworkTableEntry cameraSelection;


	// Subsystems
	// private static Compressor compressor;
	private final Pneumatics pneumatics;

	// The robot's subsystems and commands are defined here...
	private final GrabberLifter grabberLifter = new GrabberLifter();
	private final Drivetrain drivetrain = new Drivetrain();

	// declared NAVX
	public final NAVX navx = new NAVX();

	/**
	 * The container for the robot. Contains subsystems, OI devices, and commands.
	 */
	public RobotContainer() {
		// compressor = new Compressor(PneumaticsModuleType.REVPH);
		// compressor.enableDigital();

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
		// driverController.y().onTrue(new GrabberLifterCommand(0.4,
		// grabberLifter)).onFalse(new GetClosePosition(grabberLifter));

		// driverController.a().onTrue(new GrabberLifterCommand(-0.4,
		// grabberLifter)).onFalse(new GetClosePosition(grabberLifter));

		// driverController.rightBumper().onTrue(new GrabberLifterCommand(0.2,
		// grabberLifter, false)).onFalse(new GrabberLifterCommand(0, grabberLifter,
		// false));
		// driverController.leftBumper().onTrue(new GrabberLifterCommand(0.2,
		// grabberLifter, true)).onFalse(new GrabberLifterCommand(0, grabberLifter,
		// false));

		secondaryController.rightTrigger(ControlConstants.JOY_DEADBAND).whileTrue(new GrabberLifterCommand(0.2, grabberLifter, false));
		secondaryController.leftTrigger(ControlConstants.JOY_DEADBAND).whileTrue(new GrabberLifterCommand(0.2, grabberLifter, true));

		secondaryController.rightBumper().whileTrue(new GrabberLifterCommand(0.4, grabberLifter, false)).onFalse(new GetClosePosition(grabberLifter));
		secondaryController.leftBumper().whileTrue(new GrabberLifterCommand(0.4, grabberLifter, true)).onFalse(new GetClosePosition(grabberLifter));
		

		// Example: Schedule `ExampleCommand` when `exampleCondition` changes to `true`
		// new Trigger(drivetrain::exampleCondition)
		// .onTrue(new ExampleCommand(m_exampleSubsystem));

		driverController.leftTrigger(ControlConstants.JOY_DEADBAND).whileTrue(new FineTUNECommand(drivetrain));


		//driverController.b().whileTrue(new --the limelight retro reflective -- );
		//driverController.x().whileTrue(new --limelight follow--);

		secondaryController.b().onTrue(pneumatics.toggleGrabberSolenoid());
		secondaryController.a().onTrue(pneumatics.toggleLifter());

		driverController.rightTrigger(ControlConstants.JOY_DEADBAND).whileTrue(
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
		driverController.a().whileTrue(new AutoBalanceNAvX(drivetrain, navx));
		
	}

	public Command getAutonomousCommand() {
		return null;
	}
}
