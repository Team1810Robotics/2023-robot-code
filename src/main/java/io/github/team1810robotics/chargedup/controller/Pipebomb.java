package io.github.team1810robotics.chargedup.controller;

import edu.wpi.first.wpilibj.GenericHID;

public class Pipebomb extends GenericHID {

    public Pipebomb(int port) {
        super(port);
    }

    public enum Button {
        // Face Arcade Buttons
        reset(1),
        high(2),
        mid(3),
        low(4),

        // Alternative switches
        altExtenderOut(5),
        altExtenderIn(6),

        trimDown(7),
        trimUp(8),

        // Joystick
        intake(11),
        outtake(10),
        extenderIn(9),
        extenderOut(12);

        public final int value;

        Button(int value) {
            this.value = value;
        }
    }
}
