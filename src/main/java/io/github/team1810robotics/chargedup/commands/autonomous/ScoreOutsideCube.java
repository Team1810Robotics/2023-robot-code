package io.github.team1810robotics.chargedup.commands.autonomous;

import static io.github.team1810robotics.chargedup.Constants.*;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import io.github.team1810robotics.chargedup.commands.Reset;
import io.github.team1810robotics.chargedup.commands.autonomous.paths.RunCube;
import io.github.team1810robotics.chargedup.commands.autonomous.scoring.HighCube;
import io.github.team1810robotics.chargedup.subsystems.ArmSubsystem;
import io.github.team1810robotics.chargedup.subsystems.DriveSubsystem;
import io.github.team1810robotics.chargedup.subsystems.ExtenderSubsystem;
import io.github.team1810robotics.chargedup.subsystems.IntakeSubsystem;

public class ScoreOutsideCube extends SequentialCommandGroup {
    public ScoreOutsideCube(DriveSubsystem drive, ArmSubsystem arm, ExtenderSubsystem extender, IntakeSubsystem intake) {
                    // path to cube
        addCommands(RunCube.grabCube(drive),

                    // pickup cube
                    new InstantCommand(() -> arm.setGoal(ArmConstants.LOW)),
                    new InstantCommand(() -> intake.intake(false)),
                    new BBExtender(extender, AutoConstants.CUBE_FLOOR_EXTENDER),
                    new InstantCommand(() -> intake.stop()),
                    new Reset(arm, extender),

                    // path to community
                    RunCube.returnCube(drive),

                    // score cube
                    new HighCube(arm, extender, intake));
    }
}
