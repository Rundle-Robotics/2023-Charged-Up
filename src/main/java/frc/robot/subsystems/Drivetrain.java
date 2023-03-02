// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.Constants.ControlConstants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.Drivetrain;
import com.revrobotics.RelativeEncoder;

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
		frontLeft.setIdleMode(CANSparkMax.IdleMode.kBrake);
		frontRight.setIdleMode(CANSparkMax.IdleMode.kBrake);
		backLeft.setIdleMode(CANSparkMax.IdleMode.kBrake);
		backRight.setIdleMode(CANSparkMax.IdleMode.kBrake);

		//set robot to acceleate from 0 to max speed in a MINIMUM of 2 seconds
		frontLeft.setOpenLoopRampRate(1);
		backLeft.setOpenLoopRampRate(1);
		frontRight.setOpenLoopRampRate(1);
		backRight.setOpenLoopRampRate(1);


	}

	@Override
	public void periodic() {

		double joyX = -RobotContainer.driverController.getRawAxis(OperatorConstants.XBOX_LEFT_X_AXIS);
		double joyY = RobotContainer.driverController.getRawAxis(OperatorConstants.XBOX_LEFT_Y_AXIS);
		double rotation = -ControlConstants.ROTATION_MULT
				* (RobotContainer.driverController.getRawAxis(OperatorConstants.XBOX_RIGHT_X_AXIS));

		// strafe lock
		if (Math.abs(joyX) <= (Math.tan(0.26)) * joyY) {
			joyX = 0;
		} else if (Math.abs(joyY) <= (Math.tan(0.26)) * joyX) {
			joyY = 0;
		}


		mecanumDrive(joyX, joyY, rotation);

		double v = frontLefte.getVelocity();
		double p = frontLefte.getPosition();
		double CPR = frontLefte.getCountsPerRevolution();
		SmartDashboard.putNumber("Velocity", v);
		SmartDashboard.putNumber("Position", p);
		SmartDashboard.putNumber("CountsPerRevolution", CPR);

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

		if (rotation + joystickX + joystickY < 3*ControlConstants.JOY_DEADBAND || finetuned == false){
			this.setRampRate(0.2);
		} else {
			this.setRampRate(1);
		}

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

		// finetuned driving system
		if (finetuned == true) {
			frontRightPower = frontRightPower / 5;
			frontLeftPower = frontLeftPower / 5;
			backRightPower = backRightPower / 5;
			backLeftPower = backLeftPower / 5;
		}

		SmartDashboard.putNumber("frontRight", frontRightPower);
		SmartDashboard.putNumber("frontLeft", frontLeftPower);
		SmartDashboard.putNumber("backLeft", backLeftPower);
		SmartDashboard.putNumber("backRight", backLeftPower);

		// Power the motors
		frontLeft.set(frontLeftPower);
		frontRight.set(frontRightPower);
		backLeft.set(backLeftPower);
		backRight.set(backRightPower);

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

	private void setRampRate(double rampRate){
		frontLeft.setOpenLoopRampRate(rampRate);
		backLeft.setOpenLoopRampRate(rampRate);
		frontRight.setOpenLoopRampRate(rampRate);
		backRight.setOpenLoopRampRate(rampRate);
	}



}