package io.github.team1810robotics.chargedup.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import io.github.team1810robotics.chargedup.subsystems.ExtenderSubsystem;

public class Extender extends CommandBase {

    private ExtenderSubsystem extender;
    private boolean in;

    // `in` is backwards in this command and its confusing sorry
    public Extender(ExtenderSubsystem extenderSubsystem, boolean in) {
        this.extender = extenderSubsystem;
        this.in = in;

        addRequirements(extenderSubsystem);
    }

    @Override
    public void execute() {
        extender.move(in);
    }

    @Override
    public boolean isFinished() {
        // if the close ls it tripped and the entender is moving in
        // stop the command
        if (extender.getCloseLS() && !in)
            return true;

        // if the far ls it tripped and the entender isnt moving in
        // stop the command
        if (extender.getFarLS() && in)
            return true;

        return false;

    }

    @Override
    public void end(boolean interrupted) {
        // when the command stops stop the extender
        extender.stop();
    }
}
