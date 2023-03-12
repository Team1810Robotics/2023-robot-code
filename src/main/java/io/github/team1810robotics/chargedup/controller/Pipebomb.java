package io.github.team1810robotics.chargedup.controller;

import edu.wpi.first.wpilibj.GenericHID;

public class Pipebomb extends GenericHID {

    public static final int extenderAxis = 0;
    public static final int intakeAxis = 0;

    public Pipebomb(int port) {
        super(port);
    }

    public enum Button {
        reset(1),
        high(2),
        mid(3),
        low(4),

        altIntakeOut(5),
        altIntakeIn(6),

        trimDown(7),
        trimUp(8),

        altExtenderOut(9),
        altExtenderIn(10);


        public final int value;

        Button(int value) {
            this.value = value;
        }
    }

    public double getJoystickExtender() {
        return getRawAxis(extenderAxis);
    }

    public double getJoystickIntake() {
        return getRawAxis(intakeAxis);
    }
}
