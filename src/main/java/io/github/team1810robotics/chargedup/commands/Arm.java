package io.github.team1810robotics.chargedup.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import io.github.team1810robotics.chargedup.subsystems.ArmSubsystem;

public class Arm extends CommandBase {

    private ArmSubsystem arm;
    private double setpointAngle;
    private double trim;

    public Arm(ArmSubsystem armSubsystem, double setpointAngleRadians, double trim) {
        this.arm = armSubsystem;
        this.setpointAngle = setpointAngleRadians;
        this.trim = trim;

        addRequirements(armSubsystem);
    }

    @Override
    public void execute() {
        arm.setGoal(setpointAngle + trim);
    }

    @Override
    public void end(boolean interrupted) {
        arm.stop();
    }
}
