// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Drivetrain;


import edu.wpi.first.wpilibj2.command.CommandBase;

public class LimelightFollowBackup extends CommandBase {

  private Limelight limelight;
  private Drivetrain drivetrain;

  private final double CENTER_DISTANCE = -17;
	private final double TARGET_AREA_CUTOFF = 0.7;
  private final double TARGET_YAW = 0;

  double rotation = 0;
	double speed = 0;

  boolean finite;


  /** Creates a new LimelightFollowBackup. */
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

		limelight.setPipeline(0); // Pipeline 0 is for AprilTag detection

		finite = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    double yaw = limelight.getTARGETPOSECAMERA()[5];
    if (limelight.getTV()!= 1){
      drivetrain.mecanumDrive(0,0,0.5);
    }
    else {
			System.out.println("Target found");
			// If target is on the right, turn right
			if (limelight.getTX() > CENTER_DISTANCE - 2) {
				System.out.println("Target on the right, trying to turn..."); // debug
				drivetrain.mecanumDrive(-0.5, 0, 0);
			}
			// If target is on the left, turn left
			else if (limelight.getTX() < CENTER_DISTANCE + 2) {
				System.out.println("Target on the left, trying to turn..."); // debug
				drivetrain.mecanumDrive(0.5, 0, 0);
			}
			// If target area is too small, move forward
			else if (limelight.getTA() < TARGET_AREA_CUTOFF) {
				System.out.println("Target too far, trying to move forward..."); // debug
				drivetrain.mecanumDrive(0, 0.5, 0);
			} else {
        if (yaw < TARGET_YAW){
          drivetrain.mecanumDrive(0,0,0.5);
        }
        else if(yaw>TARGET_YAW){
          drivetrain.mecanumDrive(0,0,-0.5);
        }
        else{
          finite = true;
        }
      }
    }
  }
    
    
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return finite;
  }
}
