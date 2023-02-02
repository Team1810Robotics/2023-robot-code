package frc.robot.commands.autonomous.paths;

import static frc.robot.Constants.*;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;

import frc.robot.commands.autonomous.FollowPath;
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

// default path planner path
// https://drive.google.com/file/d/1FnDsJA_6mmZu-9TIkjMY9jorip5BepSF/view?usp=sharing
public class TestPathplanner extends SequentialCommandGroup {
    public TestPathplanner(DriveSubsystem driveSubsystem) {
        PathPlannerTrajectory trajectory1 = PathPlanner.loadPath("defaultPath",
                                AutoConstants.MAX_SPEED_METERS_PER_SECOND,
                                AutoConstants.MAX_ACCELERATION_METERS_PER_SECOND_SQUARED);

        addCommands(
            new InstantCommand(() -> driveSubsystem.resetOdometry(trajectory1.getInitialHolonomicPose())),
            new FollowPath(trajectory1, driveSubsystem)
        );
    }
}