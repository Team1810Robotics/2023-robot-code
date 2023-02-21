package io.github.team1810robotics.chargedup.subsystems;

import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static io.github.team1810robotics.chargedup.Constants.*;

public class PoseEstimator extends SubsystemBase {

    private final SwerveDrivePoseEstimator poseEstimator;

    private final VisionSubsystem visionSubsystem;
    private final DriveSubsystem driveSubsystem;

    private final Field2d field2d = new Field2d();

    public PoseEstimator(VisionSubsystem visionSubsystem, DriveSubsystem driveSubsystem) {
        this.visionSubsystem = visionSubsystem;
        this.driveSubsystem = driveSubsystem;

        poseEstimator = new SwerveDrivePoseEstimator(
            DriveConstants.SWERVE_KINEMATICS,
            driveSubsystem.getGyroYaw(),
            driveSubsystem.getModulePositions(),
            new Pose2d());

        Shuffleboard.getTab("Field").addString("Pose", this::getFomattedPose).withPosition(0, 0).withSize(2, 0);
        Shuffleboard.getTab("Field").add("Field", field2d).withPosition(2, 0).withSize(6, 4);
    }

    @Override
    public void periodic() {
        if (!visionSubsystem.targetValid()) return;

        poseEstimator.addVisionMeasurement(VisionSubsystem.botpose().toPose2d(), visionSubsystem.getTimestamp());

        poseEstimator.update(
            driveSubsystem.getGyroYaw(),
            driveSubsystem.getModulePositions());

        field2d.setRobotPose(getCurrentPose());
    }

    public Pose2d getCurrentPose() {
        return poseEstimator.getEstimatedPosition();
    }

    private String getFomattedPose() {
        var pose = getCurrentPose();
        return String.format("(%.2f, %.2f) %.2f degrees",
            pose.getX(),
            pose.getY(),
            pose.getRotation().getDegrees());
    }
}