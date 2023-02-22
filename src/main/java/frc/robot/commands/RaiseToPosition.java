package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.GrabberLifter;

public class RaiseToPosition extends CommandBase {

    public static enum Height {
        MID,
        HIGH
    }

    private static final double MID_HEIGHT = 0; // TODO
    private static final double HIGH_HEIGHT = 120;
    private static final double DEADBAND = 10; // TODO

    private GrabberLifter grabberLifter;
    private double speed;
    private double targetHeight;

    public RaiseToPosition(GrabberLifter grabberLifter, Height target) {
        this.grabberLifter = grabberLifter;
        speed = 0;
        targetHeight = target == Height.MID ? MID_HEIGHT : HIGH_HEIGHT;
        addRequirements(grabberLifter);
    }

    @Override
    public void initialize() {
        double direction = (targetHeight - getPos()) / Math.abs(targetHeight - getPos());
        speed = direction * 0.3;
    }

    @Override
    public void execute() {
        grabberLifter.lift(speed);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(targetHeight - getPos()) <= DEADBAND;
    }

    @Override
    public void end(boolean interrupted) {
        grabberLifter.lift(0);
    }

    private double getPos() {
        return grabberLifter.getPosOfLift() - GrabberLifter.bottomEncoderValue;
    }

}
