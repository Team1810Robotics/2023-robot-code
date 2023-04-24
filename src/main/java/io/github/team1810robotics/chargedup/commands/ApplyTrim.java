package io.github.team1810robotics.chargedup.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import io.github.team1810robotics.chargedup.subsystems.ArmSubsystem;

public class ApplyTrim extends CommandBase {

    private ArmSubsystem arm;
    private double trim;

    public ApplyTrim(ArmSubsystem armSubsystem, double trim) {
        this.arm = armSubsystem;
        this.trim = trim;
    }

    @Override
    public void execute() {
        // apply trim is a command for convenience
        // allows it to be called rapidly by a button without needing to make
        // an instant command for it
        arm.setTrim(trim);
    }

    /** why did i write this?? */
    @Override
    public void end(boolean interrupted) {
        arm.setTrim(0);
    }
}
