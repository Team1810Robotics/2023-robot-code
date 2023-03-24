package io.github.team1810robotics.chargedup.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import io.github.team1810robotics.chargedup.commands.Reset;
import io.github.team1810robotics.chargedup.commands.autonomous.paths.Offline;
import io.github.team1810robotics.chargedup.subsystems.ArmSubsystem;
import io.github.team1810robotics.chargedup.subsystems.DriveSubsystem;
import io.github.team1810robotics.chargedup.subsystems.ExtenderSubsystem;

public class FarOffline extends SequentialCommandGroup {
    public FarOffline(DriveSubsystem drive, ArmSubsystem arm, ExtenderSubsystem extender) {
        addCommands(new Reset(arm, extender),

                    Offline.farOffline(drive));
    }
}
