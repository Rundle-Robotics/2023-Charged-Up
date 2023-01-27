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
import edu.wpi.first.wpilibj.motorcontrol.Spark;

public class Drivetrain extends SubsystemBase {
	
	private MotorController frontLeft;
	private MotorController frontRight;
	private MotorController backLeft;
	private MotorController backRight;
	private MotorControllerGroup leftMotors;
	private MotorControllerGroup rightMotors;
	private DifferentialDrive drive;

	public Drivetrain() {

		frontLeft = new Spark(0);
		frontRight = new Spark(2);
		backLeft = new Spark(1);
		backRight = new Spark(3);

		leftMotors = new MotorControllerGroup (frontLeft, backLeft);
		rightMotors = new MotorControllerGroup (frontRight, backRight);

		rightMotors.setInverted(true);
		drive = new DifferentialDrive(leftMotors, rightMotors);

	}

	@Override
	public void periodic() {
		double joyX = RobotContainer.driverController.getLeftX() *-1;
		double leftTrigger = RobotContainer.driverController.getLeftTriggerAxis();
		double joyY = RobotContainer.driverController.getRightX();
		double rightTrigger = RobotContainer.driverController.getRightTriggerAxis();
		double forwardSpeed = rightTrigger - leftTrigger;
		double turnSpeed = 0;

		if (Math.abs(joyX)<= -0.10){

			turnSpeed = 0;

		} 

		else if (joyX<-0.10){

			turnSpeed = (-0.555*(Math.pow(joyX+0.10, 2)))-0.30;

		}

		if (Math.abs(joyY)<= 0.10){

			turnSpeed = 0;

		} else if (joyY>0.10){

			turnSpeed = (0.555*(Math.pow(joyY-0.10, 2)))+0.30;

		}

		drive.arcadeDrive(forwardSpeed, turnSpeed);

	}

	public void stop() {
		frontLeft.set(0);
		frontRight.set(0);
		backLeft.set(0);
		backRight.set(0);

	}
}
