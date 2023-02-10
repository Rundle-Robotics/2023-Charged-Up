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
	private double apriltagid;

	public Limelight() {
		table = NetworkTableInstance.getDefault().getTable("limelight");
		disableLimelight();
	}

	public void enableLimelight() {
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

		apriltagid = table.getEntry("tid").getDouble(0.0);

		SmartDashboard.putNumber("x offset", tx);
		SmartDashboard.putNumber("y offset", ty);
		SmartDashboard.putNumber("area", ta);

		SmartDashboard.putNumber("TId", apriltagid);

		limelightCenter();

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

	public void limelightCenter() {

	}

	public void setPipeline(int n) {
		table.getEntry("pipeline").setNumber(n);
	}

}
