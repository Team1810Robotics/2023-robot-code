package io.github.team1810robotics.chargedup.subsystems;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.TrapezoidProfileSubsystem;
import io.github.team1810robotics.lib.util.ArmFeedforward;

import static io.github.team1810robotics.chargedup.Constants.ArmConstants.*;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxRelativeEncoder.Type;

public class ArmSubsystem extends TrapezoidProfileSubsystem {

    private final CANSparkMax motor;
    private final RelativeEncoder encoder;

    private final ArmFeedforward feedforward;
    private final SparkMaxPIDController pid;

    public ArmSubsystem() {
        super(LiftConstants.CONSTRAINTS,
              LiftConstants.ARM_OFFSET);

        this.feedforward = new ArmFeedforward(this::calculateKs,
                                              this::calculateKg,
                                              this::calculateKv,
                                              this::calculateKa);

        this.motor = new CANSparkMax(LiftConstants.MOTOR_ID, MotorType.kBrushed);
        motor.restoreFactoryDefaults();
        motor.setInverted(false);

        this.pid = motor.getPIDController();

        this.encoder = motor.getEncoder(Type.kQuadrature, LiftConstants.ENCODER_CPR);
        encoder.setInverted(true);
        encoder.setPosition(LiftConstants.ENCODER_OFFSET);
        pid.setFeedbackDevice(encoder);

        encoder.setPositionConversionFactor(LiftConstants.ENCODER_POSITION_FACTOR);
        encoder.setVelocityConversionFactor(LiftConstants.ENCODER_VELOCITY_FACTOR);

        pid.setP(LiftConstants.kP);
        pid.setI(LiftConstants.kI);
        pid.setD(LiftConstants.kD);
        pid.setFF(LiftConstants.kF);
        pid.setOutputRange(-1, 1);

        motor.setIdleMode(IdleMode.kCoast);
        motor.setSmartCurrentLimit(LiftConstants.CURRENT_LIMIT);

        motor.burnFlash();

        setGoal(Math.PI / 2);
    }

    @Override
    public void useState(TrapezoidProfile.State setpoint) {
        var feed = feedforward.calculate(setpoint.position, setpoint.velocity);

        pid.setReference(setpoint.position,
                         ControlType.kPosition, 0, feed);
    }

    public double getDistance() {
        return encoder.getPosition() - (274.5 * Math.PI);
    }

    public double getDistanceDeg() {
        return Math.toDegrees(getDistance());
    }

    public void setSpeed(double speed) {
        double boundSpeed = MathUtil.clamp(speed, -1, 1);
        motor.set(boundSpeed);
    }

    public void stop() {
        motor.stopMotor();
    }

    private double calculateKs() {
        return LiftConstants.kS;
    }

    private double calculateKg() {
        return LiftConstants.kG;
    }

    private double calculateKv() {
        return LiftConstants.kV;
    }

    private double calculateKa() {
        return LiftConstants.kA;
    }
}
