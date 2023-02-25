package io.github.team1810robotics.chargedup.subsystems;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.TrapezoidProfileSubsystem;

import static io.github.team1810robotics.chargedup.Constants.ArmConstants.*;

import com.revrobotics.CANSparkMax;
// import com.revrobotics.SparkMaxAlternateEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class ArmSubsystem extends TrapezoidProfileSubsystem {

    private final CANSparkMax motor;
    private final Encoder encoder;
    // private final SparkMaxAlternateEncoder encoder;

    private final ArmFeedforward feedforward;
    private final SparkMaxPIDController pid;

    public ArmSubsystem() {
        super(LiftConstants.CONSTRAINTS,
              LiftConstants.ARM_OFFSET);

        this.feedforward = new ArmFeedforward(LiftConstants.kS,
                                              LiftConstants.kG,
                                              LiftConstants.kV,
                                              LiftConstants.kA);

        this.motor = new CANSparkMax(LiftConstants.MOTOR_ID, MotorType.kBrushed);
        motor.restoreFactoryDefaults();

        this.encoder = new Encoder(LiftConstants.ENCODER_PORTS[0], LiftConstants.ENCODER_PORTS[1]);
        encoder.setDistancePerPulse(LiftConstants.ENCODER_DISTANCE_PER_PULSE);

        this.pid = motor.getPIDController();

        // TODO: make this work by changing how encoder interfaces
        // this.encoder = motor.getEncoder();
        // pid.setFeedbackDevice(encoder);

        // encoder.setPositionConversionFactor(LiftConstants.ENCODER_POSITION_FACTOR);
        // encoder.setVelocityConversionFactor(LiftConstants.ENCODER_VELOCITY_FACTOR);

        pid.setP(LiftConstants.kP);
        pid.setI(LiftConstants.kI);
        pid.setD(LiftConstants.kD);
        pid.setFF(LiftConstants.kF);
        pid.setOutputRange(-1, 1);

        motor.setIdleMode(IdleMode.kBrake);
        motor.setSmartCurrentLimit(LiftConstants.CURRENT_LIMIT);

        motor.burnFlash();
    }

    @Override
    public void useState(TrapezoidProfile.State setpoint) {
        var feed = feedforward.calculate(setpoint.position, setpoint.velocity);

        pid.setReference(setpoint.position - LiftConstants.ARM_OFFSET,
                         ControlType.kPosition, 0, feed);
    }

    public double getDistance() {
        return encoder.getDistance() - LiftConstants.ARM_OFFSET;
    }

    public void setSpeed(double speed) {
        double boundSpeed = MathUtil.clamp(speed, -1, 1);
        motor.set(-boundSpeed);
    }

    public void stop() {
        motor.stopMotor();
    }

}
