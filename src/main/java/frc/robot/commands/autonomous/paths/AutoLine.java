package frc.robot.commands.autonomous.paths;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.autonomous.FollowPath;
import frc.robot.subsystems.DriveSubsystem;

import static frc.robot.Constants.*;

// Should be a four meter path that drives forward
public class AutoLine extends SequentialCommandGroup {
    public AutoLine(DriveSubsystem driveSubsystem) {
        PathPlannerTrajectory trajectory1 = PathPlanner.loadPath("line",
                AutoConstants.MAX_SPEED_METERS_PER_SECOND,
                AutoConstants.MAX_ACCELERATION_METERS_PER_SECOND_SQUARED);

        addCommands(
            new FollowPath(trajectory1, driveSubsystem)
        );
    }
}
