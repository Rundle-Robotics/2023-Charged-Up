package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.RelativeEncoder;
public class GrabberLifter extends SubsystemBase {
    private CANSparkMax m;
    private RelativeEncoder e;
    private double lifted;
    public GrabberLifter() {
        m = new CANSparkMax(4, MotorType.kBrushless);
        e = m.getEncoder();
        lifted = 0;
    }
    
    public double getPosOfLift() {
		return e.getPosition();
	}
    public void lift(double newValue) {
        lifted = newValue;
        m.set(lifted);
    }
    
}

