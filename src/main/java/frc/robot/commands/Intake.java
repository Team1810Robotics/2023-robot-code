package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;

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
        intake.intake(in);
    }

    @Override
    public boolean isFinished() {
        if (intake.hasCube())
            return true;

        if (intake.lineBreak())
            return true;

        return false;
    }

    @Override
    public void end(boolean interrupted) {
        intake.stop();
    }
}
