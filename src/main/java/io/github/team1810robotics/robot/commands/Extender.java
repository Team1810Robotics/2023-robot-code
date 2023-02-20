package io.github.team1810robotics.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import io.github.team1810robotics.robot.subsystems.ExtenderSubsystem;

public class Extender extends CommandBase {

    private ExtenderSubsystem extender;
    private boolean in;

    public Extender(ExtenderSubsystem extenderSubsystem, boolean in) {
        this.extender = extenderSubsystem;
        this.in = in;

        addRequirements(extenderSubsystem);
    }

    @Override
    public void execute() {
        if (in) {
            extender.backward();
        } else {
            extender.forward();
        }
    }

    @Override
    public void end(boolean interrupted) {
        extender.stop();
    }
}
