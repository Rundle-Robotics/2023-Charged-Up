// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.RobotContainer;
import frc.robot.Constants.ControlConstants;
import frc.robot.Constants.OperatorConstants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.ControlConstants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.Drivetrain;
import frc.robot.RobotContainer;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import edu.wpi.first.math.controller.PIDController;


public class Drivetrain extends SubsystemBase {
	private CANSparkMax frontLeft;
	private CANSparkMax frontRight;
	private CANSparkMax backLeft;
	private CANSparkMax backRight;
	private RelativeEncoder frontRighte;
	private RelativeEncoder backLefte;
	private RelativeEncoder backRighte;
  private RelativeEncoder frontLefte;
	private boolean finetuned;
	
	private PIDController pidFR;
	private PIDController pidBR;
	private PIDController pidFL; 
	private PIDController pidBL; 


	public Drivetrain() {
		finetuned = false;
		frontLeft = new CANSparkMax(1, MotorType.kBrushless);
		frontRight = new CANSparkMax(4, MotorType.kBrushless);
		backLeft = new CANSparkMax(2, MotorType.kBrushless);
		backRight = new CANSparkMax(3, MotorType.kBrushless);
        frontLefte = frontLeft.getEncoder();
		frontRighte = frontRight.getEncoder();
		backLefte = backLeft.getEncoder();
		backRighte = backRight.getEncoder();

		double kP = 0.2;
		double kI = 0.05;
		double kD = 0.0;

		pidFR = new PIDController(kP,kI,kD);
		pidBR = new PIDController(kP,kI,kD);
		pidFL = new PIDController(kP,kI,kD);
		pidBL = new PIDController(kP,kI,kD);



	}

	@Override
	public void periodic() {

		double joyX = RobotContainer.driverController.getRawAxis(OperatorConstants.XBOX_LEFT_X_AXIS);
		double joyY = -RobotContainer.driverController.getRawAxis(OperatorConstants.XBOX_LEFT_Y_AXIS);
		double rotation = ControlConstants.ROTATION_MULT
				* (RobotContainer.driverController.getRightTriggerAxis() - RobotContainer.driverController.getLeftTriggerAxis());

		mecanumDrive(joyX, joyY, rotation);
		double v = frontLefte.getVelocity();
        double p = frontLefte.getPosition();
        double CPR = frontLefte.getCountsPerRevolution();
        double revolutions = CPR/4; //not sure where this came from but okay
        SmartDashboard.putNumber("Velocity", v);
        SmartDashboard.putNumber("Position", p);
        SmartDashboard.putNumber("CountsPerRevolution", CPR);
        SmartDashboard.putNumber("Revolutions", revolutions);



	}

	// 2020 mecanum drive code
	public void mecanumDrive(double joystickX, double joystickY, double rotation) {
		// Deadband inputs
		if (Math.abs(rotation) < ControlConstants.ROTATION_DEADBAND)
			rotation = 0;
		if (Math.abs(joystickX) < ControlConstants.JOY_DEADBAND)
			joystickX = 0;
		if (Math.abs(joystickY) < ControlConstants.JOY_DEADBAND)
			joystickY = 0;

		// Cap rotation to ControlConstants value
		if (Math.abs(rotation) > ControlConstants.MAX_TURN_SPEED)
			rotation *= ControlConstants.MAX_TURN_SPEED / Math.abs(rotation);

		// Calculate speed for each wheel
		double frontRightPower = joystickY - joystickX - rotation;
		double frontLeftPower = joystickY + joystickX + rotation;
		double backLeftPower = joystickY - joystickX + rotation;
		double backRightPower = joystickY + joystickX - rotation;

		// Cap motor powers
		if (Math.abs(frontLeftPower) > ControlConstants.MAX_ROBOT_SPEED)
			frontLeftPower *= ControlConstants.MAX_ROBOT_SPEED / Math.abs(frontLeftPower);
		if (Math.abs(frontRightPower) > ControlConstants.MAX_ROBOT_SPEED)
			frontRightPower *= ControlConstants.MAX_ROBOT_SPEED / Math.abs(frontRightPower);
		if (Math.abs(backLeftPower) > ControlConstants.MAX_ROBOT_SPEED)
			backLeftPower *= ControlConstants.MAX_ROBOT_SPEED / Math.abs(backLeftPower);
		if (Math.abs(backRightPower) > ControlConstants.MAX_ROBOT_SPEED)
			backRightPower *= ControlConstants.MAX_ROBOT_SPEED / Math.abs(backRightPower);



		//finetuned driving system

		if (finetuned == true) {
			frontRightPower = frontRightPower/5;
			frontLeftPower = frontLeftPower/5;
			backRightPower = backRightPower/5;
			backLeftPower= backLeftPower/5;
	}

		SmartDashboard.putNumber("frontRight", frontRightPower);
		SmartDashboard.putNumber("frontLeft", frontLeftPower);
		SmartDashboard.putNumber("backLeft", backLeftPower);
		SmartDashboard.putNumber("backRight", backLeftPower);


		// Power the motors
		frontLeft.set(pidFL.calculate(frontLefte.getVelocity(), frontLeftPower));
		frontRight.set(pidFR.calculate(frontRighte.getVelocity(), frontRightPower));
		backLeft.set(pidBL.calculate(backLefte.getVelocity(), frontLeftPower));
		backRight.set(pidBR.calculate(backRighte.getVelocity(), frontLeftPower));

	}
	public double getBackRightPosition() {
		return backRighte.getPosition();
	}
	public double getBackLeftPosition() {
		return backLefte.getPosition();
	}
	public double getFrontLeftPosition() {
		return frontLefte.getPosition();
	}
	public double getFrontRightPosition() {
		return frontRighte.getPosition();
	}
	public double getBackRightVelocity() {
		return backRighte.getVelocity();
	}
	public double getBackLeftVelocity() {
		return backLefte.getVelocity();
	}
	public double getFrontLeftVelocity() {
		return frontLefte.getVelocity();
	}
	public double getFrontRightVelocity() {
		return frontRighte.getVelocity();
	}
	public double getBackRightCPR() {
		return backRighte.getCountsPerRevolution();
	}
	public double getBackLeftCPR() {
		return backLefte.getCountsPerRevolution();
	}
	public double getFrontLeftCPR() {
		return frontLefte.getCountsPerRevolution();
	}
	public double getFrontRightCPR() {
		return frontRighte.getCountsPerRevolution();
	}
	public void stop() {
		frontLeft.set(0);
		frontRight.set(0);
		backLeft.set(0);
		backRight.set(0);
	}

public void finetune(boolean newValue) {

	finetuned = newValue;

}

}