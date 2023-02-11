package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Pneumatics extends SubsystemBase {
	private DoubleSolenoid sol1;
	private DoubleSolenoid armsolenoid;
	private boolean israised;

	public Pneumatics() {
		// set solenoid values (placeholder values)
		israised = true;

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
		if (value == Value.kReverse){
			israised = true;
		}
		else{
			israised = false;
		}
	}

	public boolean isLifterRaised() {
		
		return israised;

	}


	public CommandBase toggleGrabberSolenoid() {
		return runOnce(() -> {
			sol1.toggle();
			System.out.println("Toggling Grabber solenoid"); // debug
		});
	}
	public CommandBase toggleLifter() {
		return runOnce(() -> {
			armsolenoid.toggle();
			System.out.println("Toggling Lifter solenoid"); // debug
			israised = !israised;
		});
	}

}
