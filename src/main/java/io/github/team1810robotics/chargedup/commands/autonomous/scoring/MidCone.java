package io.github.team1810robotics.chargedup.commands.autonomous.scoring;

import static io.github.team1810robotics.chargedup.Constants.*;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import io.github.team1810robotics.chargedup.commands.Arm;
import io.github.team1810robotics.chargedup.commands.Intake;
import io.github.team1810robotics.chargedup.commands.autonomous.BBExtender;
import io.github.team1810robotics.chargedup.subsystems.ArmSubsystem;
import io.github.team1810robotics.chargedup.subsystems.ExtenderSubsystem;
import io.github.team1810robotics.chargedup.subsystems.IntakeSubsystem;

public class MidCone extends SequentialCommandGroup {
    public MidCone(ArmSubsystem arm, ExtenderSubsystem extender, IntakeSubsystem intake) {
        addCommands(new Arm(arm, ArmConstants.MEDIUM),
                    new BBExtender(extender, AutoConstants.MID_EXTENDER),
                    // false denoting direction not a lack of movement :/
                    new Intake(intake, false));
    }
}
