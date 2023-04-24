package io.github.team1810robotics.chargedup.commands.autonomous;

import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.commands.PPSwerveControllerCommand;

import edu.wpi.first.math.controller.PIDController;
import io.github.team1810robotics.chargedup.subsystems.DriveSubsystem;
import static io.github.team1810robotics.chargedup.Constants.*;

// thin wrapper around PPSwerveControllerCommand to follow a PathPlannerTrajectory
public class FollowPath extends PPSwerveControllerCommand {
    PathPlannerTrajectory trajectory;
    DriveSubsystem driveSubsystem;

    public FollowPath(PathPlannerTrajectory trajectory, DriveSubsystem driveSubsystem) {
        // make a PPSwerveController
        super(trajectory,
              driveSubsystem::getPose,
              DriveConstants.SWERVE_KINEMATICS,
              new PIDController(DriveConstants.DRIVE_kP,
                                DriveConstants.DRIVE_kI,
                                DriveConstants.DRIVE_kD),
              new PIDController(DriveConstants.DRIVE_kP,
                                DriveConstants.DRIVE_kI,
                                DriveConstants.DRIVE_kD),
              new PIDController(DriveConstants.STEER_kP,
                                DriveConstants.STEER_kI,
                                DriveConstants.STEER_kD),
              driveSubsystem::setModuleStates,
              driveSubsystem);

              this.trajectory = trajectory;
              this.driveSubsystem = driveSubsystem;
            }

    @Override
    public void initialize() {
        // run the super's `initialize()`
        super.initialize();
        // reset out odometry to the inital pose of the path so that we arent
        // completely screwed up. . . only slightly
        driveSubsystem.resetOdometry(trajectory.getInitialHolonomicPose());
    }

    @Override
    public void execute() {
        // run the super's `execute()`
        super.execute();
    }

    @Override
    public boolean isFinished() {
        // run the super's `isFinished()`
        return super.isFinished();
    }

    @Override
    public void end(boolean interrupted) {
        // run the super's `end()`
        super.end(interrupted);
        // stop the bot again just to make sure ;)
        driveSubsystem.stop();
    }
}
