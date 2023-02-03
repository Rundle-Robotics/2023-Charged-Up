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
public class GrabberLifter extends SubsystemBase {
    private CANSparkMax m;
    private RelativeEncoder e;
    private boolean lifted;
    public GrabberLifter() {
        m = new CANSparkMax(0, MotorType.kBrushless);
        e = m.getEncoder();
        lifted = false;
    }
    
    @Override
    public void periodic() {
        double p = e.getPosition();
        if (lifted == true && p < 400) {
            m.set(1);
        }
        else if (lifted == false && p <= 0) {
            m.set(0);
        }
        else {
            m.set(-1);
        }
    }
    public double getPosOfLift() {
		return e.getPosition();
	}
    public void lift(boolean newValue) {
        lifted = newValue;
    }
    
}

