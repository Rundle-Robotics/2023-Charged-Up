package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.NAVX;

public class NewAutoBalance extends CommandBase {

    private Drivetrain drivetrain;
    private NAVX navx;
    private PIDController pid;

    // Use roll
    public NewAutoBalance(Drivetrain drivetrain, NAVX navx) {
        this.drivetrain = drivetrain;
        this.navx = navx;
        pid = new PIDController(0, 0, 0);
        pid.setSetpoint(0);
        pid.setTolerance(10); // TODO
    }

    @Override
    public void execute() {
        double speed = pid.calculate(navx.getRoll());
        drivetrain.mecanumDrive(0, speed, 0);
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.mecanumDrive(0, 0, 0);
        System.out.println("NewAutoBalance finished"); // debug
    }

    @Override
    public boolean isFinished() {
        return pid.atSetpoint();
    }

}
