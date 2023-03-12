package io.github.team1810robotics.chargedup.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import io.github.team1810robotics.chargedup.subsystems.ExtenderSubsystem;

public class ExtenderBool extends CommandBase {

    private ExtenderSubsystem extender;
    private double in;

    public ExtenderBool(ExtenderSubsystem extenderSubsystem, double in) {
        this.extender = extenderSubsystem;
        this.in = in;

        addRequirements(extenderSubsystem);
    }

    public ExtenderBool(ExtenderSubsystem extenderSubsystem, boolean in) {
        this(extenderSubsystem, (in ? 1 : -1));
    }

    @Override
    public void execute() {
        extender.move(in);
    }

    @Override
    public boolean isFinished() {
        return extender.getCloseLS() && (in == -1);
    }

    @Override
    public void end(boolean interrupted) {
        extender.stop();
    }
}
