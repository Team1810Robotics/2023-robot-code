package io.github.team1810robotics.chargedup.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import io.github.team1810robotics.chargedup.subsystems.ExtenderSubsystem;

public class ExtenderDistance extends CommandBase {

    private ExtenderSubsystem extender;
    private double dist;
    private boolean directionForward;

    public ExtenderDistance(ExtenderSubsystem extenderSubsystem, double distance) {
        this.extender = extenderSubsystem;
        this.dist = distance;

        this.directionForward = (extenderSubsystem.getDistance() <= distance);

        addRequirements(extenderSubsystem);
    }

    @Override
    public void execute() {
        extender.move(directionForward);
    }

    @Override
    public boolean isFinished() {
        // TODO: rubber duck logic
        if (extender.getDistance() >= dist && directionForward) {
            return true;
        } else if (extender.getDistance() <= dist && !directionForward) {
            return true;
        }

        return false;
    }

    @Override
    public void end(boolean interrupted) {
        extender.stop();
    }
}
