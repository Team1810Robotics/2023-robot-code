package io.github.team1810robotics.chargedup.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import io.github.team1810robotics.chargedup.subsystems.LifterSubsystem;

// "Why did you make that a command?"
public class LifterSpeed extends CommandBase {

    private LifterSubsystem lifter;
    private double speed;

    public LifterSpeed(LifterSubsystem lifterSubsystem, double speed) {
        this.lifter = lifterSubsystem;
        this.speed = speed;

        addRequirements(lifterSubsystem);
    }

    @Override
    public void execute() {
        lifter.setSpeed(speed);
    }

    @Override
    public void end(boolean interrupted) {
        lifter.stop();
    }
}
