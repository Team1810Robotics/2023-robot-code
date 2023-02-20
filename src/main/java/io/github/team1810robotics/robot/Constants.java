package io.github.team1810robotics.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import io.github.team1810robotics.lib.util.COTSFalconSwerveConstants;
import io.github.team1810robotics.lib.util.SwerveModuleConstants;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 * <p>
 * For example:
 * <pre> {@code import static io.github.team1810robotics.robot.Constants.*;} </pre>
 */
public final class Constants {
    public static final class DriveConstants {
        public static final int PIGEON_ID = 13;
        public static final boolean INVERT_GYRO = false; // Always ensure Gyro is CCW+ CW-

        public static final COTSFalconSwerveConstants CHOSEN_MODULE =
            COTSFalconSwerveConstants.SDSMK4(COTSFalconSwerveConstants.driveGearRatios.SDSMK4_L2);

        /* Drivetrain Constants */
        public static final double TRACKWIDTH = 0.74;
        public static final double WHEELBASE = 0.74;
        public static final double WHEEL_CIRCUMFERENCE = CHOSEN_MODULE.wheelCircumference;

        public static final SwerveDriveKinematics SWERVE_KINEMATICS = new SwerveDriveKinematics(
            new Translation2d(WHEELBASE / 2.0, TRACKWIDTH / 2.0),
            new Translation2d(WHEELBASE / 2.0, -TRACKWIDTH / 2.0),
            new Translation2d(-WHEELBASE / 2.0, TRACKWIDTH / 2.0),
            new Translation2d(-WHEELBASE / 2.0, -TRACKWIDTH / 2.0));

        /* Module Gear Ratios */
        public static final double DRIVE_GEAR_RATIO = CHOSEN_MODULE.driveGearRatio;
        public static final double STEER_GEAR_RATIO = CHOSEN_MODULE.angleGearRatio;

        /* Motor Inverts */
        public static final boolean STEER_MOTOR_INVERT = CHOSEN_MODULE.angleMotorInvert;
        public static final boolean DRIVE_MOTOR_INVERT = CHOSEN_MODULE.driveMotorInvert;

        /* Angle Encoder Invert */
        public static final boolean CAN_CODER_INVERT = CHOSEN_MODULE.canCoderInvert;

        /* Swerve Current Limiting */
        public static final int STEER_CONTINUOUS_CURRENT_LIMIT = 25;
        public static final int STEER_PEAK_CURRENT_LIMIT = 40;
        public static final double STEER_PEAK_CURRENT_DURATION = 0.1;
        public static final boolean STEER_ENABLE_CURRENT_LIMIT = true;

        public static final int DRIVE_CONTINUOUS_CURRENT_LIMIT = 35;
        public static final int DRIVE_PEAK_CURRENT_LIMIT = 80;
        public static final double DRIVE_PEAK_CURRENT_DURATION = 3;
        public static final boolean DRIVE_ENABLE_CURRENT_LIMIT = true;

        /* These values are used by the drive falcon to ramp in open loop and closed loop driving.
         * We found a small open loop ramp (0.25) helps with tread wear, tipping, etc */
        public static final double OPEN_LOOP_RAMP = 0.25;
        public static final double CLOSED_LOOP_RAMP = 0.0;

        /* Angle Motor PID Values */
        public static final double STEER_kP = CHOSEN_MODULE.angleKP;
        public static final double STEER_kI = CHOSEN_MODULE.angleKI;
        public static final double STEER_kD = CHOSEN_MODULE.angleKD;
        public static final double STEER_kF = CHOSEN_MODULE.angleKF;

        /* Drive Motor PID Values */
        // TODO: This must be tuned after the arm is attached
        public static final double DRIVE_kP = 0.013031;
        public static final double DRIVE_kI = 0.0;
        public static final double DRIVE_kD = 0.0;
        public static final double DRIVE_kF = 0.0;

        /* Drive Motor Characterization Values
         * Divide SYSID values by 12 to convert from volts to percent output for CTRE */
        // TODO: This must be tuned after the arm is attached
        public static final double DRIVE_kS = (0.17 / 12);
        public static final double DRIVE_kV = (2.14 / 12);
        public static final double DRIVE_kA = (0.41 / 12);

        /* Swerve Profiling Values */
        /** Meters per Second */
        public static final double MAX_SPEED = 2.4;
        /** Radians per Second */
        public static final double MAX_ANGULAR_VELOCITY = Math.PI;

        /* Neutral Modes */
        public static final NeutralMode STEER_NEUTRAL_MODE = NeutralMode.Brake;
        public static final NeutralMode DRIVE_NEUTRAL_MODE = NeutralMode.Brake;

