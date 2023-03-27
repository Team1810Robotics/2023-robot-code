package io.github.team1810robotics.chargedup;

import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.sensors.AbsoluteSensorRange;
import com.ctre.phoenix.sensors.CANCoderConfiguration;
import com.ctre.phoenix.sensors.SensorInitializationStrategy;
import com.ctre.phoenix.sensors.SensorTimeBase;

import static io.github.team1810robotics.chargedup.Constants.*;

public final class CTREConfigs {
    public TalonFXConfiguration swerveAngleFXConfig;
    public TalonFXConfiguration swerveDriveFXConfig;
    public CANCoderConfiguration swerveCanCoderConfig;

    public CTREConfigs() {
        swerveAngleFXConfig = new TalonFXConfiguration();
        swerveDriveFXConfig = new TalonFXConfiguration();
        swerveCanCoderConfig = new CANCoderConfiguration();

        /* Swerve Angle Motor Configurations */
        SupplyCurrentLimitConfiguration angleSupplyLimit = new SupplyCurrentLimitConfiguration(
            DriveConstants.STEER_ENABLE_CURRENT_LIMIT,
            DriveConstants.STEER_CONTINUOUS_CURRENT_LIMIT,
            DriveConstants.STEER_PEAK_CURRENT_LIMIT,
            DriveConstants.STEER_PEAK_CURRENT_DURATION);

        swerveAngleFXConfig.slot0.kP = DriveConstants.STEER_kP;
        swerveAngleFXConfig.slot0.kI = DriveConstants.STEER_kI;
        swerveAngleFXConfig.slot0.kD = DriveConstants.STEER_kD;
        swerveAngleFXConfig.slot0.kF = DriveConstants.STEER_kF;
        swerveAngleFXConfig.supplyCurrLimit = angleSupplyLimit;

        /* Swerve Drive Motor Configuration */
        SupplyCurrentLimitConfiguration driveSupplyLimit = new SupplyCurrentLimitConfiguration(
            DriveConstants.DRIVE_ENABLE_CURRENT_LIMIT,
            DriveConstants.DRIVE_CONTINUOUS_CURRENT_LIMIT,
            DriveConstants.DRIVE_PEAK_CURRENT_LIMIT,
            DriveConstants.DRIVE_PEAK_CURRENT_DURATION);

        swerveDriveFXConfig.slot0.kP = DriveConstants.DRIVE_kP;
        swerveDriveFXConfig.slot0.kI = DriveConstants.DRIVE_kI;
        swerveDriveFXConfig.slot0.kD = DriveConstants.DRIVE_kD;
        swerveDriveFXConfig.slot0.kF = DriveConstants.DRIVE_kF;
        swerveDriveFXConfig.supplyCurrLimit = driveSupplyLimit;
        swerveDriveFXConfig.openloopRamp = DriveConstants.OPEN_LOOP_RAMP;
        swerveDriveFXConfig.closedloopRamp = DriveConstants.CLOSED_LOOP_RAMP;

        /* Swerve CANCoder Configuration */
        swerveCanCoderConfig.absoluteSensorRange = AbsoluteSensorRange.Unsigned_0_to_360;
        swerveCanCoderConfig.sensorDirection = DriveConstants.CAN_CODER_INVERT;
        swerveCanCoderConfig.initializationStrategy = SensorInitializationStrategy.BootToAbsolutePosition;
        swerveCanCoderConfig.sensorTimeBase = SensorTimeBase.PerSecond;
    }
}