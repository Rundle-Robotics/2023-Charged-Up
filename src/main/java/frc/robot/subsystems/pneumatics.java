package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class pneumatics extends SubsystemBase {
    private DoubleSolenoid sol1;
    /** Creates a new Climber. */
    public pneumatics() {
  
      // set solenoid values (placeholder values)
      sol1 = new DoubleSolenoid(PneumaticsModuleType.REVPH, 0, 1);
      sol1.set(Value.kReverse);
  
    }
    @Override
    public void periodic() {
      // This method will be called once per scheduler run
      
      if (RobotContainer.driverController.getHID().getAButton()) {
        // if you press the b button, then go up
        sol1.toggle();
        System.out.println("A button presed");
      }
    }
    public void setClimber(Value value) {
      sol1.set(value);
      
    }
  }
