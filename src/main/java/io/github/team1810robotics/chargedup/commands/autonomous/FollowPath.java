package io.github.team1810robotics.chargedup.commands.autonomous;

import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.commands.PPSwerveControllerCommand;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import io.github.team1810robotics.chargedup.subsystems.DriveSubsystem;
import static io.github.team1810robotics.chargedup.Constants.*;

// thin wrapper around PPSwerveControllerCommand to follow a PathPlannerTrajectory
public class FollowPath extends PPSwerveControllerCommand {
    PathPlannerTrajectory trajectory;
    DriveSubsystem driveSubsystem;

    public FollowPath(PathPlannerTrajectory trajectory, DriveSubsystem driveSubsystem) {
        super(trajectory,
              driveSubsystem::getPose,
              DriveConstants.SWERVE_KINEMATICS,
              new PIDController(AutoConstants.DriveMotor.kP,
                                AutoConstants.DriveMotor.kI,
                                AutoConstants.DriveMotor.kD),
              new PIDController(AutoConstants.DriveMotor.kP,
                                AutoConstants.DriveMotor.kI,
                                AutoConstants.DriveMotor.kD),
              new PIDController(AutoConstants.SteerMotor.kP,
                                AutoConstants.SteerMotor.kI,
                                AutoConstants.SteerMotor.kD),
              driveSubsystem::setModuleStates,
              driveSubsystem);

        this.trajectory = trajectory;
        this.driveSubsystem = driveSubsystem;

        addRequirements(driveSubsystem);
    }

    // dumb dont do this in real code
    public FollowPath(PathPlannerTrajectory trajectory, DriveSubsystem driveSubsystem, int defaultValue) {
        super(trajectory,
              driveSubsystem::getPose,
              DriveConstants.SWERVE_KINEMATICS,
              new PIDController(
                SmartDashboard.getNumber("Drive P", defaultValue),
                SmartDashboard.getNumber("Drive I", defaultValue),
                SmartDashboard.getNumber("Drive D", defaultValue)),
              new PIDController(
                SmartDashboard.getNumber("Drive P", defaultValue),
                SmartDashboard.getNumber("Drive I", defaultValue),
                SmartDashboard.getNumber("Drive D", defaultValue)),

              new PIDController(
                SmartDashboard.getNumber("Steer P", defaultValue),
                SmartDashboard.getNumber("Steer I", defaultValue),
                SmartDashboard.getNumber("Steer D", defaultValue)),

              driveSubsystem::setModuleStates,
              driveSubsystem);

        this.trajectory = trajectory;
        this.driveSubsystem = driveSubsystem;

        addRequirements(driveSubsystem);
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
        driveSubsystem.restoreYawAfterTrajectory();
    }
}
