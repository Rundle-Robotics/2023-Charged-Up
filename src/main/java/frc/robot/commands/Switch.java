// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.GrabberLifter;

public class Switch extends CommandBase {
  private DigitalInput s1;
  private DigitalInput s2;
  private DigitalInput s3;
  private GrabberLifter g;
  private String text = "";
  private String text2 = "";
  /** Creates a new Switch. */
  public Switch(GrabberLifter grab) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(grab);
    g = grab;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    s1 = new DigitalInput(0);
    s2 = new DigitalInput(1);
    s3 = new DigitalInput(2);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (getTopSwitch()) {
      text = "Grabber is fully raised";
      g.lift(0);
    }
    if (getBottomSwitch()) {text2 = "Grabber folder is fully down"; }
    if (getMiddleSwitch()) {text = "Grabber is fully lowered"; }
    else {
      text2 = "Grabber folder is not completely down";

    SmartDashboard.putString("Grabber Folder State", text2);
    SmartDashboard.putString("Grabber state", text); 
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

  public boolean getTopSwitch() {return s3.get(); }
  public boolean getMiddleSwitch() {return s2.get(); }
  public boolean getBottomSwitch() {return s1.get(); }

}
