package io.github.team1810robotics.chargedup.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static io.github.team1810robotics.chargedup.Constants.ArmConstants.*;

public class ExtenderSubsystem extends SubsystemBase {
    private final Relay extenderMotor;
    private final Encoder extenderEncoder;
    private final DigitalInput farLimitSwitch;
    private final DigitalInput closeLimitSwitch;

    public ExtenderSubsystem() {
        extenderMotor = new Relay(ExtenderConstants.RELAY_ID, Direction.kBoth);
        extenderEncoder = new Encoder(ExtenderConstants.ENCODER_PORTS[0], ExtenderConstants.ENCODER_PORTS[1]);
        extenderEncoder.setReverseDirection(true);

        farLimitSwitch = new DigitalInput(ExtenderConstants.FAR_LS);
        closeLimitSwitch = new DigitalInput(ExtenderConstants.CLOSE_LS);
    }

    public double getDistance() {
        return extenderEncoder.getDistance();
    }

    public boolean getCloseLS() {
        return (!closeLimitSwitch.get());
    }

    public void move(boolean in) {
        if (in) {
            forward();
        } else {
            backward();
        }
    }

    public void stop() {
        extenderMotor.set(Value.kOff);
    }

    private void forward() {
        extenderMotor.set(Value.kForward);
        if (!farLimitSwitch.get()) {
        } else {
            // stop();
        }
    }

    private void backward() {
        extenderMotor.set(Value.kReverse);
    }

    @Override
    public void periodic() {
        if (getCloseLS())
            extenderEncoder.reset();
    }
}
