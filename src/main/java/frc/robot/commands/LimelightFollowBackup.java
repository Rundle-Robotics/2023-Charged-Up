// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;

public class LimelightFollowBackup extends CommandBase {
	/** Creates a new RetroREFLECTIVE. */

	private Drivetrain drivetrain;
	private Limelight limelight;

	private final double CENTER_DISTANCE = 0.7;
	private final double TARGET_AREA_CUTOFF = 2.1;
  private final double TARGET_YAW = 0;

	double rotation = 0;
	double speed = 0;

	boolean closeToTargeta;
	boolean finite;

	public LimelightFollowBackup(Drivetrain drivetrain, Limelight limelight) {
		this.drivetrain = drivetrain;
		this.limelight = limelight;

		addRequirements(drivetrain);
		addRequirements(limelight);
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
		limelight.enableLimelight();

		limelight.setPipeline(0); // Pipeline 1 is for RetroReflective detection

		finite = false;

	}
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double yaw = limelight.getTARGETPOSECAMERA()[5];
    if (limelight.getTV() == 0){drivetrain.mecanumDrive(0, 0, 0.5);}
    
    else {
      if (limelight.getTX() > CENTER_DISTANCE+1) {drivetrain.mecanumDrive(-0.5, 0, 0);}
      
      else if (limelight.getTX() < CENTER_DISTANCE-1) {drivetrain.mecanumDrive(0.5, 0, 0);}
    
      else if (limelight.getTA()< TARGET_AREA_CUTOFF){
        drivetrain.mecanumDrive(0,0.5,0);
      }
      else{
        if(yaw <TARGET_YAW){
          drivetrain.mecanumDrive(0,0,0.5);
        }
        else if(yaw>TARGET_YAW){
          drivetrain.mecanumDrive(0,0,-0.5);
        }
        else{finite = true;}
      }
    
    }
  
  
  
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    limelight.setPipeline(2);
    limelight.disableLimelight();
    drivetrain.mecanumDrive(0,0,0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return finite;
  }
}
