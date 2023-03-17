package io.github.team1810robotics.chargedup.commands.autonomous.paths.tests;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import io.github.team1810robotics.chargedup.commands.autonomous.FollowPath;
import io.github.team1810robotics.chargedup.subsystems.DriveSubsystem;

import static io.github.team1810robotics.chargedup.Constants.*;

// 5 meter path that rotates 180*
public class SpinTest extends SequentialCommandGroup {
    public SpinTest(DriveSubsystem driveSubsystem) {
        PathPlannerTrajectory trajectory1 = PathPlanner.loadPath("spinTest",
            AutoConstants.MAX_SPEED_METERS_PER_SECOND,
            AutoConstants.MAX_ACCELERATION_METERS_PER_SECOND_SQUARED);

        addCommands(
            new FollowPath(trajectory1, driveSubsystem)
        );
    }
}