        /* Module Specific Constants */
        /* Front Left Module - Module 0 */
        public static final class FL {
            public static final int STEER_MOTOR_ID = 3;
            public static final int DRIVE_MOTOR_ID = 4;
            public static final int CAN_CODER_ID = 10;
            public static final Rotation2d ANGLE_OFFSET = Rotation2d.fromDegrees(131.22);
            public static final SwerveModuleConstants CONSTANTS =
                new SwerveModuleConstants(DRIVE_MOTOR_ID, STEER_MOTOR_ID, CAN_CODER_ID, ANGLE_OFFSET);
        }

        /* Front Right Module - Module 1 */
        public static final class FR {
            public static final int STEER_MOTOR_ID = 1;
            public static final int DRIVE_MOTOR_ID = 2;
            public static final int CAN_CODER_ID = 9;
            public static final Rotation2d ANGLE_OFFSET = Rotation2d.fromDegrees(194.15);
            public static final SwerveModuleConstants CONSTANTS =
                new SwerveModuleConstants(DRIVE_MOTOR_ID, STEER_MOTOR_ID, CAN_CODER_ID, ANGLE_OFFSET);
        }

        /* Back Left Module - Module 2 */
        public static final class BL {
            public static final int STEER_MOTOR_ID = 5;
            public static final int DRIVE_MOTOR_ID = 6;
            public static final int CAN_CODER_ID = 11;
            public static final Rotation2d ANGLE_OFFSET = Rotation2d.fromDegrees(51.33);
            public static final SwerveModuleConstants CONSTANTS =
                new SwerveModuleConstants(DRIVE_MOTOR_ID, STEER_MOTOR_ID, CAN_CODER_ID, ANGLE_OFFSET);
        }

        /* Back Right Module - Module 3 */
        public static final class BR {
            public static final int STEER_MOTOR_ID = 7;
            public static final int DRIVE_MOTOR_ID = 8;
            public static final int CAN_CODER_ID = 12;
            public static final Rotation2d ANGLE_OFFSET = Rotation2d.fromDegrees(276.5);
            public static final SwerveModuleConstants CONSTANTS =
                new SwerveModuleConstants(DRIVE_MOTOR_ID, STEER_MOTOR_ID, CAN_CODER_ID, ANGLE_OFFSET);
        }
    }

    public static final class AutoConstants {
        public static final double MAX_SPEED_METERS_PER_SECOND = 3;
        public static final double MAX_ACCELERATION_METERS_PER_SECOND_SQUARED = 3;
        public static final double MAX_ANGULAR_SPEED_RADIANS_PER_SECOND = Math.PI;
        public static final double MAX_ANGULAR_SPEED_RADIANS_PER_SECOND_SQUARED = Math.PI;
    }

    public static final class ArmConstants {
        public static final class IntakeConstants {
            public static final int MOTOR_ID = 14;
            public static final boolean MOTOR_INVERTED = false;

            public static final int LINE_BREAK_PORT = 2;

            public static final String CAMERA_NAME = "Intake Camera"; // TODO: ask Josh about name
            public static final int MIN_TARGET_AREA = 10; // %
        }

        public static final class ExtenderConstants {
            public static final int RELAY_ID = 0;
            public static final int ENCODER_PORTS[] = {0, 1}; // TODO: get real values

            public static final int LIMIT_SWITCH_PORTS[] = {5, 4};
        }

        public static final class LiftConstants {
            public static final int MOTOR_ID = 15;

            public static final double kP = 1; // TODO: tune
            public static final double kI = 0; // TODO: tune
            public static final double kD = 0; // TODO: tune

            public static final double kS = 0; // TODO: tune
            public static final double kG = 0; // TODO: tune
            public static final double kV = 0; // TODO: tune
            public static final double kA = 0; // TODO: tune

            private static final double MAX_SPEED = 0.5; // m/s
            private static final double MAX_ACCEL = 0.5; // m/s/s
            public static final TrapezoidProfile.Constraints CONSTRAINTS =
                new TrapezoidProfile.Constraints(MAX_SPEED, MAX_ACCEL);

            public static final double DELTA_TIME = 0.02;
        }
    }

    public static final class OIConstants {
        public static final int MOVEMENT_JOYSTICK_PORT = 0;
        public static final int ROTATION_JOYSTICK_PORT = 1;
        public static final int MANIPULATOR_CONTROLLER_PORT = 2;

        public static final double DEADBAND = 0.3;
    }
}
