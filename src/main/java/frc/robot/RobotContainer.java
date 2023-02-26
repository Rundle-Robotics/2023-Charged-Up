// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.ControlConstants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.auto.BackupAutoMove;
import frc.robot.auto.PID_Drive_Straight;
import frc.robot.auto.PID_Turn;
import frc.robot.auto.TogglePneumatics;
import frc.robot.auto.TogglePneumatics.actuators;
import frc.robot.auto.BackupAutoMove;
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
import frc.robot.commands.GrabberLifterCommand;
import frc.robot.commands.LimelightFollow;
import frc.robot.commands.LowerToPosition;
import frc.robot.commands.RaiseToPosition;
import frc.robot.commands.RetroReflectiveFollow;
import frc.robot.commands.RaiseToPosition.Height;
import frc.robot.subsystems.GrabberLifter;
import frc.robot.subsystems.Limelight;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.FineTUNECommand;
import frc.robot.subsystems.NAVX;

public class RobotContainer {
	public static final CommandXboxController driverController = new CommandXboxController(
			OperatorConstants.DRIVER_CONTROLLER_PORT);

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
	private final Limelight limelight = new Limelight();

	// declared NAVX
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
		// GrabberLifter binding
		driverController.rightBumper().whileTrue(new GrabberLifterCommand(0.2, grabberLifter));
		driverController.leftBumper().whileTrue(new GrabberLifterCommand(-0.2, grabberLifter));

		secondaryController.rightTrigger().onTrue(new RaiseToPosition(grabberLifter, Height.HIGH));
		secondaryController.leftTrigger().onTrue(new RaiseToPosition(grabberLifter, Height.MID));
		secondaryController.rightBumper().whileTrue(new GrabberLifterCommand(0.4, grabberLifter));
		secondaryController.leftBumper().whileTrue(new GrabberLifterCommand(-0.4, grabberLifter));
		//secondaryController.povDown().onTrue(new LowerToPosition(grabberLifter, pneumatics));

		// Camera swap binding
		driverController.start().onTrue(
				new StartEndCommand(
						() -> cameraSelection.setString(armCamera.getName()),
						() -> cameraSelection.setString(mastCamera.getName())));
		secondaryController.start().onTrue(
				new StartEndCommand(
						() -> cameraSelection.setString(armCamera.getName()),
						() -> cameraSelection.setString(mastCamera.getName())));

		// Solenoid binding
		//driverController.b().onTrue(pneumatics.toggleGrabberSolenoid());
		//driverController.a().onTrue(pneumatics.toggleLifter());
		//secondaryController.b().onTrue(pneumatics.toggleGrabberSolenoid());
		//secondaryController.a().onTrue(pneumatics.toggleLifter());

		// Autobalance binding
		driverController.x().whileTrue(new AutoBalanceNAvX(drivetrain, navx));

		// Limelight follow binding
		driverController.y().whileTrue(new LimelightFollow(drivetrain, limelight));
		driverController.b().whileTrue(new RetroReflectiveFollow(drivetrain, limelight));

		

		// FineTune binding
		driverController.leftTrigger(ControlConstants.JOY_DEADBAND).whileTrue(new FineTUNECommand(drivetrain));

	}

	public Command getAutonomousCommand() {
		return (new RaiseToPosition(grabberLifter, Height.HIGH))
		.andThen((new BackupAutoMove(-30, drivetrain))
		.andThen((new BackupAutoMove(50, drivetrain))
		.andThen((new LowerToPosition(grabberLifter, pneumatics)))));

		//return (new TogglePneumatics(pneumatics, actuators.GRABBER));

			
		
	}
}
