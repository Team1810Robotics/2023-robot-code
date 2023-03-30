package io.github.team1810robotics.chargedup.subsystems;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.TrapezoidProfileSubsystem;
import io.github.team1810robotics.lib.util.ArmFeedforward;

import static io.github.team1810robotics.chargedup.Constants.ArmConstants.*;

import java.util.HashSet;
import java.util.Set;
import java.util.function.DoubleSupplier;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.FaultID;
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
              LiftConstants.ARM_INITIAL + LiftConstants.RADIAN_OFFSET);

        this.feedforward = new ArmFeedforward(() -> LiftConstants.kS,
                                              () -> calculateKg(extenderEncoder),
                                              () -> LiftConstants.kV,
                                              () -> LiftConstants.kA);

        this.motor = new CANSparkMax(LiftConstants.MOTOR_ID, MotorType.kBrushed);

        motor.restoreFactoryDefaults();
        motor.setInverted(true);

        this.encoder = motor.getEncoder(Type.kQuadrature, LiftConstants.ENCODER_CPR);

        encoder.setInverted(true);
        encoder.setPosition(LiftConstants.ENCODER_OFFSET + 0.137);

        encoder.setPositionConversionFactor(LiftConstants.ENCODER_POSITION_FACTOR);
        encoder.setVelocityConversionFactor(LiftConstants.ENCODER_VELOCITY_FACTOR);

        this.pidController = new PIDController(LiftConstants.kP,
                                               LiftConstants.kI,
                                               LiftConstants.kD);

        motor.setIdleMode(IdleMode.kCoast);
        motor.setSmartCurrentLimit(LiftConstants.CURRENT_LIMIT);

        motor.burnFlash();

        setShuffleboard();
        this.setGoal(LiftConstants.ARM_INITIAL);
    }

    public void setGoal(double goal) {
        super.setGoal(goal + LiftConstants.RADIAN_OFFSET - Math.PI);
    }

    @Override
    public void useState(TrapezoidProfile.State setpoint) {
        double pid  = pidController.calculate(encoder.getPosition() - LiftConstants.ARM_INITIAL, setpoint.position);
        double feed = feedforward.calculate(getDistance(), encoder.getVelocity());

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
        Shuffleboard.getTab("Arm").addNumber("Encoder Deg", this::getDistanceDeg);
        Shuffleboard.getTab("Arm").addNumber("Velocity", this::getVelocity);
        Shuffleboard.getTab("Arm").addNumber("Error", this::getPIDError);
        Shuffleboard.getTab("Arm").addString("SparkMAX Fault", this::getFaults);
        Shuffleboard.getTab("Arm").addNumber("Motor Temp (C)", () -> motor.getMotorTemperature());
        Shuffleboard.getTab("Arm").addNumber("Output Current", () -> motor.getOutputCurrent());
        Shuffleboard.getTab("Arm").addNumber("Bus Voltage", () -> motor.getBusVoltage());
        Shuffleboard.getTab("Arm").addCamera("Intake Camera", IntakeConstants.CAMERA_NAME, "http://10.18.10.24:1182/stream.mjpg?1678920294634");
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
     * calculateKg's magic numbers come from here:
     * https://www.desmos.com/calculator/qlubzqpbu1
     */
    private double calculateKg(DoubleSupplier extender) {
        double e = extender.getAsDouble();
        return (6.5324e-8 * (e * e)) + (3.36428e-4 * e) + 0.228167920645;
    }

    /**
     * @return String containing a list of active faults
     */
    private String getFaults() {
        Set<String> faults = new HashSet<>();
        short faultBits = motor.getFaults();

        for (int i = 0; i < Short.BYTES * 8; i++) {
            if ((faultBits & (1 << i)) != 0) {
                faults.add(FaultID.fromId(i).name().substring(1));
            }
        }

        return String.join(", ", faults);
    }
}
