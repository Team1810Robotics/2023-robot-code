package io.github.team1810robotics.chargedup.commands.autonomous.paths;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import io.github.team1810robotics.chargedup.Constants.AutoConstants;
import io.github.team1810robotics.chargedup.commands.autonomous.FollowPath;
import io.github.team1810robotics.chargedup.subsystems.DriveSubsystem;

public class DontRun {
    public DontRun() {
        SmartDashboard.putNumber("Drive P", 0);
        SmartDashboard.putNumber("Drive I", 0);
        SmartDashboard.putNumber("Drive D", 0);
        SmartDashboard.putNumber("Steer P", 0);
        SmartDashboard.putNumber("Steer I", 0);
        SmartDashboard.putNumber("Steer D", 0);
    }

    public Command rotate90deg(DriveSubsystem drive) {
        PathPlannerTrajectory trajectory = PathPlanner.loadPath("90degRot",
            AutoConstants.MAX_SPEED,
            AutoConstants.MAX_ACCELERATION);

        return new FollowPath(trajectory, drive, 0);
    }

    public Command spinLine(DriveSubsystem drive) {
        PathPlannerTrajectory trajectory = PathPlanner.loadPath("spinLine",
            AutoConstants.MAX_SPEED,
            AutoConstants.MAX_ACCELERATION);

        return new FollowPath(trajectory, drive, 0);
    }

    public Command line(DriveSubsystem drive) {
        PathPlannerTrajectory trajectory = PathPlanner.loadPath("line",
            AutoConstants.MAX_SPEED,
            AutoConstants.MAX_ACCELERATION);

        return new FollowPath(trajectory, drive, 0);
    }
}
