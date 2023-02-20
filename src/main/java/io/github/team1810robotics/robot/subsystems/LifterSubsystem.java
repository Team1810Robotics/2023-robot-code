package io.github.team1810robotics.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static io.github.team1810robotics.robot.Constants.ArmConstants.*;

public class LifterSubsystem extends SubsystemBase {

    private final CANSparkMax lifterMotor;
    private final RelativeEncoder lifterEncoder;

    private final ProfiledPIDController pidController;
    private final ArmFeedforward feedforward;
    private TrapezoidProfile.State currentSetpoint;

    public LifterSubsystem() {
        lifterMotor = new CANSparkMax(LiftConstants.MOTOR_ID, MotorType.kBrushless);
        lifterMotor.setIdleMode(IdleMode.kBrake);
        lifterMotor.burnFlash();

        lifterEncoder = lifterMotor.getEncoder();

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

        currentSetpoint = new TrapezoidProfile.State(0, 0);
    }

    public void setpoint(double setpoint) {
        // TODO: look at this?
        var profile = new TrapezoidProfile(LiftConstants.CONSTRAINTS,
                                           new TrapezoidProfile.State(setpoint, 0),
                                           currentSetpoint);

        currentSetpoint = profile.calculate(LiftConstants.DELTA_TIME);

        lifterMotor.setVoltage(feedforward.calculate(setpoint, currentSetpoint.velocity)
            + pidController.calculate(lifterEncoder.getPosition(), setpoint));
    }

    public void stop() {
        lifterMotor.stopMotor();
    }
}
