package io.github.team1810robotics.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static io.github.team1810robotics.robot.Constants.ArmConstants.*;

public class ExtenderSubsystem extends SubsystemBase {
    private final Relay extenderMotor;
    private final Encoder extenderEncoder;
    private final DigitalInput farLimitSwitch;
    private final DigitalInput closeLimitSwitch;

    public ExtenderSubsystem() {
        extenderMotor = new Relay(ExtenderConstants.RELAY_ID, Direction.kBoth);
        extenderEncoder = new Encoder(ExtenderConstants.ENCODER_PORTS[0], ExtenderConstants.ENCODER_PORTS[1]);

        farLimitSwitch = new DigitalInput(ExtenderConstants.LIMIT_SWITCH_PORTS[0]);
        closeLimitSwitch = new DigitalInput(ExtenderConstants.LIMIT_SWITCH_PORTS[1]);
    }

    public double getEncoder() {
        return extenderEncoder.getDistance();
    }

    public void forward() {
        if (farLimitSwitch.get()) {
            extenderMotor.set(Value.kForward);
        } else {
            stop();
        }
    }

    public void backward() {
        if (closeLimitSwitch.get()) {
            extenderMotor.set(Value.kReverse);
        } else {
            stop();
            extenderEncoder.reset();
        }
    }

    public void stop() {
        extenderMotor.set(Value.kOff);
    }
}
