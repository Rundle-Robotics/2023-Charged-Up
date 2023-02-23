package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.RelativeEncoder;

public class GrabberLifter extends SubsystemBase {
    private CANSparkMax rm;
    private CANSparkMax lm;
    private RelativeEncoder e;
    private RelativeEncoder eL;

    private DigitalInput bottomSwitch;
    private DigitalInput middleSwitch;
    private DigitalInput topSwitch;

    public static double bottomEncoderValue;

    public GrabberLifter() {
        rm = new CANSparkMax(5, MotorType.kBrushless);
        lm = new CANSparkMax(7, MotorType.kBrushless);
        rm.setInverted(true);
        eL = lm.getEncoder();
        e = rm.getEncoder();

        rm.setIdleMode(CANSparkMax.IdleMode.kBrake);
        lm.setIdleMode(CANSparkMax.IdleMode.kBrake);

        bottomSwitch = new DigitalInput(0); // lifter (solenoid/piston) at bottom = pressed, no switch for lifter at top
        middleSwitch = new DigitalInput(1); // arm (motors) at bottom height = pressed
        topSwitch = new DigitalInput(2); // arm (motors) at max height = pressed

        bottomEncoderValue = e.getPosition();
    }

    public double getPosOfLift() {
        return e.getPosition();
    }

    public void lift(double newValue) {
        if (stopArm(newValue)) {
            rm.set(0);
            lm.set(0);
        } else {
            rm.set(newValue); // positive speed on rm is up
            lm.set(newValue);
        }

    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("right encoder", e.getPosition());
        SmartDashboard.putNumber("left encoder", eL.getPosition());
        SmartDashboard.putNumber("left encoder rpm", eL.getVelocity());
        SmartDashboard.putNumber(" right encoder rpm", e.getVelocity());

        SmartDashboard.putBoolean("middle", getMiddleSwitch());
        SmartDashboard.putBoolean("bottom", getBottomSwitch());
        SmartDashboard.putBoolean("top", getTopSwitch());

    }
    /*
     * IMPORTANT
     * ADJUST EXCLAMATION MARKS TO CHANGE LOGICs
     */

    public boolean getTopSwitch() {
        return topSwitch.get();
    } // returns true when top switch is pressed

    public boolean getMiddleSwitch() {
        return middleSwitch.get();
    } // returns true when middle switch is pressed

    public boolean getBottomSwitch() {
        return !bottomSwitch.get(); // this switch is inverted
    } // returns true when bottom switch is pressed

    /**
     * @param speed given to the motors
     * @return true if the arm needs to stop moving, false if it can continue moving
     */
    public boolean stopArm(double speed) {
        // assume positive speed is moving up

        // at top, wanting to move higher
        if (getTopSwitch() && speed > 0)
            return true;

        // at bottom, wanting to move lower
        else if (getMiddleSwitch() && speed < 0)
            return true;

        // arm lifter is up, arm is tucked in - can't move in either direction
        else if (!getBottomSwitch() && getMiddleSwitch())
            return true;

        // arm lifter is up, arm is not tucked in and wants to move lower
        else if (!getBottomSwitch() && !getMiddleSwitch() && speed < 0)
            return true;

        // none of the above cases
        return false;

    }

    public boolean lifterNeedsLowering(double speed) {
        if (!getBottomSwitch() && getMiddleSwitch()) // arm lifter is up, arm is down - can't move in either direction
        {
            return true;
        } else if (!getBottomSwitch() && !getMiddleSwitch() && speed < 0) // arm lifter is up, arm is not tucked and
                                                                          // wants to move lower
        {
            return true;
        }

        return false;
    }

    public boolean lifterNeedsRaising(double speed) {
        if (getBottomSwitch() && getMiddleSwitch()) {
            return true;
        }
        return false;
    }

}
