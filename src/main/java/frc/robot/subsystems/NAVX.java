package frc.robot.subsystems;




import edu.wpi.first.wpilibj.DigitalInput;

import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class NAVX extends SubsystemBase {
 
    private static double blue;
    private static double red;
    private static double green;
    private static ColorSensorV3 colorSensor;
    public final static int threshold = 300;
    private static DigitalInput distanceSensor;
    private static DigitalInput distanceSensorFar;
    private static boolean shortDistance;
    private static boolean longDistance;

    private static int thresholdDistance = 800;



    private static String color;

    
    
    public NAVX() { 
        distanceSensor = new DigitalInput(1);
        distanceSensorFar = new DigitalInput(0);

        colorSensor = new ColorSensorV3(I2C.Port.kOnboard);
        color = "";
    }

    @Override
    public void periodic() {

        blue = colorSensor.getBlue();
        red = colorSensor.getRed();
        green = colorSensor.getGreen();
    }
    

    // determine the color from the values the sensor is detecting
    public static String determineColour() {
        
        //ranges between 0 and 2047, 800 chosen arbitrarily
        if (colorSensor.getProximity() > 700 && red > blue){
        color = "Red";
        } else if (colorSensor.getProximity() > 700 && blue > red){
        color = "Blue";
        } else {
        color = "Other";
        }

        SmartDashboard.putNumber("Proximity", colorSensor.getProximity());
        
        SmartDashboard.putNumber("red", red);
        SmartDashboard.putNumber("green", green);
        SmartDashboard.putNumber("blue", blue);

        SmartDashboard.putString("color detected", color);
        

        return color;
        
    }
}
