package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.ControlConstants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.Drivetrain;
import frc.robot.RobotContainer;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxRelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class SparkMax {
    private static final int I = 0;
    public CANSparkMax m;
    public RelativeEncoder e;

    public SparkMax() {
        m = new CANSparkMax(0,MotorType.kBrushless);
        e = m.getEncoder();
        while ( I == 0) {
            public double v = e.getVelocity();
            public double p = e.getPosition();
            double CPR = e.getCountsPerRevolution();
        }   
    }
}
@Override
	public void periodic() {            
        SmartDashboard.putNumber("Velocity", v);

    }