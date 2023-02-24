package io.github.team1810robotics.chargedup.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static io.github.team1810robotics.chargedup.Constants.ArmConstants.*;

public class ArmSubsystem extends SubsystemBase {

    private final VictorSPX armMotor;
    private final DutyCycleEncoder armEncoder;

    private final ProfiledPIDController pidController;
    private final ArmFeedforward feedforward;
    private TrapezoidProfile.State currentSetpoint;

    public ArmSubsystem() {
        armMotor = new VictorSPX(LiftConstants.MOTOR_ID);
        armMotor.setNeutralMode(NeutralMode.Brake);

        armEncoder = new DutyCycleEncoder(LiftConstants.ENCODER_PORT);
        armEncoder.setDistancePerRotation(1440);

        pidController =
            new ProfiledPIDController(LiftConstants.kP,
                                      LiftConstants.kI,
                                      LiftConstants.kD,
                                      LiftConstants.CONSTRAINTS);

        feedforward =
            new ArmFeedforward(LiftConstants.kS,
                               LiftConstants.kG,
                               LiftConstants.kV,
                               LiftConstants.kA);

        currentSetpoint = new TrapezoidProfile.State(Math.PI / 2, 0);
    }

    public void setpoint(double setpoint) {
        pidController.setGoal(setpoint);
        // TODO: look at this?
        var profile = new TrapezoidProfile(LiftConstants.CONSTRAINTS,
                                           new TrapezoidProfile.State(setpoint, 0),
                                           currentSetpoint);

        currentSetpoint = profile.calculate(LiftConstants.DELTA_TIME);

        armMotor.set(ControlMode.Velocity, currentSetpoint.velocity,
                     DemandType.ArbitraryFeedForward,
                     feedforward.calculate(getEncoderRadians(), setpoint));
    }

    public boolean atSetpoint() { // TODO: might break something ¯\_(ツ)_/¯
        return pidController.atGoal();
    }

    public double getEncoderDistance() {
        return armEncoder.getDistance();
    }

    public double getEncoderRadians() {
        return Math.toRadians(getEncoderDistance() / 4);
    }

    public void setSpeed(double speed) {
        double boundSpeed = MathUtil.clamp(speed, -1, 1);
        armMotor.set(ControlMode.PercentOutput, boundSpeed);
    }

    public void stop() {
        armMotor.set(ControlMode.PercentOutput, 0);
    }
}
