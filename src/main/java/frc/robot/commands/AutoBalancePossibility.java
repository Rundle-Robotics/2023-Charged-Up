// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.NAVX;
import frc.robot.auto.BackupAutoMove;
import edu.wpi.first.math.controller.PIDController;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoBalancePossibility extends CommandBase {

	private Drivetrain drivetrain;
	private NAVX navx;
	private boolean finished;
	private boolean tilted;

  private double distance;
  private Drivetrain drive;

  private double initialPosition;
  private double currentPosition;
  private double targetPosition;

  private BackupAutoMove auto;

  private double initialRoll;
  
  private boolean finite;

  private PIDController yPID;
  private int mu;
  private static final double MAX_SPEED = 0.3;
  private boolean tracking;
  private double roll;
  private double setpoint;

	/** Creates a new AutoBalanceNAvX. */
	public AutoBalancePossibility(Drivetrain drivetrain, NAVX navx, BackupAutoMove auto) {
		// Use addRequirements() here to declare subsystem dependencies.

		this.drivetrain = drivetrain;
		this.navx = navx;
    this.auto = auto;

		addRequirements(drivetrain);
		addRequirements(navx);
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
    roll = navx.getRoll();
    tracking = false;
		initialPosition = drive.getFrontLeftPosition();

    initialRoll = navx.getRoll();

    targetPosition = initialPosition + (distance);

    yPID = new PIDController(0.05,0, 0);

    yPID.setTolerance(2); //2 ticks (1/21 of a rotation)

    yPID.setSetpoint(targetPosition);

    finite = false;


    
	}

  //once roll changes, drive x meters. if roll is not == 0 then drive another 1 m, and check, and again again again. 

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		currentPosition = navx.getRoll();
   

    double output = yPID.calculate(currentPosition);

    if (output > MAX_SPEED) {
      output = MAX_SPEED;
    }
    else if (output < -MAX_SPEED) {
      output = -MAX_SPEED;
    }
   //Damian look at this code too, possibly finish the code lower too, not finished atm., this 1 is a placeholder value. ishan wants a lock on the lower code. 
    // if (navx.getRoll()>9) {
      
    //   new BackupAutoMove(1, drive);
    //   if (navx.getRoll()<9){
    //     new BackupAutoMove(1, drive);
    //     if (navx.getRoll()<9){
    //       new BackupAutoMove(1, drive);
    //       if (navx.getRoll()<9){
    //         new BackupAutoMove(1, drive);
    //         if (navx.getRoll()<9){
    //           new BackupAutoMove(1, drive);
    //         }}}else {
    //           finite = true;
    //         }
    //   }
      
   // }

   if (Math.abs(roll) > 9) {
    setpoint = currentPosition + 5;
    output = -.25;
    tracking = true;
   } else {
    output = 0;
    finite = true;
  }



    drive.mecanumDrive(0, output, 0);

		
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
		drivetrain.mecanumDrive(0, 0, 0);
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		return finite;
	}
}

  

