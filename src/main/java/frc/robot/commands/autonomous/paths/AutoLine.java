package frc.robot.commands.autonomous.paths;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
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
            new InstantCommand(() -> driveSubsystem.resetOdometry(trajectory1.getInitialHolonomicPose())),
            new InstantCommand(() -> driveSubsystem.zeroGyro()),
            new WaitCommand(0.5),
            new FollowPath(trajectory1, driveSubsystem)
        );
    }
}
