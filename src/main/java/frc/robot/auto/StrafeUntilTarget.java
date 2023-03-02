// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;

public class StrafeUntilTarget extends CommandBase {
  private Drivetrain drivetrain;
  private Limelight limelight;
  private boolean finite;

  private double PLACEHOLDER;  /** Creates a new StrafeUntilTarget. */
  public StrafeUntilTarget(Drivetrain drivetrain, Limelight limelight) {
    this.limelight = limelight;
    this.drivetrain = drivetrain;
    
    PLACEHOLDER = 1;
    finite = false;

    addRequirements(drivetrain);
    addRequirements(limelight);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    limelight.enableLimelight();
    limelight.setPipeline(0);
    finite = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (Math.abs(limelight.getTX()) > PLACEHOLDER){
      drivetrain.mecanumDrive(0.3*Math.signum(limelight.getTX()),0,0);
    }
    else {finite = true;}
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.mecanumDrive(00,0,0);
    limelight.disableLimelight();
    limelight.setPipeline(2);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return finite;
  }
}
