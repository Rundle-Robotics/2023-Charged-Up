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

        bottomSwitch = new DigitalInput(0);
        middleSwitch = new DigitalInput(1);
        topSwitch = new DigitalInput(2);

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

    }
    /*
     * IMPORTANT
     * ADJUST EXCLAMATION MARKS TO CHANGE LOGICs
     */

    public boolean getTopSwitch() {
        return topSwitch.get();
    } // returns true when top switch is pressed (lifter at top)

    public boolean getMiddleSwitch() {
        return middleSwitch.get();
    } // returns true when middle switch is pressed (this switch tells if the arm is
      // down)

    public boolean getBottomSwitch() {
        return !bottomSwitch.get(); // keep the inversion
    } // returns true when bottom switch is pressed (lifter at bottom)

    public boolean stopArm(double speed) {

        // assume positive speed is moving up
        if (getTopSwitch() && speed > 0) // at top, wanting to move higher
        {
            return true;
        } else if (getBottomSwitch() && speed < 0) // at bottom, wanting to move lower
        {
            return true;
        } else if (!getBottomSwitch() && getMiddleSwitch()) // arm lifter is up, arm is down - can't move in either
                                                            // direction
        {
            return true;
        } else if (!getBottomSwitch() && !getMiddleSwitch() && speed < 0) // arm lifter is up, arm is not tucked and
                                                                          // wants to move lower
        {
            return true;
        }

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
