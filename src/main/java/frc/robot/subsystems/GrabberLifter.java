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
    private boolean lowered;
    public GrabberLifter() {
        m = new CANSparkMax(0, MotorType.kBrushless);
        e = m.getEncoder();
        lifted = false;
    }
    
    @Override
    public void periodic() {
        double p = e.getPosition();
        if (lifted == true && lowered == false) {
            m.set(2);
        }
        else if (lifted == false && lowered == false && p >= 180 && p <= 220 || lifted == false && lowered == false && p >= 380 && p <= 420 || lifted == false && lowered == false && p >= 580 && p <= 620 || lifted == false && lowered == false && p >= 780 && p <= 820 ) {
            m.set(0);
        }
        else if (lowered == false && lifted == false){
            m.set(-2);
        }
        else {
            m.set(-2);
        }
    }
    public double getPosOfLift() {
		return e.getPosition();
	}
    public void lift(boolean newValue) {
        lifted = newValue;
    }
    public void lower(boolean newValue) {
        lowered = newValue;
    }
    
}

