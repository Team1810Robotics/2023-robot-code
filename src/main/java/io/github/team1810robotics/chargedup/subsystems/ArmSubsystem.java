package io.github.team1810robotics.chargedup.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static io.github.team1810robotics.chargedup.Constants.ArmConstants.*;

public class ArmSubsystem extends SubsystemBase {

    private final CANSparkMax armMotor;
    private final RelativeEncoder armEncoder;

    private final ProfiledPIDController pidController;
    private final ArmFeedforward feedforward;
    private TrapezoidProfile.State currentSetpoint;

    public ArmSubsystem() {
        armMotor = new CANSparkMax(LiftConstants.MOTOR_ID, MotorType.kBrushed);
        armMotor.setIdleMode(IdleMode.kBrake);
        armMotor.burnFlash();

        armEncoder = armMotor.getEncoder();

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

        armMotor.setVoltage(feedforward.calculate(setpoint, currentSetpoint.velocity)
            + pidController.calculate(armEncoder.getPosition(), setpoint));
    }

    public boolean atSetpoint() { // TODO: might break something ¯\_(ツ)_/¯
        return pidController.atGoal();
    }

    public void setSpeed(double speed) {
        double boundSpeed = MathUtil.clamp(speed, -1, 1);
        armMotor.set(boundSpeed);
    }

    public void stop() {
        armMotor.stopMotor();
    }
}
