package io.github.team1810robotics.chargedup.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import io.github.team1810robotics.chargedup.subsystems.IntakeSubsystem;

public class Intake extends CommandBase {

    private IntakeSubsystem intake;
    private boolean in;

    public Intake(IntakeSubsystem intakeSubsystem, boolean in) {
        this.intake = intakeSubsystem;
        this.in = in;

        addRequirements(intakeSubsystem);

    }

    @Override
    public void execute() {
        // move the intake in the denoted direction
        // that being in or not in
        intake.intake(in);
    }

    @Override
    public boolean isFinished() {
        // if the linebreak is tripped and the inake is moving in
        // stop the command
        if (intake.lineBreak() && in)
            return true;

        return false;
    }

    @Override
    public void end(boolean interrupted) {
        // when the command stops stop the intake
        intake.stop();
    }
}
