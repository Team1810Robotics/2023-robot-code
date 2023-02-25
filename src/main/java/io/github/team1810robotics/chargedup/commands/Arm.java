package io.github.team1810robotics.chargedup.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import io.github.team1810robotics.chargedup.subsystems.ArmSubsystem;

public class Arm extends CommandBase {

    private ArmSubsystem arm;
    private double setpointAngle;

    public Arm(ArmSubsystem armSubsystem, double setpointAngleRadians) {
        this.arm = armSubsystem;
        this.setpointAngle = setpointAngleRadians;

        addRequirements(armSubsystem);
    }

    @Override
    public void execute() {
        arm.setGoal(setpointAngle);
    }

    @Override
    public void end(boolean interrupted) {
        arm.stop();
    }
}
