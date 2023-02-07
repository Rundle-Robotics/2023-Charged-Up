package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMaxLowLevel;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
public class GrabberLifter extends SubsystemBase {
    private CANSparkMax m;
    private RelativeEncoder e;
    private double lifted;
    public GrabberLifter() {
        m = new CANSparkMax(4, MotorType.kBrushless);
        e = m.getEncoder();
        lifted = 0;
        m.setIdleMode(CANSparkMax.IdleMode.kBrake);
    }
    
    public double getPosOfLift() {
		return e.getPosition();
	}
    public void lift(double newValue) {
        m.set(newValue);
    }
    
}

