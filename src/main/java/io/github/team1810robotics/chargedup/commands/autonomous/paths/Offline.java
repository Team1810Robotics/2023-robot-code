package io.github.team1810robotics.chargedup.commands.autonomous.paths;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.wpilibj2.command.Command;
import io.github.team1810robotics.chargedup.Constants.AutoConstants;
import io.github.team1810robotics.chargedup.commands.autonomous.FollowPath;
import io.github.team1810robotics.chargedup.subsystems.DriveSubsystem;

public class Offline {
    /** @return a command that contains the path that gets mobility points for
     * the line that is closer */
    public static Command closeOffline(DriveSubsystem drive) {
        PathPlannerTrajectory trajectory = PathPlanner.loadPath("closeOffline",
            AutoConstants.MAX_SPEED,
            AutoConstants.MAX_ACCELERATION);

        return new FollowPath(trajectory, drive);
    }

    /** @return a command that contains the path that gets mobility points for
     * the line that is further */
    public static Command farOffline(DriveSubsystem drive) {
        PathPlannerTrajectory trajectory = PathPlanner.loadPath("farOffine",
            AutoConstants.MAX_SPEED,
            AutoConstants.MAX_ACCELERATION);

        return new FollowPath(trajectory, drive);
    }
}
