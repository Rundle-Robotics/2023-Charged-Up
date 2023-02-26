// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Pneumatics;

public class TogglePneumatics extends CommandBase {
  /** Creates a new ToggleGrabber. */
  private Pneumatics pneumatics;

  public static enum actuators {
    GRABBER,
    LIFTER
  }

  private actuators selected;

  public TogglePneumatics(Pneumatics pneumatics, actuators solenoid) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.pneumatics = pneumatics;
    selected = solenoid;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    switch (selected){
    case GRABBER:
    System.out.println("toggling grabber method");
    pneumatics.setGrabberOpen();
    break;
    case LIFTER:
    System.out.println("toggling lifter method");
    pneumatics.setLifterDown();
    break;
    default:
    System.out.println("default");
    break;
    }


  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
