package io.github.team1810robotics.chargedup.commands.autonomous;

// import static io.github.team1810robotics.chargedup.Constants.*;

// import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
// import edu.wpi.first.wpilibj2.command.WaitCommand;
import io.github.team1810robotics.chargedup.commands.Reset;
import io.github.team1810robotics.chargedup.commands.autonomous.paths.RunCube;
import io.github.team1810robotics.chargedup.subsystems.*;

public class GrabDock extends SequentialCommandGroup {
    public GrabDock(DriveSubsystem drive, ExtenderSubsystem extender, ArmSubsystem arm, IntakeSubsystem intake) {
        addCommands(new Reset(arm, extender),

                    RunCube.grabCube(drive)/* ,

                    // pickup cube
                    new InstantCommand(() -> arm.setGoal(ArmConstants.LOW)),
                    new WaitCommand(0.5),
                    new InstantCommand(() -> intake.intake(true)),
                    new BBExtender(extender, AutoConstants.CUBE_FLOOR_EXTENDER),
                    new InstantCommand(() -> intake.stop()),
                    new Reset(arm, extender),

                    RunCube.toDock(drive) */);
    }
}
