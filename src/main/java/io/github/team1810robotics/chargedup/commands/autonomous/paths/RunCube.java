package io.github.team1810robotics.chargedup.commands.autonomous.paths;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.wpilibj2.command.Command;
import io.github.team1810robotics.chargedup.Constants.AutoConstants;
import io.github.team1810robotics.chargedup.commands.autonomous.FollowPath;
import io.github.team1810robotics.chargedup.subsystems.DriveSubsystem;

public class RunCube {
    public static Command grabCube(DriveSubsystem drive) {
        PathPlannerTrajectory trajectory = PathPlanner.loadPath("grabCube",
            AutoConstants.MAX_SPEED,
            AutoConstants.MAX_ACCELERATION);

        return new FollowPath(trajectory, drive);
    }

    public static Command returnCube(DriveSubsystem drive) {
        PathPlannerTrajectory trajectory = PathPlanner.loadPath("retCube",
            AutoConstants.MAX_SPEED,
            AutoConstants.MAX_ACCELERATION);

        return new FollowPath(trajectory, drive);
    }

    public static Command toDock(DriveSubsystem drive) {
        PathPlannerTrajectory trajectory = PathPlanner.loadPath("cubeToDock",
            AutoConstants.MAX_SPEED,
            AutoConstants.MAX_ACCELERATION);

        return new FollowPath(trajectory, drive);
    }
}
