package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;

public class LimelightFollowTank extends CommandBase {

    private Drivetrain drivetrain;
    private Limelight limelight;

    private final double CENTER_DISTANCE = -10.2;
    private final double TARGET_AREA_CUTOFF = 1.2;
    private final double CENTER_DEADBAND = 5;
    private final double YAW_DEADBAND = 10;
    private final double TARGET_YAW = 0;
    private final double SPEED = 0.45;

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

        finished = false;
    }

    @Override
    public void execute() {
        limelight.putTargetPoseDataonSmartDashboard(); // Debug

        if (limelight.getTV() == 0) {
            System.out.println("No target found, trying to turn and find one..."); // debug
            drivetrain.mecanumDrive(0, 0, SPEED);
        } else {
            System.out.println("Target found");

            double yaw = limelight.getTARGETPOSECAMERA()[5];

            boolean targetOnRight = limelight.getTX() > (CENTER_DISTANCE + CENTER_DEADBAND);
            boolean targetOnLeft = limelight.getTX() < (CENTER_DISTANCE - CENTER_DEADBAND);
            boolean targetTooFar = limelight.getTA() < TARGET_AREA_CUTOFF;
            boolean targetSkewed = Math.abs(yaw) > YAW_DEADBAND;

            // Positive forwardSpeed to move forward,
            double forwardSpeed = targetTooFar ? SPEED : 0;

            // Positive rotation to turn clockwise
            double rotation;
            if (targetOnRight || targetOnLeft) { // First priority, face the target
                rotation = targetOnRight ? SPEED : -SPEED;
            } else if (targetTooFar) {
                rotation = 0;
            } else { // Once we are up close to the target, try and rotate to match target yaw
                // Might need to invert
                rotation = targetSkewed ? (yaw - TARGET_YAW) / Math.abs(yaw - TARGET_YAW) * SPEED : 0;
            }

            drivetrain.mecanumDrive(0, forwardSpeed, rotation);
            finished = !targetOnLeft && !targetOnRight && !targetTooFar && !targetSkewed;

            System.out.print("Target detected on right: " + targetOnRight);
            System.out.print("Target detected on left: " + targetOnLeft);
            System.out.print("Target detected too far: " + targetTooFar);
            System.out.print("Target detected at a skew: " + targetSkewed);
        }
    }

    @Override
    public void end(boolean interrupted) {
        limelight.disableLimelight();

        limelight.setPipeline(2); // Pipeline 2 is for driver vision
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

}
