// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.auto;

import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.NAVX;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class PID_Turn extends PIDCommand {
  /** Creates a new PID_Turn. 
   * 
   /      !!!  does this even need to be here?
   * @param DrivetrainSubsystem */

   //   !!! where does this go?
   public Rotation2d getHeadingRotation2d;

   private static DoubleSupplier targetAngleDegreesDoubleSupplier;

   private Drivetrain m_drive;
   private NAVX m_navx;
   private double setpointDegrees;

  public PID_Turn(double targetAngleDegrees, Drivetrain drive, NAVX navx, double kp, double ki, double kd) {
    super(
        // these constants have the potential to be dangerously wrong...
        new PIDController(kp,ki,kd),
        // This should return the measurement
        //     !!!I already feel that this is wrong... pls help
        navx::getYaw,
        // This should return the setpoint (can also be a constant)
        //    !!!this should turn 90 degrees right, right?

        navx.getYaw() + targetAngleDegrees,
        // This uses the output
        output -> {
          if (output > 0.5){
            output = 0.5;
          }
          else if (output < -0.5){
            output = -0.5;
          }
          drive.mecanumDrive(0, 0, -1*output);
        }
          ,
          // Use the output here
          drive);
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.

    getController().enableContinuousInput(-180, 180);

    //      !!!i dont really know how to do this... 
    //      !!!does that seem like a reasonable tolerance? should I put it in constants?
    getController().setTolerance(1);

    m_drive = drive;
    m_navx = navx;
    setpointDegrees = targetAngleDegrees;

  }

  private static DoubleSupplier targetAngleDegrees(int i) {
    return null;
  }

  
  @Override
  public void end(boolean interrupted) {
    m_drive.mecanumDrive(0, 0, 0);
		//limelight.setPipeline(2); // Pipeline 2 is for driver vision
	}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false; //getController().atSetpoint();
  }
}