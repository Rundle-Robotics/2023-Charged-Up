// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.kinematics.MecanumDriveWheelSpeeds;
import edu.wpi.first.wpilibj2.command.SubsystemBase;



import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.CANSparkMax;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;
// IF  REV  IMPORTS FAIL AT ANY TIME!!!!!!
// click WPILib button, click manage vendor libraries, click install new libraries online
// USE THIS LINK!! 
// https://www.revrobotics.com/content/sw/max/sdk/REVRobotics.json
// for all cansparkmax imports
// https://software-metadata.revrobotics.com/REVLib.json

public class MecanumSubsystem extends SubsystemBase {

  // right motors
  public CANSparkMax frontRight;
  public  CANSparkMax backRight;
  public  double frontRightPower;
  public  double backRightPower;

  // left motors
  public  CANSparkMax frontLeft;
  public  CANSparkMax backLeft;
  public  double frontLeftPower;
  public  double backLeftPower;

  public  MecanumDriveWheelSpeeds wheelSpeeds;

  double deadzone;
  



  /** Creates a new MecanumSubsystem. */
  public MecanumSubsystem() {

    // declaring motors
    // right
    frontRight = new CANSparkMax(0, MotorType.kBrushless);
    backRight = new CANSparkMax(1, MotorType.kBrushless);

    // left
    frontLeft = new CANSparkMax(2, MotorType.kBrushless);
    backLeft = new CANSparkMax(3, MotorType.kBrushless);
    



    frontRight.setInverted(true);
    backRight.setInverted(true);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    

  }

  public void setSpeeds(double stickX, double stickY, double rotation, double deadzone) {
    // deadzone
    if (Math.abs(stickX) < deadzone) {
      stickX = (double) 0;
    } if (Math.abs(stickY) < deadzone) {
      stickY = (double) 0;
    } if (Math.abs(rotation) < deadzone) {
      rotation = (double) 0;
    }

    


    SmartDashboard.putNumber("rotation", rotation);
    SmartDashboard.putNumber("stick X", stickX);
    SmartDashboard.putNumber("stick yy", stickY);

    // math (thanks damian)
    // right
    frontRightPower = stickY - stickX - rotation;
    backRightPower = stickY + stickX - rotation;

    // left
    frontLeftPower = stickY + stickX + rotation;
    backLeftPower = stickY - stickX + rotation;


    // setting motor speeds
    // right
    frontRight.set(frontRightPower);
    backRight.set(backRightPower);

    // left
    frontLeft.set(frontLeftPower);
    backLeft.set(backLeftPower);
  }

  
}