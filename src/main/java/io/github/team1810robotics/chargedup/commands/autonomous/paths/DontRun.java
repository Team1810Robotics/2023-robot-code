package io.github.team1810robotics.chargedup.commands.autonomous.paths;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.wpilibj2.command.Command;
import io.github.team1810robotics.chargedup.Constants.AutoConstants;
import io.github.team1810robotics.chargedup.commands.autonomous.FollowPath;
import io.github.team1810robotics.chargedup.subsystems.DriveSubsystem;

public class DontRun {
    public static Command rotate90deg(DriveSubsystem drive) {
        PathPlannerTrajectory trajectory = PathPlanner.loadPath("90degRot",
            AutoConstants.MAX_SPEED,
            AutoConstants.MAX_ACCELERATION);

        return new FollowPath(trajectory, drive);
    }

    public static Command spinLine(DriveSubsystem drive) {
        PathPlannerTrajectory trajectory = PathPlanner.loadPath("spinLine",
            AutoConstants.MAX_SPEED,
            AutoConstants.MAX_ACCELERATION);

        return new FollowPath(trajectory, drive);
    }

    public static Command line(DriveSubsystem drive) {
        PathPlannerTrajectory trajectory = PathPlanner.loadPath("line",
            AutoConstants.MAX_SPEED,
            AutoConstants.MAX_ACCELERATION);

        return new FollowPath(trajectory, drive);
    }
}
