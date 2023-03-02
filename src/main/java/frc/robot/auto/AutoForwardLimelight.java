// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.auto;

import javax.security.auth.PrivateCredentialPermission;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;


public class AutoForwardLimelight extends CommandBase {

  private Drivetrain drivetrain;
  private Limelight limelight;
  private boolean finite = false;

  /** Creates a new AutoForwardLimelight. */
  public AutoForwardLimelight(Drivetrain drivetrain, Limelight limelight) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.drivetrain = drivetrain;
    this.limelight = limelight;

    addRequirements(drivetrain);
    addRequirements(limelight);
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
    if (limelight.getTA() < 2.1) {drivetrain.mecanumDrive(0, -.15, 0);}
    else { finite = true; } 

    SmartDashboard.putNumber("area", limelight.getTA());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    limelight.disableLimelight();
    limelight.setPipeline(2);
    drivetrain.mecanumDrive(0, 0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    
    return finite;
  }
}
 