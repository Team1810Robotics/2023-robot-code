package io.github.team1810robotics.chargedup.commands.autonomous.scoring;

import static io.github.team1810robotics.chargedup.Constants.*;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import io.github.team1810robotics.chargedup.commands.Arm;
import io.github.team1810robotics.chargedup.commands.Intake;
import io.github.team1810robotics.chargedup.commands.autonomous.BBExtender;
import io.github.team1810robotics.chargedup.subsystems.ArmSubsystem;
import io.github.team1810robotics.chargedup.subsystems.ExtenderSubsystem;
import io.github.team1810robotics.chargedup.subsystems.IntakeSubsystem;

public class HighCone extends SequentialCommandGroup {
    public HighCone(ArmSubsystem arm, ExtenderSubsystem extender, IntakeSubsystem intake) {
        addCommands(new Arm(arm, ArmConstants.HIGH),
                    new BBExtender(extender, AutoConstants.HIGH_EXTENDER),
                    // false denoting direction not a lack of movement :/
                    new Intake(intake, false));
    }
}
