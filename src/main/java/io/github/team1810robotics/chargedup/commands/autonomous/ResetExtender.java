package io.github.team1810robotics.chargedup.commands.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import io.github.team1810robotics.chargedup.subsystems.ExtenderSubsystem;

/** reset extender without reseting the arm */
public class ResetExtender extends CommandBase {

    private ExtenderSubsystem extender;

    public ResetExtender(ExtenderSubsystem extenderSubsystem) {
        this.extender = extenderSubsystem;

        addRequirements(extenderSubsystem);
    }

    @Override
    public void execute() {
        // moves the extender
        extender.move(false);
    }

    @Override
    public boolean isFinished() {
        // stops the command if the extender ls is hit
        return extender.getCloseLS();
    }

    @Override
    public void end(boolean interrupted) {
        // stops the extender
        extender.stop();
    }
}
