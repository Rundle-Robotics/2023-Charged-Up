// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.GrabberLifter;
import frc.robot.subsystems.Pneumatics;



public class LowerToPosition extends CommandBase {
  /** Creates a new LowerToPosition. */

  



private boolean fin;

 

private GrabberLifter grabberLifter;
private Pneumatics pneumatics;
private double speed;

  public LowerToPosition(GrabberLifter grabberLifter, Pneumatics pneumatics) {
    this.grabberLifter = grabberLifter;
    this.pneumatics = pneumatics;
        speed = 0;
        
        fin = false;
        addRequirements(grabberLifter);
        addRequirements(pneumatics);

    
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    if (grabberLifter.getBottomSwitch() != true){
      speed = -0.2;
      grabberLifter.lift(speed);
    }
   

    
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    if (interrupted != true){
      pneumatics.toggleLifter();
      fin = true;
    }
  }

  

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return fin;
  }
}


