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
import frc.robot.Constants.ControlConstants;
import frc.robot.Constants.OperatorConstants;

public class Drivetrain extends SubsystemBase {

	private MotorController frontLeft;
	private MotorController frontRight;
	private MotorController backLeft;
	private MotorController backRight;
	private CommandXboxController controller;
	private boolean finetuned;

	public Drivetrain(CommandXboxController controller) {
		this.controller = controller;
		finetuned = false;
		frontLeft = new CANSparkMax(1, MotorType.kBrushless);
		frontRight = new CANSparkMax(4, MotorType.kBrushless);
		backLeft = new CANSparkMax(2, MotorType.kBrushless);
		backRight = new CANSparkMax(3, MotorType.kBrushless);


	}



	@Override
	public void periodic() {
		double joyX = -controller.getRawAxis(OperatorConstants.XBOX_LEFT_X_AXIS);
		double joyY = controller.getRawAxis(OperatorConstants.XBOX_LEFT_Y_AXIS);
		double rotation = ControlConstants.ROTATION_MULT
				* -(controller.getRightTriggerAxis() - controller.getLeftTriggerAxis());

		mecanumDrive(joyX, joyY, rotation);


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


		//strafing finetuning

		if (Math.abs(joystickX) <= (Math.tan(0.26))*joystickY)
			joystickX = 0;

		else if (Math.abs(joystickY) >= (Math.tan(0.26))*joystickX)
			joystickY = 0;




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
		frontLeft.set(frontLeftPower);
		frontRight.set(frontRightPower);
		backLeft.set(backLeftPower);
		backRight.set(backRightPower);

	
	}

public void finetune(boolean newValue) {

	finetuned = newValue;

}


public void stop() {
	frontLeft.set(0);
	frontRight.set(0);
	backLeft.set(0);
	backRight.set(0);
}
}