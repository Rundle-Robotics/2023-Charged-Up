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
  private int mu;

  public BackupAutoMove(double getDistanceMetres, Drivetrain drive) {
    // Use addRequirements() here to declare subsystem dependencies.

    this.drive = drive;
    distance = getDistanceMetres;

    addRequirements(drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    initialPosition = drive.getFrontLeftPosition();

    targetPosition = initialPosition + (distance);

    yPID = new PIDController(0.05,0, 0);

    yPID.setTolerance(2); //2 ticks (1/21 of a rotation)

    yPID.setSetpoint(targetPosition);

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    currentPosition = drive.getFrontLeftPosition();

    double output = yPID.calculate(currentPosition);

    if (output > 0.2) {
      output = 0.2;
    }
    else if (output < -0.2) {
      output = -0.2;
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
