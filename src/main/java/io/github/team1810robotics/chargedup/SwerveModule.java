package io.github.team1810robotics.chargedup;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;

import io.github.team1810robotics.lib.math.Conversions;
import io.github.team1810robotics.lib.util.CTREModuleState;
import io.github.team1810robotics.lib.util.SwerveModuleConstants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.CANCoder;

import static io.github.team1810robotics.chargedup.Constants.*;

public class SwerveModule {
    public int moduleNumber;
    private Rotation2d angleOffset;
    private Rotation2d lastAngle;

    private TalonFX steerMotor;
    private TalonFX driveMotor;
    private CANCoder canCoder;

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

    public void setDesiredState(SwerveModuleState desiredState, boolean isOpenLoop) {
        /* This is a custom optimize function, since default WPILib optimize assumes continuous controller which CTRE and Rev onboard is not */
        desiredState = CTREModuleState.optimize(desiredState, getState().angle); 
        setAngle(desiredState);
        setSpeed(desiredState, isOpenLoop);
    }

    private void setSpeed(SwerveModuleState desiredState, boolean isOpenLoop) {
        if (isOpenLoop) {
            double percentOutput = desiredState.speedMetersPerSecond / DriveConstants.MAX_SPEED;
            driveMotor.set(ControlMode.PercentOutput, percentOutput);
        } else {
            double velocity = Conversions.MPSToFalcon(desiredState.speedMetersPerSecond, DriveConstants.WHEEL_CIRCUMFERENCE, DriveConstants.DRIVE_GEAR_RATIO);
            driveMotor.set(ControlMode.Velocity, velocity, DemandType.ArbitraryFeedForward, feedforward.calculate(desiredState.speedMetersPerSecond));
        }
    }

    private void setAngle(SwerveModuleState desiredState) {
        Rotation2d angle = (Math.abs(desiredState.speedMetersPerSecond) <= (DriveConstants.MAX_SPEED * 0.01)) ? lastAngle : desiredState.angle; //Prevent rotating module if speed is less then 1%. Prevents Jittering.

        steerMotor.set(ControlMode.Position, Conversions.degreesToFalcon(angle.getDegrees(), DriveConstants.STEER_GEAR_RATIO));
        lastAngle = angle;
    }

    private Rotation2d getAngle() {
        return Rotation2d.fromDegrees(Conversions.falconToDegrees(steerMotor.getSelectedSensorPosition(), DriveConstants.STEER_GEAR_RATIO));
    }

    public Rotation2d getCanCoder() {
        return Rotation2d.fromDegrees(canCoder.getAbsolutePosition());
    }

    private void resetToAbsolute() {
        double absolutePosition = Conversions.degreesToFalcon(getCanCoder().getDegrees() - angleOffset.getDegrees(), DriveConstants.STEER_GEAR_RATIO);
        steerMotor.setSelectedSensorPosition(absolutePosition);
    }

    private void configAngleEncoder() {
        canCoder.configFactoryDefault();
        canCoder.configAllSettings(Robot.ctreConfigs.swerveCanCoderConfig);
    }

    private void configAngleMotor() {
        steerMotor.configFactoryDefault();
        steerMotor.configAllSettings(Robot.ctreConfigs.swerveAngleFXConfig);
        steerMotor.setInverted(DriveConstants.STEER_MOTOR_INVERT);
        steerMotor.setNeutralMode(DriveConstants.STEER_NEUTRAL_MODE);
        resetToAbsolute();
    }

    private void configDriveMotor() {
        driveMotor.configFactoryDefault();
        driveMotor.configAllSettings(Robot.ctreConfigs.swerveDriveFXConfig);
        driveMotor.setInverted(DriveConstants.DRIVE_MOTOR_INVERT);
        driveMotor.setNeutralMode(DriveConstants.DRIVE_NEUTRAL_MODE);
        driveMotor.setSelectedSensorPosition(0);
    }

    public SwerveModuleState getState() {
        return new SwerveModuleState(
            Conversions.falconToMPS(driveMotor.getSelectedSensorVelocity(), DriveConstants.WHEEL_CIRCUMFERENCE, DriveConstants.DRIVE_GEAR_RATIO), 
            getAngle()
        ); 
    }

    public SwerveModulePosition getPosition() {
        return new SwerveModulePosition(
            Conversions.falconToMeters(driveMotor.getSelectedSensorPosition(), DriveConstants.WHEEL_CIRCUMFERENCE, DriveConstants.DRIVE_GEAR_RATIO), 
            getAngle()
        );
    }

    public void stop() {
        driveMotor.set(ControlMode.PercentOutput, 0);
        steerMotor.set(ControlMode.PercentOutput, 0);
    }
}