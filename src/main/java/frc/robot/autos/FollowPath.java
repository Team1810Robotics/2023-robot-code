package frc.robot.autos;

import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.commands.PPSwerveControllerCommand;

import edu.wpi.first.math.controller.PIDController;
import frc.robot.subsystems.DriveSubsystem;
import static frc.robot.Constants.*;

public class FollowPath extends PPSwerveControllerCommand {
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
    }
}
