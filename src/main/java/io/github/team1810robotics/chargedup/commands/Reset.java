package io.github.team1810robotics.chargedup.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import io.github.team1810robotics.chargedup.Constants.ArmConstants;
import io.github.team1810robotics.chargedup.subsystems.ArmSubsystem;
import io.github.team1810robotics.chargedup.subsystems.ExtenderSubsystem;

/** resets the arm with one command call */
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
        // sets the arm goal to reset
        /* the are doesnt need to be controlled in this command because the
         * subsystem does it for us  */
        arm.setGoal(ArmConstants.RESET);

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
