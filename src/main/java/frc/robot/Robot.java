// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the
 * name of this class or
 * the package after creating this project, you must also update the
 * build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

	private CANSparkMax frontLeft = new CANSparkMax(0, MotorType.kBrushless);
	private CANSparkMax frontRight = new CANSparkMax(1, MotorType.kBrushless);
	private CANSparkMax backLeft = new CANSparkMax(2, MotorType.kBrushless);
	private CANSparkMax backRight = new CANSparkMax(3, MotorType.kBrushless);

	private Joystick joy1 = new Joystick(0);

	/*change to "false" if the robot is not stopping at the setpoint and going even faster.
	We want motor outputs and encoder readings to both be positive.
	Disable real fast if this goes wrong.
	*/
	
	private Encoder encoder = new Encoder (0, 1, true, EncodingType.k4X);
	private final double kDriveTick2Feet = 1.0 / 128 * 6 * Math.PI / 12;

	private Command m_autonomousCommand;

	private RobotContainer m_robotContainer;

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any
	 * initialization code.
	 */
	@Override
	public void robotInit() {
		// Instantiate our RobotContainer. This will perform all our button bindings,
		// and put our
		// autonomous chooser on the dashboard.
		m_robotContainer = new RobotContainer();
	}

	/**
	 * This function is called every 20 ms, no matter the mode. Use this for items
	 * like diagnostics
	 * that you want ran during disabled, autonomous, teleoperated and test.
	 *
	 * <p>
	 * This runs after the mode specific periodic functions, but before LiveWindow
	 * and
	 * SmartDashboard integrated updating.
	 */
	@Override
	public void robotPeriodic() {
		// Runs the Scheduler. This is responsible for polling buttons, adding
		// newly-scheduled
		// commands, running already-scheduled commands, removing finished or
		// interrupted commands,
		// and running subsystem periodic() methods. This must be called from the
		// robot's periodic
		// block in order for anything in the Command-based framework to work.
		CommandScheduler.getInstance().run();

		//smartdashboard is our friend for PID. Helps find kP
		SmartDashboard.putNumber("encoder", encoder.get()* kDriveTick2Feet);

	}

	/** This function is called once each time the robot enters Disabled mode. */
	@Override
	public void disabledInit() {
	}

	@Override
	public void disabledPeriodic() {
	}

	/**
	 * This autonomous runs the autonomous command selected by your
	 * {@link RobotContainer} class.
	 */
	@Override
	public void autonomousInit() {
		encoder.reset();
		m_autonomousCommand = m_robotContainer.getAutonomousCommand();

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.schedule();
		}
	}

	//large kP values cause oscillation
	final double kP = 0.222;

	double setpoint = 0;

	/** This function is called periodically during autonomous. */
	@Override
	public void autonomousPeriodic() {

		//get joystick command
		if (joy1.getRawButton(1)) {
			setpoint = 3;
		} else if (joy1.getRawButton(2)){
			setpoint = 0;
		}

		//get sensor position
		double sensorPosition = encoder.get() * kDriveTick2Feet;

		//calculations
		double error = setpoint -sensorPosition;

		double outputSpeed = kP * error;

		//output to motoes
		frontLeft.set(outputSpeed);
		backLeft.set(outputSpeed);
		frontRight.set(-outputSpeed);
		backRight.set(-outputSpeed);
	}

	
	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
	}

	/** This function is called periodically during operator control. */
	@Override
	public void teleopPeriodic() {
	}

	@Override
	public void testInit() {
		// Cancels all running commands at the start of test mode.
		CommandScheduler.getInstance().cancelAll();
	}

	/** This function is called periodically during test mode. */
	@Override
	public void testPeriodic() {
	}

	/** This function is called once when the robot is first started up. */
	@Override
	public void simulationInit() {
	}

	/** This function is called periodically whilst in simulation. */
	@Override
	public void simulationPeriodic() {
	}
}
