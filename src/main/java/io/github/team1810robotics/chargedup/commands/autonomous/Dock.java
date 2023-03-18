package io.github.team1810robotics.chargedup.commands.autonomous;

import com.ctre.phoenix.sensors.Pigeon2;

import io.github.team1810robotics.chargedup.Constants.*;
import io.github.team1810robotics.chargedup.subsystems.DriveSubsystem;

// TODO: check all signs e.g. - > <
public class Dock {

    private Pigeon2 gyro;

    private DockStates state;
    private int debounceCount;

    Dock(DriveSubsystem driveSubsystem) {
        this.gyro = driveSubsystem.gyro;

        state = DockStates.driveToStation;
        debounceCount = 0;
    }

    public double autoBalance() {
        switch(state) {
            case driveToStation:
            return getToStation();

            case driveUpStation:
            return upStation();

            case onStation:
            return onStation();

            case wait:
            default:
            return 0;
        }
    }

    /********** private **********/
    private enum DockStates {
        driveToStation,
        driveUpStation,
        onStation,
        wait;
    }

    private double getTilt() {
        double pitch = gyro.getPitch();
        double roll = gyro.getRoll();

        if ((pitch + roll) >= 0) {
            return Math.hypot(pitch, roll);
        } else {
            return -Math.hypot(pitch, roll);
        }
    }

    // drive forwards to approach station, exit when tilt is detected
    private double getToStation() {
        if (getTilt() > AutoConstants.CHARGE_STATION_TILT_DEG)
            debounceCount++;

        if (debounceCount > AutoConstants.DEBOUNCE_TICKS) {
            state = DockStates.driveUpStation;
            debounceCount = 0;
            return AutoConstants.SLOW_SPEED;
        }
        return AutoConstants.FAST_SPEED;
    }

    // driving up charge station, drive slower, stopping when level
    private double upStation() {
        if (getTilt() < AutoConstants.LEVEL_DEG)
            debounceCount++;

        if (debounceCount > AutoConstants.DEBOUNCE_TICKS) {
            state = DockStates.onStation;
            debounceCount = 0;
            return 0;
        }
        return AutoConstants.SLOW_SPEED;
    }

    private double onStation() {
        if (Math.abs(getTilt()) <= AutoConstants.LEVEL_DEG / 2) {
            debounceCount++;
        }
        if (debounceCount > AutoConstants.DEBOUNCE_TICKS) {
            state = DockStates.wait;
            debounceCount = 0;
            return 0;
        }

        if (getTilt() >= AutoConstants.LEVEL_DEG) {
            return 0.1;
        } else if (getTilt() <= -AutoConstants.LEVEL_DEG) {
            return -0.1;
        }

        return 0;
    }
}
