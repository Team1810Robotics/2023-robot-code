package io.github.team1810robotics.chargedup.commands.autonomous.scoring;

import static io.github.team1810robotics.chargedup.Constants.*;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import io.github.team1810robotics.chargedup.commands.autonomous.BBExtender;
import io.github.team1810robotics.chargedup.subsystems.ArmSubsystem;
import io.github.team1810robotics.chargedup.subsystems.ExtenderSubsystem;
import io.github.team1810robotics.chargedup.subsystems.IntakeSubsystem;

/** sequence needed to score a cone high */
public class HighCone extends SequentialCommandGroup {
    public HighCone(ArmSubsystem arm, ExtenderSubsystem extender, IntakeSubsystem intake) {
                    // set the arm to the correct spot
        addCommands(new InstantCommand(() -> arm.setGoal(ArmConstants.HIGH)),
                    // move the entender to correct distance
                    new BBExtender(extender, AutoConstants.CONE_HIGH_EXTENDER),
                    // outake for 0.5s
                    // false denoting direction not a lack of movement :/
                    new InstantCommand(() -> intake.intake(false)),
                    new WaitCommand(0.5),
                    new InstantCommand(() -> intake.stop()));
    }
}
