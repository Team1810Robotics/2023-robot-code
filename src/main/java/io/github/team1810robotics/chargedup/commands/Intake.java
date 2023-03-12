package io.github.team1810robotics.chargedup.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import io.github.team1810robotics.chargedup.subsystems.IntakeSubsystem;

public class Intake extends CommandBase {

    private IntakeSubsystem intake;
    private double speed;

    public Intake(IntakeSubsystem intakeSubsystem, double speed) {
        this.intake = intakeSubsystem;
        this.speed = speed;

        addRequirements(intakeSubsystem);
    }

    public Intake(IntakeSubsystem intakeSubsystem, boolean in) {
        this(intakeSubsystem, (in ? 1 : -1));
    }

    @Override
    public void execute() {
        intake.intake(speed);
    }

    @Override
    public boolean isFinished() {
        // TODO: Check issue
        /* if (intake.hasCube())
            return true; */

        if (intake.lineBreak() && (speed == 1))
            return true;

        return false;
    }

    @Override
    public void end(boolean interrupted) {
        intake.stop();
    }
}
