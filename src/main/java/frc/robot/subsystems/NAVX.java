package frc.robot.subsystems;

//why is this not importing?
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import java.nio.ByteBuffer;
import edu.wpi.first.hal.I2CJNI;
import edu.wpi.first.wpilibj.I2C.Port;
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
    public static NAVX NavX = new NAVX();

    public NAVX() {

        navx = new AHRS(SPI.Port.kMXP, (byte) 50);
        irSensor1 = new AnalogInput(2);
        lidar = new LiDAR(Port.kMXP);

    }

    public class LiDAR {

        private static final byte k_deviceAddress = 0x62;
    
        private final byte m_port;
    
        private final ByteBuffer m_buffer = ByteBuffer.allocateDirect(2);
    
        public LiDAR(Port port) {
            m_port = (byte) port.value;
            I2CJNI.i2CInitialize(m_port);
        }
    
        public void startMeasuring() {
            writeRegister(0x04, 0x08 | 32); // default plus bit 5
            writeRegister(0x11, 0xff);
            writeRegister(0x00, 0x04);
        }
    
        public void stopMeasuring() {
            writeRegister(0x11, 0x00);
        }
    
        public short getDistance() {
            startMeasuring();
            short dist = readShort(0x8f);
            stopMeasuring();
            return dist;
        }
    
        private int writeRegister(int address, int value) {
            m_buffer.put(0, (byte) address);
            m_buffer.put(1, (byte) value);
    
            return I2CJNI.i2CWrite(m_port, k_deviceAddress, m_buffer, (byte) 2);
        }
    
        public short readShort(int address) {
            m_buffer.put(0, (byte) address);
            I2CJNI.i2CWrite(m_port, k_deviceAddress, m_buffer, (byte) 1);
            I2CJNI.i2CRead(m_port, k_deviceAddress, m_buffer, (byte) 2);
            return m_buffer.getShort(0);
        }
    
    }

    @Override
    public void periodic() {

        SmartDashboard.putNumber("gyroX", getGyroX());
        SmartDashboard.putNumber("gyroY", getGyroY());
        SmartDashboard.putNumber("gyroZ", getGyroZ());
        SmartDashboard.putNumber("Pitch", getPitch());
        SmartDashboard.putNumber("Roll",  getRoll());
        SmartDashboard.putNumber("Yaw",   getYaw());
        SmartDashboard.putNumber("Lidar Distance", getLidarDistance());
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

    public double getPitch() {
        return navx.getPitch();
    }

    public double getRoll() {
        return navx.getRoll();
    }

    public static double getYaw() {
        return NavX.getYaw();
    }

    public double getIrVoltage() {
        double sensorVoltage = irSensor1.getVoltage();
        if (sensorVoltage > 4 || sensorVoltage < 0) { // Eliminate corrupt data
            sensorVoltage = 0;
        }
        return sensorVoltage;
    }

    public double getLidarDistance() {
        return lidar.getDistance();
    }

    

}