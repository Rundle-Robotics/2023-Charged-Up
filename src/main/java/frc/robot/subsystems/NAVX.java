package frc.robot.subsystems;

//why is this not importing?
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
//this too...
import frc.robot.utilities.LiDAR;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.I2C;

@SuppressWarnings("unused")
public class NAVX extends SubsystemBase {

    private AHRS navx;
    private AnalogInput irSensor1;
    private LiDAR lidar;

    private RobotContainer container; // This classes reference to the RobotContainer

    public NAVX(RobotContainer container) {
        this.container = container;

        navx = new AHRS(SPI.Port.kMXP, (byte) 50);
        irSensor1 = new AnalogInput(2);
        lidar = new LiDAR(Port.kMXP);

        this.container.driveTrain.initOdometry(this);
    }



    @Override
    public void periodic() {
    }

    // We could just use 4096 for this, but this method will ensure accuracy if one changes

    // We could just use 4096 for this, but this method will ensure accuracy if one changes

    
    //I moved this into periodic idk if it should be here though
    public double getGyroX() {
        return navx.getRawGyroX();

    }

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