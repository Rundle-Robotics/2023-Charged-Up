package frc.robot.commands;

import javax.management.remote.TargetedNotification;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;

public class LimelightFollowTank extends CommandBase {

    private Drivetrain drivetrain;
    private Limelight limelight;

    private final double CENTER_DISTANCE = -10.2;
    private final double TARGET_AREA_CUTOFF = 1.7;
    private final double CENTER_DEADBAND = 0.7;
    private final double YAW_DEADBAND = 10;
    private final double TARGET_YAW = 0;
    private final double SPEED = 0.3;

    private double rot = 0; 
    private double forward = 0; 

    boolean finite;

    private boolean finished;

    public LimelightFollowTank(Drivetrain drivetrain, Limelight limelight) {
        this.drivetrain = drivetrain;
        this.limelight = limelight;

        addRequirements(drivetrain);
        addRequirements(limelight);
    }

    @Override
    public void initialize() {
        limelight.enableLimelight();

        limelight.setPipeline(0); // Pipeline 0 is for AprilTag detection

        finite = false;
    }

    @Override
    public void execute() {
        if (limelight.getTX() != 0) {rot = (Math.pow((limelight.getTX()-CENTER_DISTANCE)*0.01,2)) + .1;}
        else {rot = 0;}


        if (rot > .3) {rot= .3;}
        else if (rot < .3) {rot= -.3;}

        if (Math.abs(rot) < 0.12) {rot = 0;}

        if (Math.signum(limelight.getTX()) == -1) {rot = -rot;}

        else if (limelight.getTA()<TARGET_AREA_CUTOFF){
            forward = -0.3;
        }
        
        
        if (limelight.getTA()<TARGET_AREA_CUTOFF && rot == 0) {finite = true; }

        drivetrain.mecanumDrive(0, forward, rot);

        // if (limelight.getTV() !=1){
        //     drivetrain.mecanumDrive(0,0,0.2);
        // }
        // else {
        //     if (limelight.getTX()< CENTER_DEADBAND-0.75){
        //         drivetrain.mecanumDrive(0,0,rot);
                
        //     }
        //     else if (limelight.getTX()>CENTER_DEADBAND +0.75){
        //         drivetrain.mecanumDrive(0,0,rot);

            
        //     }
        //     else if (limelight.getTA()<TARGET_AREA_CUTOFF){
        //         drivetrain.mecanumDrive(0,-0.35,0);
        //     }
        //     else{
        //         finite = true;
        //     }
        
        //}
       
    }

    @Override
    public void end(boolean interrupted) {
        limelight.disableLimelight();

        limelight.setPipeline(2); // Pipeline 2 is for driver vision
    }

    @Override
    public boolean isFinished() {
        return finite;
    }

}
