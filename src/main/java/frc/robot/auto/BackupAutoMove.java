// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.auto;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ControlConstants;
import frc.robot.subsystems.Drivetrain;

public class BackupAutoMove extends CommandBase {
  /** Creates a new BackupAutoMove. */

  private double distance;
  private Drivetrain drive;

  private double initialPosition;
  private double currentPosition;
  private double targetPosition;

  private PIDController yPID;

  public BackupAutoMove(double getDistanceMetres, Drivetrain drive) {
    // Use addRequirements() here to declare subsystem dependencies.

    this.drive = drive;
    distance = getDistanceMetres;

    addRequirements(drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    initialPosition = drive.getFrontRightPosition();

    targetPosition = initialPosition + (distance / ControlConstants.kDriveTick2Meter);

    yPID = new PIDController(0.5, 0.1, 0.1);

    yPID.setSetpoint(targetPosition);

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    currentPosition = drive.getFrontRightPosition();

    double output = yPID.calculate(currentPosition);

    if (output > 0.6) {
      output = 0.6;
    }
    else if (output < -0.6) {
      output = -0.6;
    }

    drive.mecanumDrive(0, output, 0);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drive.mecanumDrive(0, 0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return yPID.atSetpoint();
  }
}
