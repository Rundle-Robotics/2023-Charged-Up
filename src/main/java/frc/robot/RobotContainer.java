package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {

	public static final CommandXboxController m_driverController = new CommandXboxController(
			OperatorConstants.kDriverControllerPort);

	/**
	 * This is how we create subsystems for our robot. Declare an instance of the
	 * subsystem class here, then bind any necessary actions in configureBindings()
	 */
	private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

	public RobotContainer() {
		// Configure the trigger bindings
		configureBindings();
	}

	/**
	 * Use this method to declare any actions that we want to happen. Examples:
	 * 1. Execute a new command whenever a subsystem method returns true
	 * 2. Execute a new command whenever a button is pressed on the Xbox controller
	 * 3. Call a method whenever a button is pressed on the Xbox controller by using
	 * an inline command
	 */
	private void configureBindings() {
		// Example 1.
		// Schedule `ExampleCommand` when `exampleCondition` changes to `true`
		new Trigger(m_exampleSubsystem::exampleCondition).onTrue(new ExampleCommand(m_exampleSubsystem));
		/*
		 * Create a new Trigger to handle this interaction and give the method for the
		 * trigger to "watch" like so:
		 * new Trigger(<subsystem variable>::<method name>)
		 * 
		 * Then, specify the condition we want our Trigger to act on (in our case we
		 * want an action whenever this method would return true, so we use .onTrue)
		 * All the conditions that Trigger can act on are given in the documentation:
		 * https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/wpilibj2/
		 * command/button/Trigger.html#debounce(double)
		 * 
		 * Finally, give the onTrue method the command we want it to execute as an
		 * argument.
		 */

		// Example 2.
		// Schedule `exampleMethodCommand` when the Xbox controller's B button is
		// pressed, cancelling on release.
		m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());
		/*
		 * m_driverController.b() returns the Trigger object which is tied to the B
		 * button on the Xbox controller. We can then bind a condition and an action to
		 * this Trigger just like in example 1.
		 * 
		 * In this example we will use the whileTrue condition (the method .whileTrue())
		 * This condition starts the given command when the button is first pressed
		 * (calls the init() method once), then calls execute() repeatedly while the
		 * button is held down and the command hasn't finished yet (don't worry about
		 * this, just put your code in execute()). The command is automatically
		 * cancelled/ended once the button is released (end() is called once)
		 * 
		 * This example shows how you can give a command produced by a method in the
		 * subsystem to the Trigger:
		 * m_exampleSubsystem.exampleMethodCommand() (this method returns a command)
		 */

		// Example 3.
		// Print "Hi!" to the console once when the Xbox controller's A button is
		// pressed
		m_driverController.a().onTrue(Commands.runOnce(() -> {
			System.out.println("Hi!");
		}, m_exampleSubsystem));
		/*
		 * NOTE: this method can quickly make your code hard to read, and this should
		 * only be used for quick fixes and quick tests.
		 * 
		 * Use this method as follows:
		 * Commands.runOnce(<lambda function with code to run>, <subsytem to bind to>)
		 * 
		 * For permanent/long-term code:
		 * Since this method requires a subsystem to bind to in the first place, you
		 * might as well create a method in that subsystem and move your code into that
		 * method! This will help keep configureBindings() clean and keep your code
		 * organized
		 */
	}

	public Command getAutonomousCommand() {
		// An example command will be run in autonomous
		return Autos.exampleAuto(m_exampleSubsystem);
	}
}
