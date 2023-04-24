package io.github.team1810robotics.chargedup.commands.autonomous.paths;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.wpilibj2.command.Command;
import io.github.team1810robotics.chargedup.Constants.AutoConstants;
import io.github.team1810robotics.chargedup.commands.autonomous.FollowPath;
import io.github.team1810robotics.chargedup.subsystems.DriveSubsystem;

public class RunCube {

    // https://drive.google.com/file/d/1-faIjVuOw5SOThjz5IfJNtHqLJuuj8hP/view?usp=sharing
    /** @return a command that contains the path shown above */
    public static Command grabCube(DriveSubsystem drive) {
        PathPlannerTrajectory trajectory = PathPlanner.loadPath("grabCube",
            AutoConstants.MAX_SPEED,
            AutoConstants.MAX_ACCELERATION);

        return new FollowPath(trajectory, drive);
    }

    // https://drive.google.com/file/d/1Hr-W1GhXhzeMGAeki8y-Gsce25drSP6C/view?usp=sharing
    /** @return a command that contains the path shown above */
    public static Command returnCube(DriveSubsystem drive) {
        PathPlannerTrajectory trajectory = PathPlanner.loadPath("retCube",
            AutoConstants.MAX_SPEED,
            AutoConstants.MAX_ACCELERATION);

        return new FollowPath(trajectory, drive);
    }

    // https://drive.google.com/file/d/1bDWgPOFqYFpyK6DrhFlgDbFahRZxfsZa/view?usp=sharing
    /** @return a command that contains the path shown above */
    public static Command toDock(DriveSubsystem drive) {
        PathPlannerTrajectory trajectory = PathPlanner.loadPath("cubeToDock",
            AutoConstants.MAX_SPEED,
            AutoConstants.MAX_ACCELERATION);

        return new FollowPath(trajectory, drive);
    }
}
