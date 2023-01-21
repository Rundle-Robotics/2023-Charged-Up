// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

  public static class OperatorConstants {
    public static final int DRIVER_CONTROLLER_PORT = 1;
    public static final int XBOX_LEFT_Y_AXIS = 1;
    public static final int XBOX_RIGHT_Y_AXIS = 5;
    public static final int XBOX_LEFT_X_AXIS = 0;
    public static final int XBOX_RIGHT_X_AXIS = 4;
    public static final int XBOX_LEFT_BUMBER = 6;
    public static final int XBOX_RIGHT_BUMPER = 5;
    public static final int XBOX_A_BUTTON = 1;
    public static final int XBOX_B_BUTTON = 2;
    public static final int XBOX_X_BUTTON = 3;
    public static final int XBOX_Y_BUTTON = 4;
    public static final int XBOX_BACK_BUTTON = 7;
    public static final int XBOX_START_BUTTON = 8;
  }

  public static class ControlConstants {
    public static final double JOY_DEADBAND = 0.15;
    public static final double ROTATION_MULT = 0.65;
    public static final double ROTATION_DEADBAND = 0.1;
    public static final double MAX_ROBOT_SPEED = 1;
    public static final double MIN_ROBOT_SPEED = -1;
    public static final double MAX_TURN_SPEED = 0.7;
  }
}
