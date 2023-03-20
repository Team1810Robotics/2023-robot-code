package io.github.team1810robotics.chargedup.subsystems;

import java.util.ArrayList;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.music.Orchestra;

import io.github.team1810robotics.chargedup.SwerveModule;
import io.github.team1810robotics.chargedup.log.Log;

public class Music {

    private final Orchestra orchestra;

    public Music(SwerveModule... modules) {
        ArrayList<TalonFX> instruments = new ArrayList<>();
        for (int i = 0; i < modules.length; i++) {
            instruments.add(modules[i].steerMotor);
            instruments.add(modules[i].driveMotor);
        }

        orchestra = new Orchestra(instruments);
    }

    public void loadMusic(String path) {
        orchestra.loadMusic(path);
    }

    public void play() {
        Log.debug("Play called");
        orchestra.play();
    }

    public void stop() {
        orchestra.stop();
    }

    public void pause() {
        orchestra.pause();
    }

    public boolean isPlaying() {
        return orchestra.isPlaying();
    }
}
