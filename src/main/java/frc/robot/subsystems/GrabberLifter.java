package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.ControlConstants;
import frc.robot.Constants.OperatorConstants;


import frc.robot.subsystems.Drivetrain;
import frc.robot.RobotContainer;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.RelativeEncoder;
public class GrabberLifterInfo extends SubsystemBase {
    private CANSparkMax m;
    private RelativeEncoder e;
    GrabberLifterInfo() {
        m = new CANSparkMax(0, MotorType.kBrushless);
        e = m.getEncoder();
    }
    public double getPosOfLift() {
		return e.getPosition();
	}

@Override
    public void periodic() {
        double p = e.getPosition();
        if (p >= 400) {
            double g = 1;
            p = 0;
        }
    }
}

