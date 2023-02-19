// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.auto;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants.ControlConstants;
import frc.robot.subsystems.Drivetrain;
// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class PID_Drive_Straight extends PIDCommand {
  /** Creates a new PID_Drive_Straight. */
  public PID_Drive_Straight(double getDistance, Drivetrain drive) {
    super(
        // The controller that the command will use
        new PIDController(0.222,0.222,01),
        // This should return the measurement
        drive::getFrontLeftPosition,
        // This should return the setpoint (can also be a constant)
        getDistance / ControlConstants.kDriveTick2Meter, //convert meters to encoder ticks
        // This uses the output
        output -> { 
          drive.mecanumDrive(output, 0, 0);
        },
        drive);
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.

    getController().setTolerance(0.1/ControlConstants.kDriveTick2Meter); //tolerance of 0.1m (10cm)

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return getController().atSetpoint();
  }
}