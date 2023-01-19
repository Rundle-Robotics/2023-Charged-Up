// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.RobotContainer;
import frc.robot.Constants.ControlConstants;
import frc.robot.Constants.OperatorConstants;

public class Drivetrain extends SubsystemBase {
	
	private MotorController frontLeft;
	private MotorController frontRight;
	private MotorController backLeft;
	private MotorController backRight;
	private MotorControllerGroup leftMotors;
	private MotorControllerGroup rightMotors;
	private DifferentialDrive drive;

	public Drivetrain() {

		frontLeft = new CANSparkMax(0, MotorType.kBrushless);
		frontRight = new CANSparkMax(1, MotorType.kBrushless);
		backLeft = new CANSparkMax(2, MotorType.kBrushless);
		backRight = new CANSparkMax(3, MotorType.kBrushless);

		leftMotors = new MotorControllerGroup (frontLeft, backLeft);
		rightMotors = new MotorControllerGroup (frontRight, backRight);

		rightMotors.setInverted(false);
		drive = new DifferentialDrive(leftMotors, rightMotors);

	}

	@Override
	public void periodic() {
		double joyXL = RobotContainer.driverController.getLeftX();
		double leftTrigger = RobotContainer.driverController.getLeftTriggerAxis();
		double joyYR = RobotContainer.driverController.getRightY();
		double rightTrigger = RobotContainer.driverController.getRightTriggerAxis();
		double forwardSpeed = rightTrigger - leftTrigger;

		drive.arcadeDrive(forwardSpeed, joyXL);



	}

	public void stop() {
		frontLeft.set(0);
		frontRight.set(0);
		backLeft.set(0);
		backRight.set(0);

	}
}
