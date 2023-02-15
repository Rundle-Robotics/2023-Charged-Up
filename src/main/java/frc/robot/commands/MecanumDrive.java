// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.Constants.ControlConstants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.NAVX;

public class MecanumDrive extends CommandBase {
  /** Creates a new MecanumDrive. */

  private Drivetrain drivetrain;
  private NAVX navx;

  double forwardSpeed;
  double desiredHeading;
  double currentHeading;
  double rotation;
  double turnPower;
  double kP = 0.1;

  private boolean firstStrafe;
  private boolean strafeLock;

  public MecanumDrive(Drivetrain drivetrain, NAVX navx) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.drivetrain = drivetrain;
    this.navx = navx;
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    double joyX = RobotContainer.driverController.getRawAxis(OperatorConstants.XBOX_LEFT_X_AXIS);
    double joyY = -RobotContainer.driverController.getRawAxis(OperatorConstants.XBOX_LEFT_Y_AXIS);
    double rotation = ControlConstants.ROTATION_MULT
        * (RobotContainer.driverController.getRawAxis(OperatorConstants.XBOX_RIGHT_X_AXIS));

    currentHeading = navx.getYaw();

    // strafe lock
    if (Math.abs(joyX) <= (Math.tan(0.26)) * joyY) {
      joyX = 0;
      strafeLock = true;

      if (firstStrafe == true) {
        firstStrafe = false;
        desiredHeading = navx.getYaw();
      }

      if (desiredHeading != currentHeading) {
        turnPower = (currentHeading - desiredHeading) * (kP);
        rotation = turnPower;
      }

    } else if (Math.abs(joyY) <= (Math.tan(0.26)) * joyX) {
      joyY = 0;
      strafeLock = true;
      
    } else {
      firstStrafe = true;
      strafeLock = false;
    }
    drivetrain.mecanumDrive(joyX, joyY, rotation);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
