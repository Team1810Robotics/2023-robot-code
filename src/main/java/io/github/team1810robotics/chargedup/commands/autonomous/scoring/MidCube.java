package io.github.team1810robotics.chargedup.commands.autonomous.scoring;

import static io.github.team1810robotics.chargedup.Constants.*;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import io.github.team1810robotics.chargedup.commands.autonomous.BBExtender;
import io.github.team1810robotics.chargedup.subsystems.ArmSubsystem;
import io.github.team1810robotics.chargedup.subsystems.ExtenderSubsystem;
import io.github.team1810robotics.chargedup.subsystems.IntakeSubsystem;

public class MidCube extends SequentialCommandGroup {
    public MidCube(ArmSubsystem arm, ExtenderSubsystem extender, IntakeSubsystem intake) {
        addCommands(new InstantCommand(() -> arm.setGoal(ArmConstants.MEDIUM)),
                    new BBExtender(extender, AutoConstants.MID_EXTENDER),
                    new WaitCommand(1.5),
                    // false denoting direction not a lack of movement :/
                    new InstantCommand(() -> intake.intake(false)),
                    new WaitCommand(0.5),
                    new InstantCommand(() -> intake.stop()));
    }
}
