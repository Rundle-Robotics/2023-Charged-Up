// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;



import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.ControlConstants;
import frc.robot.Constants.OperatorConstants;

import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;



public class Drivetrain extends SubsystemBase {

	private MotorController frontLeft;
	private MotorController frontRight;
	private MotorController backLeft;
	private MotorController backRight;

	private MotorController neo;

	private CommandXboxController controller;
	
	private MotorControllerGroup leftMotors;
	private MotorControllerGroup rightMotors;
	private DifferentialDrive drive;

	public Drivetrain(CommandXboxController controller) {
		this.controller = controller;

	    frontLeft = new Spark(0);
		frontRight = new Spark(2);
		backLeft = new Spark(1);
		backRight = new Spark(3);

		neo = new PWMSparkMax(4);

		leftMotors = new MotorControllerGroup(frontLeft,backLeft);
		rightMotors = new MotorControllerGroup(frontRight, backRight);
		
		rightMotors.setInverted(true);
		
		drive = new DifferentialDrive(leftMotors, rightMotors);

	}

	@Override
	public void periodic() {
		double move = controller.getLeftTriggerAxis();
		double turn = controller.getLeftX();

		drive.arcadeDrive(move, turn);

		neo.set(controller.getRightX()/6);
	}


	

	public void stop() {
		frontLeft.set(0);
		frontRight.set(0);
		backLeft.set(0);
		backRight.set(0);
	}
	public void setSpeeds(double forwardSpeed, double rotationSpeed){
		drive.arcadeDrive(forwardSpeed,rotationSpeed);

	}
}
