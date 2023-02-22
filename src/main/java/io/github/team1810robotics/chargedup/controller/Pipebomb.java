package io.github.team1810robotics.chargedup.controller;

import edu.wpi.first.wpilibj.GenericHID;

// NOTE: all names should be changed
public class Pipebomb extends GenericHID {

    public Pipebomb(int port) {
        super(port);
    }

    public enum Button {
        kMidPot1(2),
        kMidPot2(3),
        kMidPot3(4),
        kMidPot4(5),
        kMidPot5(6),

        kRedSwitchH(7),
        kRedSwitchL(8),
        kBlueSwitch(9);


        public final int value;

        Button(int value) {
            this.value = value;
        }
    }

    public double getLeftPot() {
        return getRawAxis(0);
    }

    public double getRightPot() {
        return getRawAxis(1);
    }

    public boolean getBlueSwitch() {
        return !getRawButton(Button.kBlueSwitch.value);
    }

    /**
     * @return -1 if switch is Low <br>
     *          0 if switch is Nutral <br>
     *          1 if switch is High
     */
    public int getRedSwitch() {
        if (getRawButton(Button.kRedSwitchH.value)) {
            return 1;
        } else if (getRawButton(Button.kRedSwitchL.value)) {
            return -1;
        }

        return 0;
    }

    public double getCenterPot() {
        // sorry.
        if (getRawButton(Button.kMidPot1.value)) {
            return 1;
        } else if (getRawButton(Button.kMidPot2.value)) {
            return 2;
        } else if (getRawButton(Button.kMidPot3.value)) {
            return 3;
        }  else if (getRawButton(Button.kMidPot4.value)) {
            return 4;
        }  else if (getRawButton(Button.kMidPot5.value)) {
            return 5;
        }

        return 0;
    }
}
