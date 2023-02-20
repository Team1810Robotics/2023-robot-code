package io.github.team1810robotics.robot.commands.autonomous.paths;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import io.github.team1810robotics.robot.commands.autonomous.FollowPath;
import io.github.team1810robotics.robot.subsystems.DriveSubsystem;

import static io.github.team1810robotics.robot.Constants.*;

public class FullSpin extends SequentialCommandGroup {
    public FullSpin(DriveSubsystem driveSubsystem) {
        PathPlannerTrajectory trajectory1 = PathPlanner.loadPath("360spinTest",
            AutoConstants.MAX_SPEED_METERS_PER_SECOND,
            AutoConstants.MAX_ACCELERATION_METERS_PER_SECOND_SQUARED
        );

        addCommands(
            new FollowPath(trajectory1, driveSubsystem)
        );
    }
}
