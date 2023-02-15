package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.RelativeEncoder;

public class GrabberLifter extends SubsystemBase {
    private CANSparkMax rm;
    private CANSparkMax lm;
    private RelativeEncoder e;

    private DigitalInput bottomSwitch;
    private DigitalInput middleSwitch;
    private DigitalInput topSwitch;

    public GrabberLifter() {
        rm = new CANSparkMax(5, MotorType.kBrushless);
        lm = new CANSparkMax(7, MotorType.kBrushless);
        lm.setInverted(true);
        e = rm.getEncoder();
        rm.setIdleMode(CANSparkMax.IdleMode.kBrake);
        lm.setIdleMode(CANSparkMax.IdleMode.kBrake);

        bottomSwitch = new DigitalInput(0);
        middleSwitch = new DigitalInput(1);
        topSwitch = new DigitalInput(2);
    }
    
    public double getPosOfLift() {
		return e.getPosition();
	}
    public void lift(double newValue) {
        rm.set(newValue);
        lm.set(newValue);
    }

    @Override
    public void periodic() {

        double speed = rm.get();
        if (getTopSwitch() && speed > 0)
        {
            lift(0);
        } 
        else if (getMiddleSwitch() && speed < 0)
        {
            lift(0);
        }
        else if (!getBottomSwitch() && getMiddleSwitch())
        {
            lift(0);
        }
    }


    public boolean getTopSwitch() {return topSwitch.get(); }
    public boolean getMiddleSwitch() {return middleSwitch.get(); }
    public boolean getBottomSwitch() {return bottomSwitch.get(); }
    
}

