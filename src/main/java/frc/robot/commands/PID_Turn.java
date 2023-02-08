// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.Drivetrain;

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

  public PID_Turn(double targetAngleDegrees, Object DrivetrainSubsystem, Drivetrain drive) {
    super(
        // these constants have the potential to be dangerously wrong...
        new PIDController(0.333, 0.3, 0.03),
        // This should return the measurement
        //     !!!I already feel that this is wrong... pls help
        drive::getHeadingRotation2d,
        // This should return the setpoint (can also be a constant)
        //    !!!this should turn 90 degrees right, right?
        targetAngleDegrees (90),
        // This uses the output
        output -> drive.arcadeDrive(0, output),
          // Use the output here
          drive);
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.

    getController().enableContinuousInput(-180, 180);

    //      !!!i dont really know how to do this... 
    //      !!!does that seem like a reasonable tolerance? should I put it in constants?
    getController().setTolerance(0.1);

  }

  private static DoubleSupplier targetAngleDegrees(int i) {
    return null;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
