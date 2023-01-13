package frc.robot.autos;

import static frc.robot.Constants.*;
import frc.robot.subsystems.DriveSubsystem;

import java.util.List;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;

public class exampleAuto extends SequentialCommandGroup {
    public exampleAuto(DriveSubsystem driveSubsystem) {
        TrajectoryConfig config =
            new TrajectoryConfig(
                    AutoConstants.MAX_SPEED_METERS_PER_SECOND,
                    AutoConstants.MAX_ACCELERATION_METERS_PER_SECOND_SQUARED)
                .setKinematics(DriveConstants.SWERVE_KINEMATICS);

        // An example trajectory to follow.  All units in meters.
        Trajectory exampleTrajectory =
            TrajectoryGenerator.generateTrajectory(
                // Start at the origin facing the +X direction
                new Pose2d(0, 0, new Rotation2d(0)),
                // Pass through these two interior waypoints, making an 's' curve path
                List.of(new Translation2d(1, 1), new Translation2d(2, -1)),
                // End 3 meters straight ahead of where we started, facing forward
                new Pose2d(3, 0, new Rotation2d(0)),
                config);

        var thetaController =
            new ProfiledPIDController(
                AutoConstants.PTHETE_CONTROLLER, 0, 0, AutoConstants.THETA_CONTROLLER_CONSTRAINTS);
        thetaController.enableContinuousInput(-Math.PI, Math.PI);

        SwerveControllerCommand swerveControllerCommand =
            new SwerveControllerCommand(
                exampleTrajectory,
                driveSubsystem::getPose,
                DriveConstants.SWERVE_KINEMATICS,
                new PIDController(AutoConstants.PX_CONTROLLER, 0, 0),
                new PIDController(AutoConstants.PY_CONTROLLER, 0, 0),
                thetaController,
                driveSubsystem::setModuleStates,
                driveSubsystem);


        addCommands(
            new InstantCommand(() -> driveSubsystem.resetOdometry(exampleTrajectory.getInitialPose())),
            swerveControllerCommand
        );
    }
}