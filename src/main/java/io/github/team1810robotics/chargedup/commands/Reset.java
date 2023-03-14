package io.github.team1810robotics.chargedup.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import io.github.team1810robotics.chargedup.Constants.ArmConstants.*;
import io.github.team1810robotics.chargedup.subsystems.ArmSubsystem;
import io.github.team1810robotics.chargedup.subsystems.ExtenderSubsystem;

public class Reset extends CommandBase {

    private ArmSubsystem arm;
    private ExtenderSubsystem extender;

    public Reset(ArmSubsystem armSubsystem, ExtenderSubsystem extenderSubsystem) {
        this.arm = armSubsystem;
        this.extender = extenderSubsystem;

        addRequirements(armSubsystem, extenderSubsystem);
    }

    @Override
    public void execute() {
        arm.setGoal(LiftConstants.ARM_INITIAL);

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
