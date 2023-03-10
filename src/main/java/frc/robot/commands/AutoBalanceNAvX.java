// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.NAVX;
import java.util.concurrent.TimeUnit;

import edu.wpi.first.wpilibj2.command.CommandBase;


public class AutoBalanceNAvX extends CommandBase {

  private Drivetrain drivetrain;
  private NAVX navx;
  private boolean finished;
  private boolean HasMoved;
  /** Creates a new AutoBalanceNAvX. */
  public AutoBalanceNAvX(Drivetrain drivetrain, NAVX navx) {
    // Use addRequirements() here to declare subsystem dependencies.

    this.drivetrain = drivetrain;
    this.navx = navx;

    addRequirements(drivetrain);
    addRequirements(navx);






  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    finished = false;
    HasMoved = false;

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
    double roll = navx.getRoll();
    double speed = 0;
    if (Math.abs(roll) < 9 && HasMoved == false){
      speed = -0.2;
      
    }
   
    else if (roll < -9){
      speed = 0.2;
      HasMoved = true;
    }
    else if ((roll) > 9){
      speed = -0.2;
      HasMoved = true;
    }
    else if((Math.abs(roll) < 9) && (Math.abs(roll) > 5) && HasMoved == true) {
      speed = 0.1;
    }
    else if((Math.abs(roll) < 5) && HasMoved == true) {
      speed = 0;
      finished = true;
    }
    
  
    drivetrain.mecanumDrive(0, -1*speed, 0);
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.mecanumDrive(0, 0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    

    
    return finished;
  }
}
