package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.utilities.LiDAR;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.I2C;

@SuppressWarnings("unused")
public class Sensors extends SubsystemBase {

    private AHRS navx;
    private AnalogInput irSensor1;
    private ColorSensorV3 colourSensor;
    private LiDAR lidar;

    private RobotContainer container; // This classes reference to the RobotContainer

    public Sensors(RobotContainer container) {
        this.container = container;

        navx = new AHRS(SPI.Port.kMXP, (byte) 50);
        irSensor1 = new AnalogInput(2);
        lidar = new LiDAR(Port.kMXP);

        this.container.driveTrain.initOdometry(this);
    }

    public double getGyroX() {
        return navx.getRawGyroX();
    }

    @Override
    public void periodic() {
    }

    // We could just use 4096 for this, but this method will ensure accuracy if one changes

    // We could just use 4096 for this, but this method will ensure accuracy if one changes


    public double getGyroY() {
        return navx.getRawGyroY();
    }

    public double getGyroZ() {
        return navx.getRawGyroZ();
    }

    public double getIrVoltage() {
        double sensorVoltage = irSensor1.getVoltage();
        if (sensorVoltage > 4 || sensorVoltage < 0) { // Eliminate corrupt data
            sensorVoltage = 0;
        }
        return sensorVoltage;
    }


    }

    public double getLidarDistance() {
        return lidar.getDistance();
    }
    
}