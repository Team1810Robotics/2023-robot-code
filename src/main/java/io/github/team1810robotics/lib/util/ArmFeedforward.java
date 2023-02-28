package io.github.team1810robotics.lib.util;

import java.util.function.DoubleSupplier;

/**
 * Because our arm has a rapidly changing weight distribution due to its
 * telescoping nature I have decided to *remake* the ArmFeedforward class to
 * use a DoubleSupplier instead of constants for the ks, kg, kv, ka variables.
 * This means as we move the arm in and out we can have the feed forward react
 * accordingly. Hopefully
 */
public class ArmFeedforward {
    public final DoubleSupplier ks;
    public final DoubleSupplier kg;
    public final DoubleSupplier kv;
    public final DoubleSupplier ka;

    /**
     * Creates a new ArmFeedforward with the specified gains. Units of the gain values will dictate
     * units of the computed feedforward.
     *
     * @param ks The static gain.
     * @param kg The gravity gain.
     * @param kv The velocity gain.
     * @param ka The acceleration gain.
     */
    public ArmFeedforward(DoubleSupplier ks, DoubleSupplier kg,
                          DoubleSupplier kv, DoubleSupplier ka) {
        this.ks = ks;
        this.kg = kg;
        this.kv = kv;
        this.ka = ka;
    }

    /**
     * Creates a new ArmFeedforward with the specified gains. Acceleration gain is defaulted to zero.
     * Units of the gain values will dictate units of the computed feedforward.
     *
     * @param ks The static gain.
     * @param kg The gravity gain.
     * @param kv The velocity gain.
     */
    public ArmFeedforward(DoubleSupplier ks, DoubleSupplier kg, DoubleSupplier kv) {
        this(ks, kg, kv, () -> 0);
    }

    /**
     * Calculates the feedforward from the gains and setpoints.
     *
     * @param positionRadians The position (angle) setpoint. This angle should be measured from the
     *     horizontal (i.e. if the provided angle is 0, the arm should be parallel with the floor). If
     *     your encoder does not follow this convention, an offset should be added.
     * @param velocityRadPerSec The velocity setpoint.
     * @param accelRadPerSecSquared The acceleration setpoint.
     * @return The computed feedforward.
     */
    public double calculate(double positionRadians,
            double velocityRadPerSec, double accelRadPerSecSquared) {
        return ks.getAsDouble() * Math.signum(velocityRadPerSec)
             + kg.getAsDouble() * Math.cos(positionRadians)
             + kv.getAsDouble() * velocityRadPerSec
             + ka.getAsDouble() * accelRadPerSecSquared;
    }

    /**
     * Calculates the feedforward from the gains and velocity setpoint (acceleration is assumed to be
     * zero).
     *
     * @param positionRadians The position (angle) setpoint. This angle should be measured from the
     *     horizontal (i.e. if the provided angle is 0, the arm should be parallel with the floor). If
     *     your encoder does not follow this convention, an offset should be added.
     * @param velocity The velocity setpoint.
     * @return The computed feedforward.
     */
    public double calculate(double positionRadians, double velocity) {
        return calculate(positionRadians, velocity, 0);
    }

    // Rearranging the main equation from the calculate() method yields the
    // formulas for the methods below:

    /**
     * Calculates the maximum achievable velocity given a maximum voltage supply, a position, and an
     * acceleration. Useful for ensuring that velocity and acceleration constraints for a trapezoidal
     * profile are simultaneously achievable - enter the acceleration constraint, and this will give
     * you a simultaneously-achievable velocity constraint.
     *
     * @param maxVoltage The maximum voltage that can be supplied to the arm.
     * @param angle The angle of the arm. This angle should be measured from the horizontal (i.e. if
     *     the provided angle is 0, the arm should be parallel with the floor). If your encoder does
     *     not follow this convention, an offset should be added.
     * @param acceleration The acceleration of the arm.
     * @return The maximum possible velocity at the given acceleration and angle.
     */
    public double maxAchievableVelocity(double maxVoltage, double angle, double acceleration) {
        // Assume max velocity is positive
        return (maxVoltage - ks.getAsDouble() - Math.cos(angle) * kg.getAsDouble()
                - acceleration * ka.getAsDouble()) / kv.getAsDouble();
    }
}
