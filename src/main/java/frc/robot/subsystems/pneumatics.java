package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Pneumatics extends SubsystemBase {
	private DoubleSolenoid sol1;

	public Pneumatics() {
		// set solenoid values (placeholder values)
		sol1 = new DoubleSolenoid(PneumaticsModuleType.REVPH, 0, 1); 
		sol1.set(Value.kReverse);
	}

	@Override
	public void periodic() {
	}

	public void setClimber(Value value) {
		sol1.set(value);
	}

	public CommandBase toggleClimberSolenoid() {
		return runOnce(() -> {
			sol1.toggle();
			System.out.println("Toggling climber solenoid"); // debug
		});
	}
}
