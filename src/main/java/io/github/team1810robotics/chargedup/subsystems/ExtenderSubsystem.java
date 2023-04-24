package io.github.team1810robotics.chargedup.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static io.github.team1810robotics.chargedup.Constants.ArmConstants.*;

public class ExtenderSubsystem extends SubsystemBase {
    private final Talon extenderMotor;
    private final Encoder extenderEncoder;
    private final DigitalInput farLimitSwitch;
    private final DigitalInput closeLimitSwitch;

    public ExtenderSubsystem() {
        extenderMotor = new Talon(ExtenderConstants.MOTOR_ID);
        extenderEncoder = new Encoder(ExtenderConstants.ENCODER_PORTS[0], ExtenderConstants.ENCODER_PORTS[1]);
        extenderEncoder.setReverseDirection(true);

        farLimitSwitch = new DigitalInput(ExtenderConstants.FAR_LS);
        closeLimitSwitch = new DigitalInput(ExtenderConstants.CLOSE_LS);

        /** debug stuff */
        Shuffleboard.getTab("Arm").addNumber("Extender Encoder", this::getDistance);
        Shuffleboard.getTab("Arm").addBoolean("Close LS", this::getCloseLS);
        Shuffleboard.getTab("Arm").addBoolean("Far LS", this::getFarLS);
        Shuffleboard.getTab("Arm").addBoolean("Passed Half Extender", () -> getDistance() >= ExtenderConstants.MAX_OUT / 2);
    }

    /** @return the distance of the encoder in ticks */
    public double getDistance() {
        return extenderEncoder.getDistance();
    }

    /** @return the value of the close limit switch<p>
     * mostly a motor safty switch. needed to make sure we dont continue to
     * try and pull the motor in after we hit a hard mechanical stop to keep
     * it from damaging itself
     */
    public boolean getCloseLS() {
        // inverted because they got wired to be active low
        // and i wanted active high
        return (!closeLimitSwitch.get());
    }

    /** @return the value of the close limit switch<p>
     * used to tell the max extension of the entender
     * and to make sure we dont go past
    */
    public boolean getFarLS() {
        // inverted because they got wired to be active low
        // and i wanted active high
        return (!farLimitSwitch.get());
    }

    /** found making a boolean control the motor easiest */
    public void move(boolean in) {
        if (in) {
            forward();
        } else {
            backward();
        }
    }

    /** wrapper fn that stops the motor */
    public void stop() {
        extenderMotor.stopMotor();
    }

    /** moves the motor forward at max speed */
    private void forward() {
        // treat the motor like a relay i will
        extenderMotor.set(1);
    }

    /** moves the motor backward at max speed */
    private void backward() {
        // treat the motor like a relay i will
        extenderMotor.set(-1);
    }

    @Override
    public void periodic() {
        // resets the encoder to 0 when the close limit switch is hit to
        // midigate the drift over a match
        if (getCloseLS())
            extenderEncoder.reset();
    }
}
