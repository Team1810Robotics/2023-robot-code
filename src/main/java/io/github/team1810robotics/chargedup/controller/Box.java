package io.github.team1810robotics.chargedup.controller;

import edu.wpi.first.wpilibj.GenericHID;

/** Class for more easily interfacing with Button Box Josh made */
public class Box extends GenericHID {

    public Box(int port) {
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

        // Trim switch
        trimDown(7),
        trimUp(8),

        // Joystick
        intake(11),
        outtake(10),
        extenderOut(9),
        extenderIn(12);

        public final int value;

        Button(int value) {
            this.value = value;
        }
    }
}
