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
        arm.setTrim(trim);
    }
}
