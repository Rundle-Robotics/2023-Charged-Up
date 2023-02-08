package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.RelativeEncoder;

public class GrabberLifter extends SubsystemBase {
    private CANSparkMax m;
    private RelativeEncoder e;
    public GrabberLifter() {
        m = new CANSparkMax(4, MotorType.kBrushless);
        e = m.getEncoder();
        m.setIdleMode(CANSparkMax.IdleMode.kBrake);
    }
    
    public double getPosOfLift() {
		return e.getPosition();
	}
    public void lift(double newValue) {
        m.set(newValue);
    }
    
}

