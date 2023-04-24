package io.github.team1810robotics.chargedup;

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
 * <pre> {@code import static io.github.team1810robotics.chargedup.Constants.*;} </pre>
 */
public final class Constants {
    // TODO: remove
    public static final int DISTANCE_SENSOR = 0;

    // constants need for drive
    public static final class DriveConstants {
        public static final int PIGEON_ID = 13;
        public static final boolean INVERT_GYRO = false; // Always ensure Gyro is CCW+ CW-

        // get the constants for the module we are using (an SDS MK4 L2)
        public static final COTSFalconSwerveConstants CHOSEN_MODULE =
            COTSFalconSwerveConstants.SDSMK4(COTSFalconSwerveConstants.driveGearRatios.SDSMK4_L2);

        /* Drivetrain Constants */
        public static final double TRACKWIDTH = 0.74; // distance between the front and back wheels
        public static final double WHEELBASE = 0.74; // distance between the right and left wheels
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
        // these come from sysid
        public static final double DRIVE_kP = 0.35444;
        public static final double DRIVE_kI = 0.0;
        public static final double DRIVE_kD = 0.0;
        public static final double DRIVE_kF = 0.0;

        /* Drive Motor Characterization Values
         * Divide SYSID values by 12 to convert from volts to percent output for CTRE */
        // divide by 12 is a lie actually but im really not sure why
        public static final double DRIVE_kS = 0.29170;
        public static final double DRIVE_kV = 2.16650;
        public static final double DRIVE_kA = 0.46999;

        /* Swerve Profiling Values */
        /** Meters per Second */
        public static final double MAX_SPEED = 2;
        /** Radians per Second */
        public static final double MAX_ANGULAR_VELOCITY = Math.PI / 3.2; // everyones favorite unit circle constant π/3.2

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
        /* max speed for bot during auto paths */
        public static final double MAX_SPEED = 2.2;
        public static final double MAX_ACCELERATION = 2;

        // the ticks off that the extender can be before the command tells it to stop
        public static final int EXTENDER_DEADBAND = 100;

        // tested values to figure out the extension amount to score each place
        public static final int CUBE_HIGH_EXTENDER = 4300;
        public static final int CUBE_MID_EXTENDER = 0;
        public static final int CONE_HIGH_EXTENDER = 10000; // set super high because hitting the ls is more consistent
        public static final int CONE_MID_EXTENDER = 1500;
        public static final int CUBE_FLOOR_EXTENDER = 4000;
    }

    public static final class ArmConstants {
        // diffrent angle values for the arm
        public static final double RESET  = Math.toRadians(90);
        public static final double LOW    = Math.toRadians(-30);
        public static final double MEDIUM = Math.toRadians(22);
        public static final double HIGH   = Math.toRadians(33);
        public static final double SUBSTATION_HIGH = Math.toRadians(38.8);

        /** normal constants */
        public static final class IntakeConstants {
            public static final int MOTOR_ID = 14;
            public static final boolean MOTOR_INVERTED = false;

            public static final int LINE_BREAK_PORT = 3;

            public static final String CAMERA_NAME = "OV5781";
        }

        public static final class ExtenderConstants {
            public static final int MOTOR_ID = 0;
            public static final int ENCODER_PORTS[] = {6, 7};

            // just used for shuffleboard. not an accurate value
            public static final int MAX_OUT = 5000;

            public static final int CLOSE_LS = 5; // port number
            public static final int FAR_LS = 4; // port number
        }

        /**
         * Lift is really what will soon become arm but i made the parent class
         * called arm so this will remain lift
         */
        public static final class LiftConstants {
            public static final int MOTOR_ID = 16;

            // quadrature encoder clicks per revolution
            public static final int ENCODER_CPR = 1536;
            // not used. . .
            public static final double ENCODER_DISTANCE_PER_PULSE = (2 * Math.PI) / ENCODER_CPR;

            /* `ENCODER_OFFSET` is better explained in `ArmSubsystem#setPosition()` */
            public static final int ENCODER_OFFSET = 550;
            // the expected initial position of the arm
            public static final double ARM_INITIAL = Math.PI / 2;
            // also better in `ArmSubsystem#setPosition()`
            public static final double RADIAN_OFFSET = ENCODER_OFFSET * ((2 * Math.PI) / LiftConstants.GEAR_RATIO);

            // current limit of the motor controller
            public static final int CURRENT_LIMIT = 15;

            // gear ratio between the arm and the encoder
            public static final double GEAR_RATIO = (4 / 1);
            // find the conversion rate from CPR to radians
            public static final double ENCODER_POSITION_FACTOR = ((2 * Math.PI) / GEAR_RATIO); // radians
            // find the conversion rate from CPR to radians per second
            public static final double ENCODER_VELOCITY_FACTOR = ENCODER_POSITION_FACTOR / 60; // rad/s

            /* because we couldnt get sysid to work these values are a combo
             * of re-calc and so good ole SWAGs. that is why the values are so round
             */
            public static final double kS = 0.50;
            public static final double kV = 0.65;
            public static final double kA = 0.10;

            // the kp basically says move at max speed until you are less than 1/4 rad off
            public static final double kP = 48;
            public static final double kI = 0;
            public static final double kD = 0;

            /*
             * the arm could never actually go this fast. if i had more time i
             * would have rewritten the `armSubsystem` so that it didnt need to
             * use constraints but the quick in-comp fix to speed up the arm
             * was to just put the max way higher than the arm could ever get
             */
            private static final double MAX_SPEED = 10; // rad/s
            private static final double MAX_ACCEL = 10; // rad/s/s
            public static final TrapezoidProfile.Constraints CONSTRAINTS =
                new TrapezoidProfile.Constraints(MAX_SPEED, MAX_ACCEL);
        }
    }

    /** normal constants */
    public static final class OIConstants {
        public static final int MOVEMENT_JOYSTICK_PORT = 0;
        public static final int ROTATION_JOYSTICK_PORT = 1;
        public static final int MANIPULATOR_CONTROLLER_PORT = 2;

        public static final double DEADBAND = 0.3;
    }
}
