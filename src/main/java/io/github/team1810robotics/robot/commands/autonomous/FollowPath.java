package io.github.team1810robotics.robot.commands.autonomous;

import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.commands.PPSwerveControllerCommand;

import edu.wpi.first.math.controller.PIDController;
import io.github.team1810robotics.robot.subsystems.DriveSubsystem;
import static io.github.team1810robotics.robot.Constants.*;

// thin wrapper around PPSwerveControllerCommand to follow a PathPlannerTrajectory
public class FollowPath extends PPSwerveControllerCommand {
    PathPlannerTrajectory trajectory;
    DriveSubsystem driveSubsystem;

    public FollowPath(PathPlannerTrajectory trajectory, DriveSubsystem driveSubsystem) {
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
        super.initialize();
        driveSubsystem.resetOdometry(trajectory.getInitialHolonomicPose());
    }

    @Override
    public void execute() {
        super.execute();
    }

    @Override
    public boolean isFinished() {
        return super.isFinished();
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        driveSubsystem.stop();
    }
}
