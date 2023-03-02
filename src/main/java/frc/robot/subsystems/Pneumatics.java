package frc.robot.subsystems;

import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator.Validity;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Pneumatics extends SubsystemBase {
	private DoubleSolenoid sol1;
	private DoubleSolenoid armsolenoid;

	public Pneumatics() {
		sol1 = new DoubleSolenoid(PneumaticsModuleType.REVPH, 2, 3);
		sol1.set(Value.kReverse);

		armsolenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH, 1, 0);
		armsolenoid.set(Value.kForward);
	}

	@Override
	public void periodic() {
	}

	public void setGrabber(Value value) {
		sol1.set(value);
	}

	public void setLifter(Value value) {
		armsolenoid.set(value);
	}

	public void setLifterDown() {
		armsolenoid.set(Value.kReverse);
	}

	public void setLifterUp() {
		armsolenoid.set(Value.kForward);
	}

	public void setGrabberOpen() {
		sol1.set(Value.kForward);
	}

	public CommandBase toggleGrabberSolenoid() {
		return this.runOnce(() -> {
			sol1.toggle();
			System.out.println("Toggling Grabber solenoid"); // debug
		});
	}

	public CommandBase toggleLifter() {
		return this.runOnce(() -> {
			armsolenoid.toggle();
			System.out.println("Toggling Grabber solenoid"); // debug
		});
	}
}
