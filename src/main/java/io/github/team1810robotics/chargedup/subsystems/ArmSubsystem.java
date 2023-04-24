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

    /**
     * "global" trim var to keep track of how much trim is currently on the
     * arm at any given time. I put global in quotes because it is private
     * however I seem to treat it as if it was global (non-const global vars :/ )
     */
    private static double trim = 0;

    public ArmSubsystem(DoubleSupplier extenderEncoder) {
        // super constructor requires a few constants. read `encoder.setPosition()` for `RADIAN_OFFSET`
        super(LiftConstants.CONSTRAINTS,
              LiftConstants.ARM_INITIAL + LiftConstants.RADIAN_OFFSET);

        this.feedforward = new ArmFeedforward(() -> LiftConstants.kS,
                                              () -> calculateKg(extenderEncoder), // Kg changes based on arm extender length so we provide a callback fn rather than a const like the other inputs
                                              () -> LiftConstants.kV,
                                              () -> LiftConstants.kA);

        this.motor = new CANSparkMax(LiftConstants.MOTOR_ID, MotorType.kBrushed);

        motor.restoreFactoryDefaults();
        motor.setInverted(true);

        this.encoder = motor.getEncoder(Type.kQuadrature, LiftConstants.ENCODER_CPR);

        encoder.setInverted(true);
        /**
         * this quadrature encoder we decided to plug into the SparkMAX. this
         * was necessary when we were using the SparkMAX integrated PID but as
         * we switched off of that it no longer needed to be plugged into the
         * spark max. this odd placement of the encoder had one weird side
         * effect, that being that when we go below 0 or above about 1067.5 it
         * overflows to the other value. in an attempt to fix this problem we
         * set the initial value of the encoder to be 550 ticks and treat that
         * as the zero point. this works great at keeping the value from
         * skipping but adds the problem that now we are sitting at about 800
         * radians as we turn on the bot and if we tell it to move to say pi/3
         * it will try to do 130 rotations to get back to where it thinks pi/3
         * is. We ended up just lying to the PID controller whenever it needs
         * to know the encoder distance we just subtract off the
         * `RADIAN_OFFSET` from it to trick it into thinking it is at
         * `0 + [actual moved amount]` rather than
         * `550 + [actual moved amount]` fixing this problem
         */
        encoder.setPosition(LiftConstants.ENCODER_OFFSET + 0.137); // `+ 0.137` is a little cheat to force a bit of bias into the offset so that the arm does not need to be perfectly vertical

        // force the encoder to be in rad and rad/s
        encoder.setPositionConversionFactor(LiftConstants.ENCODER_POSITION_FACTOR);
        encoder.setVelocityConversionFactor(LiftConstants.ENCODER_VELOCITY_FACTOR);

        this.pidController = new PIDController(LiftConstants.kP,
                                               LiftConstants.kI,
                                               LiftConstants.kD);

        motor.setIdleMode(IdleMode.kCoast);
        motor.setSmartCurrentLimit(LiftConstants.CURRENT_LIMIT);

        motor.burnFlash();

        setShuffleboard();
        // the this is very importat otherwise the super `.setGoal` would be
        // called and the arm would break itself :)
        this.setGoal(LiftConstants.ARM_INITIAL);
    }

    // set goal method so that we can, you know, set the goal
    // goal AKA setpoint
    public void setGoal(double goal) {
        // need to subtract off this amount because of the tricking we are doing to make the quadrature encoder work
        // more about that on the comment for `encoder.setPosition()`
        super.setGoal(goal + LiftConstants.RADIAN_OFFSET - Math.PI);
    }

    @Override
    public void useState(TrapezoidProfile.State setpoint) {
        // calculate the PID amount that should be traveled in the time interval (comes back in volts)
        double pid  = pidController.calculate(encoder.getPosition() - LiftConstants.ARM_INITIAL, setpoint.position);
        // calculate the FF voltatge needed to keep the arm from falling (comes back in volts)
        double feed = feedforward.calculate(getDistance(), encoder.getVelocity());

        /* often the amount will exceed 12.5v but that is fine as anything
         * above the max amount it can pull will be flattened out to max */
        // add those two voltages together and apply it to the motor
        motor.setVoltage(pid + feed);
    }

    /** @return the error of the PID controller
     * <p>error in a PID in this PID controller tells you how far the
     * PID is (in rads) from the setpoint or goal
     */
    public double getPIDError() {
        return pidController.getPositionError();
    }

    /** @return the amount that the arm has moved in radians */
    public double getDistance() {
        // need to subtract off this amount because of the tricking we are doing to make the quadrature encoder work
        // more about that on the comment for `encoder.setPosition()`
        return encoder.getPosition() - (274.5 * Math.PI);
    }

    /** @return the velocity of the arm (needed for the PID) */
    public double getVelocity() {
        return encoder.getVelocity();
    }

    /**
     * @return the amount the arm has moved in degrees rather than radians
     * because degrees are a bit more intuitive unless you've been staring at
     * radian values that arent in terms of pi far more than would be
     * considered healthy like i have
     */
    public double getDistanceDeg() {
        return Math.toDegrees(getDistance());
    }

    /** old code that set the speed of the motor directly rather than letting the PID do it */
    public void setSpeed(double speed) {
        // nice little thing to make sure that the input speed isnt out of the range of [-1, 1]
        // the `.set` method should do the check anyway but its good to have here just incase
        double boundSpeed = MathUtil.clamp(speed, -1, 1);
        motor.set(boundSpeed);
    }

    /** stops the motor for the arm (very cool) */
    public void stop() {
        motor.stopMotor();
    }

    /** Debug shuffleboard stuff */
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

    /** trim getter */
    public double getTrim() {
        return trim;
    }

    /** special setter that adds the `incrementValue` to the trim rather than just directly setting it */
    public void setTrim(double incrementValue) {
        trim += incrementValue;
    }

    /** util fn that sets the trim to 0 (crazy) */
    public void zeroTrim() {
        trim = 0;
    }

    /*
     * calculateKg's magic numbers come from here:
     * https://www.desmos.com/calculator/qlubzqpbu1
     */
    /** used to figure out the kg based on the arms extender length */
    private double calculateKg(DoubleSupplier extender) {
        double e = extender.getAsDouble();
        return (6.5324e-8 * (e * e)) + (3.36428e-4 * e) + 0.228167920645;
    }

    /**
     * easiest way to get the list of spark max faults i can think of
     * @return String containing a list of active faults
     */
    private String getFaults() {
        Set<String> faults = new HashSet<>();
        /* the faluts come back as a bit field placed into a short (2 bytes)
         * each bit corresponding to a different fault that could occur
         */
        short faultBits = motor.getFaults();

        // iterate through the whole short one bit at a time and append it to set made above
        for (int i = 0; i < Short.BYTES * 8; i++) {
            // checks if the bit in the specific position is 1
            if ((faultBits & (1 << i)) == 1) {
                // gets the fault from its place in the short and appends it to the set
                faults.add(FaultID.fromId(i).name().substring(1));
            }
        }

        // smushes the set together into a single string spaced out with a ", " between each fault
        return String.join(", ", faults);
    }
}
