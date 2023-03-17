package io.github.team1810robotics.chargedup.commands.autonomous.paths.tests;

import static io.github.team1810robotics.chargedup.Constants.*;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;

import io.github.team1810robotics.chargedup.commands.autonomous.FollowPath;
import io.github.team1810robotics.chargedup.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

// default path planner path
// https://drive.google.com/file/d/1FnDsJA_6mmZu-9TIkjMY9jorip5BepSF/view?usp=sharing
public class TestPathplanner extends SequentialCommandGroup {
    public TestPathplanner(DriveSubsystem driveSubsystem) {
        PathPlannerTrajectory trajectory1 = PathPlanner.loadPath("defaultPath",
                                AutoConstants.MAX_SPEED_METERS_PER_SECOND,
                                AutoConstants.MAX_ACCELERATION_METERS_PER_SECOND_SQUARED);

        addCommands(
            new FollowPath(trajectory1, driveSubsystem)
        );
    }
}