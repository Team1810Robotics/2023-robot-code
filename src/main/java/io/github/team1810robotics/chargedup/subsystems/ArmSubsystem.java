package io.github.team1810robotics.chargedup.subsystems;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.TrapezoidProfileSubsystem;
import io.github.team1810robotics.lib.util.ArmFeedforward;

import static io.github.team1810robotics.chargedup.Constants.ArmConstants.*;

import java.util.function.DoubleSupplier;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxRelativeEncoder.Type;

public class ArmSubsystem extends TrapezoidProfileSubsystem {

    private final CANSparkMax motor;
    private final RelativeEncoder encoder;

    private final ArmFeedforward feedforward;
    private final PIDController pidController;

    private static double trim = 0;

    public ArmSubsystem(DoubleSupplier extenderEncoder) {
        super(LiftConstants.CONSTRAINTS,
              LiftConstants.ARM_OFFSET + LiftConstants.RADIAN_OFFSET);

        this.feedforward = new ArmFeedforward(() -> calculateKs(extenderEncoder),
                                              () -> calculateKg(extenderEncoder),
                                              () -> calculateKv(extenderEncoder),
                                              () -> calculateKa(extenderEncoder));

        this.motor = new CANSparkMax(LiftConstants.MOTOR_ID, MotorType.kBrushed);
        motor.restoreFactoryDefaults();
        motor.setInverted(true);

        this.encoder = motor.getEncoder(Type.kQuadrature, LiftConstants.ENCODER_CPR);

        encoder.setInverted(true);
        encoder.setPosition(LiftConstants.ENCODER_OFFSET);

        encoder.setPositionConversionFactor(LiftConstants.ENCODER_POSITION_FACTOR);
        encoder.setVelocityConversionFactor(LiftConstants.ENCODER_VELOCITY_FACTOR);

        this.pidController = new PIDController(LiftConstants.kP,
                                               LiftConstants.kI,
                                               LiftConstants.kD);

        motor.setIdleMode(IdleMode.kCoast);
        motor.setSmartCurrentLimit(LiftConstants.CURRENT_LIMIT);

        motor.burnFlash();

        setShuffleboard();
        this.setGoal(LiftConstants.ARM_OFFSET);
    }

    public void setGoal(double goal) {
        super.setGoal(goal + LiftConstants.RADIAN_OFFSET - Math.PI);
    }

    @Override
    public void useState(TrapezoidProfile.State setpoint) {
        var pid  = pidController.calculate(encoder.getPosition() - LiftConstants.ARM_OFFSET, setpoint.position);
        var feed = feedforward.calculate(encoder.getPosition() - LiftConstants.ARM_OFFSET, encoder.getVelocity());

        motor.setVoltage(pid + feed);
    }

    public double getPIDError() {
        return pidController.getPositionError();
    }

    public double getDistance() {
        return encoder.getPosition() - (274.5 * Math.PI);
    }

    public double getVelocity() {
        return encoder.getVelocity();
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

    private void setShuffleboard() {
        Shuffleboard.getTab("Arm").addNumber("Encoder", this::getDistance);
        Shuffleboard.getTab("Arm").addNumber("Velocity", this::getVelocity);
        Shuffleboard.getTab("Arm").addNumber("Error", this::getPIDError);
    }

    public double getTrim() {
        return trim;
    }

    public void setTrim(double incrementValue) {
        trim += incrementValue;
    }

    public void zeroTrim() {
        trim = 0;
    }

    /**
     * all calculateK[x] method's magic numbers come from here:
     * https://www.desmos.com/calculator/g7nmtvtam8
     */

    private double calculateKs(DoubleSupplier extender) {
        return extender.getAsDouble() * 1.03092783505e-4;
    }

    private double calculateKg(DoubleSupplier extender) {
        return extender.getAsDouble() * 3.40206185567e-4;
    }

    private double calculateKv(DoubleSupplier extender) {
        return 0.65;
    }

    private double calculateKa(DoubleSupplier extender) {
        return 0.1;
    }
}
