package io.github.team1810robotics.chargedup.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import io.github.team1810robotics.chargedup.subsystems.ArmSubsystem;
import io.github.team1810robotics.chargedup.subsystems.ExtenderSubsystem;

public class ArmExtender extends ParallelCommandGroup {

    public ArmExtender(ArmSubsystem arm, ExtenderSubsystem extender,
                       double armSetpointRad, double extenderDistance) {

        addCommands(new Arm(arm, armSetpointRad),
                    new ExtenderDistance(extender, extenderDistance));
    }
}
