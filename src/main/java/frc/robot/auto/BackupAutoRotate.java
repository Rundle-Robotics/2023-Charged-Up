// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.auto;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ControlConstants;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.NAVX;

public class BackupAutoRotate extends CommandBase {
  /** Creates a new BackupAutoRotate. */

  private double turn;
  private Drivetrain drive;
  private NAVX navx;

  private double initialPosition;
  private double currentPosition;
  private double targetPosition;

  private PIDController zPID;

  public BackupAutoRotate(double targetAngleDegrees, Drivetrain drive, NAVX navx) {
    // Use addRequirements() here to declare subsystem dependencies.

    this.drive = drive;
    this.navx = navx;
    turn = targetAngleDegrees;

    addRequirements(drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    initialPosition = navx.getYaw();

    targetPosition = initialPosition + (turn);

    zPID = new PIDController(0.05,0, 0);

    zPID.setSetpoint(targetPosition);
    zPID.setTolerance(2); //2 degrees

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    currentPosition = navx.getYaw();

    double output = zPID.calculate(currentPosition);

    if (output > 0.4) {
      output = 0.4;
    }
    else if (output < -0.4) {
      output = -0.4;
    }

    drive.mecanumDrive(0, 0, output);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drive.mecanumDrive(0, 0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return zPID.atSetpoint();
  }
}
