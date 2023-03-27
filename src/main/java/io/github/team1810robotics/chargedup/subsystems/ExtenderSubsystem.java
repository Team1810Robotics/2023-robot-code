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

        Shuffleboard.getTab("Arm").addNumber("Extender Encoder", this::getDistance);
        Shuffleboard.getTab("Arm").addBoolean("Close LS", this::getCloseLS);
        Shuffleboard.getTab("Arm").addBoolean("Far LS", this::getFarLS);
        Shuffleboard.getTab("Arm").addBoolean("Passed Half Extender", () -> getDistance() >= ExtenderConstants.MAX_OUT / 2);
    }

    public double getDistance() {
        return extenderEncoder.getDistance();
    }

    public boolean getCloseLS() {
        return (!closeLimitSwitch.get());
    }

    public boolean getFarLS() {
        return (!farLimitSwitch.get());
    }

    public void move(boolean in) {
        if (in) {
            forward();
        } else {
            backward();
        }
    }

    public void stop() {
        extenderMotor.stopMotor();
    }

    private void forward() {
        extenderMotor.set(1);
    }

    private void backward() {
        extenderMotor.set(-1);
    }

    @Override
    public void periodic() {
        if (getCloseLS())
            extenderEncoder.reset();
    }
}
