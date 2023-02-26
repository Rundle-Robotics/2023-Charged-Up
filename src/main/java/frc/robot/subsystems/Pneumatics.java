package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Pneumatics extends SubsystemBase {
	private DoubleSolenoid sol1;
	private DoubleSolenoid armsolenoid;

	public Pneumatics() {
		sol1 = new DoubleSolenoid(PneumaticsModuleType.REVPH, 0, 1);
		sol1.set(Value.kReverse);

		armsolenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH, 2, 3);
		armsolenoid.set(Value.kReverse);
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

	public void toggleLifterMethod() {
		armsolenoid.toggle();
	}

	public void toggleGrabberMethod() {
		sol1.toggle();
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
