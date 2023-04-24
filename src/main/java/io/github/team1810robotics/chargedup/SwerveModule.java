package io.github.team1810robotics.chargedup;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import io.github.team1810robotics.lib.math.Conversions;
import io.github.team1810robotics.lib.util.CTREModuleState;
import io.github.team1810robotics.lib.util.SwerveModuleConstants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.CANCoder;

import static io.github.team1810robotics.chargedup.Constants.*;

/** class that repersents one indiviual swerve module */
public class SwerveModule {
    public int moduleNumber;
    private Rotation2d angleOffset;
    private Rotation2d lastAngle;

    public TalonFX steerMotor;
    public TalonFX driveMotor;
    private CANCoder canCoder;

    // put a feedforward on the module so a really small input doesnt get eliminated by friction
    // this a simple FF because it doesnt need to account for gravity or any outside force other than friction
    SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(DriveConstants.DRIVE_kS, DriveConstants.DRIVE_kV, DriveConstants.DRIVE_kA);

    public SwerveModule(int moduleNumber, SwerveModuleConstants moduleConstants) {
        this.moduleNumber = moduleNumber;
        this.angleOffset = moduleConstants.angleOffset;

        /* Angle Encoder Config */
        canCoder = new CANCoder(moduleConstants.cancoderID);
        configAngleEncoder();

        /* Angle Motor Config */
        steerMotor = new TalonFX(moduleConstants.angleMotorID);
        configAngleMotor();

        /* Drive Motor Config */
        driveMotor = new TalonFX(moduleConstants.driveMotorID);
        configDriveMotor();

        lastAngle = getState().angle;
    }

    /** each module has the ablity to goto any state requested with the
     * `SwerveModuleState` class
     */
    public void setDesiredState(SwerveModuleState desiredState, boolean isOpenLoop) {
        /* This is a custom optimize function, since default WPILib optimize assumes continuous controller which CTRE and Rev onboard is not */
        // optimization is needed to keep the module from ever rotating more than 90°
        desiredState = CTREModuleState.optimize(desiredState, getState().angle);
        setAngle(desiredState);
        setSpeed(desiredState, isOpenLoop);
    }

    /**
     * set speed fn for the module
     * @param desiredState the object containg the requested speed
     * @param isOpenLoop used to decide if feed forward should be used or not
     */
    private void setSpeed(SwerveModuleState desiredState, boolean isOpenLoop) {
        if (isOpenLoop) {                                               // this is dumb as auto doesnt even use the set speed method. once again why did i write this??
            double percentOutput = desiredState.speedMetersPerSecond / ((DriverStation.isAutonomousEnabled()) ? AutoConstants.MAX_SPEED : DriveConstants.MAX_SPEED);
            driveMotor.set(ControlMode.PercentOutput, percentOutput);
        } else {
            // the falcon uses a weird unit for velocity so we convert it to the correct units
            double velocity = Conversions.MPSToFalcon(desiredState.speedMetersPerSecond, DriveConstants.WHEEL_CIRCUMFERENCE, DriveConstants.DRIVE_GEAR_RATIO);
            // set the velocity of the motor to requested velocity and apply feed forward
            driveMotor.set(ControlMode.Velocity, velocity, DemandType.ArbitraryFeedForward, feedforward.calculate(desiredState.speedMetersPerSecond));
        }
    }

    /**
     * set angle fn for the module
     * @param desiredState the object containg the requested angle
     */
    private void setAngle(SwerveModuleState desiredState) {
        // Prevent rotating module if speed is less then 1%. Prevents Jittering
        Rotation2d angle = (Math.abs(desiredState.speedMetersPerSecond) <= (DriveConstants.MAX_SPEED * 0.01)) ? lastAngle : desiredState.angle;

        // set the angle of the module       // convert from degrees to the falcon units because falcons use a weird unit
        steerMotor.set(ControlMode.Position, Conversions.degreesToFalcon(angle.getDegrees(), DriveConstants.STEER_GEAR_RATIO));
        // save the angle so we can do the above check again
        lastAngle = angle;
    }

    /** @return the angle of the angle of the module as a `Rotation2d` */
    private Rotation2d getAngle() {
        return Rotation2d.fromDegrees(Conversions.falconToDegrees(steerMotor.getSelectedSensorPosition(), DriveConstants.STEER_GEAR_RATIO));
    }

    /** @return the value of the CANCoder as a `Rotation2d` */
    public Rotation2d getCanCoder() {
        return Rotation2d.fromDegrees(canCoder.getAbsolutePosition());
    }

    /** resets the steer motor to the value of the CANCoder */
    private void resetToAbsolute() {
        double absolutePosition = Conversions.degreesToFalcon(getCanCoder().getDegrees() - angleOffset.getDegrees(), DriveConstants.STEER_GEAR_RATIO);
        steerMotor.setSelectedSensorPosition(absolutePosition);
    }

    /** helper fn for setting the configs for the CANCoder */
    private void configAngleEncoder() {
        canCoder.configFactoryDefault();
        canCoder.configAllSettings(Robot.ctreConfigs.swerveCanCoderConfig);
    }

    /** helper fn for setting the configs for the steer motor */
    private void configAngleMotor() {
        steerMotor.configFactoryDefault();
        steerMotor.configAllSettings(Robot.ctreConfigs.swerveAngleFXConfig);
        steerMotor.setInverted(DriveConstants.STEER_MOTOR_INVERT);
        steerMotor.setNeutralMode(DriveConstants.STEER_NEUTRAL_MODE);
        resetToAbsolute();
        // https://github.com/Team364/BaseFalconSwerve/issues/8#issuecomment-1384600950
        Timer.delay(1.0);
    }

    /** helper fn for setting the configs for the drive motor */
    private void configDriveMotor() {
        driveMotor.configFactoryDefault();
        driveMotor.configAllSettings(Robot.ctreConfigs.swerveDriveFXConfig);
        driveMotor.setInverted(DriveConstants.DRIVE_MOTOR_INVERT);
        driveMotor.setNeutralMode(DriveConstants.DRIVE_NEUTRAL_MODE);
        driveMotor.setSelectedSensorPosition(0);
    }

    /**
     * @return the state of the module<p>
     * state contains a vector the represents the module
     */
    public SwerveModuleState getState() {
        return new SwerveModuleState(
            Conversions.falconToMPS(driveMotor.getSelectedSensorVelocity(), DriveConstants.WHEEL_CIRCUMFERENCE, DriveConstants.DRIVE_GEAR_RATIO), 
            getAngle()
        );
    }

    /**
     * @return the position of the module<p>
     * position repesents the distance travled and the angle of the module
     */
    public SwerveModulePosition getPosition() {
        return new SwerveModulePosition(
            Conversions.falconToMeters(driveMotor.getSelectedSensorPosition(), DriveConstants.WHEEL_CIRCUMFERENCE, DriveConstants.DRIVE_GEAR_RATIO), 
            getAngle()
        );
    }

    /** stops both of the module motors */
    public void stop() {
        driveMotor.set(ControlMode.PercentOutput, 0);
        steerMotor.set(ControlMode.PercentOutput, 0);
    }
}