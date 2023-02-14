// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Limelight extends SubsystemBase {
	private static NetworkTable table;

	private double tx;
	private double ty;
	private double ta;
	private double tshort;
	private double tlong;
	private double[] targetpose_cameraspace;
	private double apriltagid;

	public Limelight() {
		table = NetworkTableInstance.getDefault().getTable("limelight");
		disableLimelight();
	}

	public static void enableLimelight() {
		table.getEntry("ledMode").setNumber(3);
	}

	public void disableLimelight() {
		table.getEntry("ledMode").setNumber(1);
	}

	@Override
	public void periodic() {
		tx = table.getEntry("tx").getDouble(0.0);
		ty = table.getEntry("tv").getDouble(0.0);
		ta = table.getEntry("ta").getDouble(0.0);

		tshort = table.getEntry("tshort").getDouble(0.0);
		tlong = table.getEntry("tlong").getDouble(0.0);
		targetpose_cameraspace = table.getEntry("targetpose_cameraspace").getDoubleArray(new double[6]);


		apriltagid = table.getEntry("tid").getDouble(0.0);

		SmartDashboard.putNumber("x offset", tx);
		SmartDashboard.putNumber("y offset", ty);
		SmartDashboard.putNumber("area", ta);

		SmartDashboard.putNumber("TId", apriltagid);

		SmartDashboard.putNumber("tshort", tshort);
		SmartDashboard.putNumber("tlong", tlong);
		SmartDashboard.putNumber("targetpose", targetpose_cameraspace[5]);


		limelightCenter();

	}

	public void putTargetPoseDataonSmartDashboard() {
		targetpose_cameraspace = getTARGETPOSECAMERA();
		SmartDashboard.putNumberArray("TPArray", targetpose_cameraspace);
		SmartDashboard.putNumber("TP0", targetpose_cameraspace[0]);
		SmartDashboard.putNumber("TP1", targetpose_cameraspace[1]);
		SmartDashboard.putNumber("TP2", targetpose_cameraspace[2]);
		SmartDashboard.putNumber("TP3", targetpose_cameraspace[3]);
		SmartDashboard.putNumber("TP4", targetpose_cameraspace[4]);
		SmartDashboard.putNumber("TP5", targetpose_cameraspace[5]);
	}


	public double getTX() {
		return table.getEntry("tx").getDouble(0.0);
	}

	public double getTV() {
		return table.getEntry("tv").getDouble(0.0);
	}

	public double getTA() {
		return table.getEntry("ta").getDouble(0.0);
	}
	public double getTSHORT() {
		return table.getEntry("tshort").getDouble(0.0);
	}

	public double getTLONG() {
		return table.getEntry("tlong").getDouble(0.0);
	}

	public double[] getTARGETPOSECAMERA() {
		return table.getEntry("targetpose_cameraspace").getDoubleArray(new double[6]);
	}

	public void limelightCenter() {

	}
	public void setPipeline(int n) {
		table.getEntry("pipeline").setNumber(n);
	}


}
