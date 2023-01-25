// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;


public class LimelightFollow extends CommandBase {
  /** Creates a new LimelightFollow. */


  private Drivetrain drivetrain;
  private Limelight limelight;



  public LimelightFollow(Drivetrain drivetrain, Limelight limelight) {
    this.drivetrain = drivetrain;
    this.limelight = limelight;

    addRequirements(drivetrain);
    addRequirements(limelight);

    

    
    
  }
  

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Limelight.enableLimelight();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    
    

     

  
      if (RobotContainer.driverController.getHID().getBButton()) {

         if (limelight.getTV() == 0){
          drivetrain.setSpeeds(0, .1);
          

         }
         else if (Math.abs(limelight.getTX()) > 1){
          drivetrain.setSpeeds(0,limelight.getTX() / Math.abs(limelight.getTX()) * 0.1);
         
        
        }

        

    }


  }

 

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    limelight.disableLimelight();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
    
  }
}
