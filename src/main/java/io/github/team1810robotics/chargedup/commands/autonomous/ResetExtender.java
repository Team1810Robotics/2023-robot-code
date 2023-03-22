package io.github.team1810robotics.chargedup.commands.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import io.github.team1810robotics.chargedup.subsystems.ExtenderSubsystem;

public class ResetExtender extends CommandBase {

    private ExtenderSubsystem extender;

    public ResetExtender(ExtenderSubsystem extenderSubsystem) {
        this.extender = extenderSubsystem;

        addRequirements(extenderSubsystem);
    }

    @Override
    public void execute() {

        extender.move(false);
    }

    @Override
    public boolean isFinished() {
        return extender.getCloseLS();
    }

    @Override
    public void end(boolean interrupted) {
        extender.stop();
    }
}
