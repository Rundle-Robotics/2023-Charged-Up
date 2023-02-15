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
        rm.set(newValue); //positive speed on rm is up
        lm.set(newValue);
    }

    @Override
    public void periodic() {

        double speed = rm.get();
        if (stopArm(speed))
        {
            lift(0);
        } 

    }
    /*
     * IMPORTANT
     * ADJUST EXCLAMATION MARKS TO CHANGE LOGICs
     */

    public boolean getTopSwitch() {return !topSwitch.get(); } //top switch is 'active' when low
    public boolean getMiddleSwitch() {return middleSwitch.get(); } //middle switch is 'active' when high
    public boolean getBottomSwitch() {return !bottomSwitch.get(); } //bottom siwtch is 'active' when low

    public boolean stopArm(double speed) 
    {

        //assume positive speed is moving up
        if (getTopSwitch() && speed > 0) //at top, wanting to move higher
        {
            return true;
        } 
        else if (getMiddleSwitch() && speed < 0) //at bottom, wanting to move lower
        {
            return true;
        }
        else if (!getBottomSwitch() && getMiddleSwitch()) //arm lifter is up, arm is down - can't move in either direction
        {
            return true;
        }
        else if (!getBottomSwitch() && !getMiddleSwitch() && speed < 0) //arm lifter is up, arm is not tucked and wants to move lower
        {
            return true;
        }

        return false;

    }

    public boolean lifterNeedsLowering(double speed)
    {
        if (!getBottomSwitch() && getMiddleSwitch()) //arm lifter is up, arm is down - can't move in either direction
        {
            return true;
        }
        else if (!getBottomSwitch() && !getMiddleSwitch() && speed < 0) //arm lifter is up, arm is not tucked and wants to move lower
        {
            return true;
        }

        return false;
    }

    public boolean lifterNeedsRaising(double speed)
    {
        if (getBottomSwitch() && getMiddleSwitch())
        {
            return true;
        }
        return false;
    }
    
}

