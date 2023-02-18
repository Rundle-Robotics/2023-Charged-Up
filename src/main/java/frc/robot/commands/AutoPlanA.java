package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.NAVX;

public class AutoPlanA extends SequentialCommandGroup {
    public AutoPlanA(Drivetrain drivetrain, Limelight limelight, NAVX navx) {
        addCommands(
                // Move to scoring position
                new LimelightFollow(drivetrain, limelight),
                // Score
                null, // TODO
                // Move over power station and beyond for mobility bonus
                null, // TODO
                // Move back onto power station
                null, // TODO
                // NAVX autobalance
                new AutoBalanceNAvX(drivetrain, navx));
    }
}
