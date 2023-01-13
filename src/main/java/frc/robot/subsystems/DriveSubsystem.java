package frc.robot.subsystems;

import frc.robot.SwerveModule;
import static frc.robot.Constants.*;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;

import com.ctre.phoenix.sensors.PigeonIMU;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.commands.PPSwerveControllerCommand;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardContainer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase {
    public SwerveDriveOdometry swerveOdometry;
    public SwerveModule[] swerveModules;
    public PigeonIMU gyro;
    private ShuffleboardContainer moduleContainer[] = new ShuffleboardContainer[4];

    public double driveP, driveI, driveD;

    public DriveSubsystem() {
        gyro = new PigeonIMU(DriveConstants.PIGEON_ID);
        gyro.configFactoryDefault();
        zeroGyro();

        swerveModules = new SwerveModule[] {
            new SwerveModule(0, DriveConstants.FL.CONSTANTS),
            new SwerveModule(1, DriveConstants.FR.CONSTANTS),
            new SwerveModule(2, DriveConstants.BL.CONSTANTS),
            new SwerveModule(3, DriveConstants.BR.CONSTANTS)
        };

        swerveOdometry = new SwerveDriveOdometry(DriveConstants.SWERVE_KINEMATICS, getYaw(), getModulePositions());

        setShuffleboard();
    }

    public void drive(Translation2d translation, double rotation, boolean fieldRelative, boolean isOpenLoop) {
        SwerveModuleState[] swerveModuleStates =
            DriveConstants.SWERVE_KINEMATICS.toSwerveModuleStates(
                fieldRelative ? ChassisSpeeds.fromFieldRelativeSpeeds(
                                    translation.getX(), 
                                    translation.getY(), 
                                    rotation, 
                                    getYaw()
                                )
                                : new ChassisSpeeds(
                                    translation.getX(), 
                                    translation.getY(), 
                                    rotation)
                                );
        SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, DriveConstants.MAX_SPEED);

        for (SwerveModule mod : swerveModules) {
            mod.setDesiredState(swerveModuleStates[mod.moduleNumber], isOpenLoop);
        }
    }

    /* Used by SwerveControllerCommand in Auto */
    public void setModuleStates(SwerveModuleState[] desiredStates) {
        SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, DriveConstants.MAX_SPEED);

        for (SwerveModule mod : swerveModules) {
            mod.setDesiredState(desiredStates[mod.moduleNumber], false);
        }
    }

    public Pose2d getPose() {
        return swerveOdometry.getPoseMeters();
    }

    public void resetOdometry(Pose2d pose) {
        swerveOdometry.resetPosition(getYaw(), getModulePositions(), pose);
    }

    public SwerveModuleState[] getModuleStates() {
        SwerveModuleState[] states = new SwerveModuleState[4];
        for (SwerveModule mod : swerveModules) {
            states[mod.moduleNumber] = mod.getState();
        }
        return states;
    }

    public SwerveModulePosition[] getModulePositions() {
        SwerveModulePosition[] positions = new SwerveModulePosition[4];
        for (SwerveModule mod : swerveModules) {
            positions[mod.moduleNumber] = mod.getPosition();
        }
        return positions;
    }

    public void zeroGyro() {
        gyro.setYaw(0);
    }

    public Rotation2d getYaw() {
        return (DriveConstants.INVERT_GYRO) ? Rotation2d.fromDegrees(360 - gyro.getYaw()) : Rotation2d.fromDegrees(gyro.getYaw());
    }

    @Override
    public void periodic() {
        swerveOdometry.update(getYaw(), getModulePositions());

        SmartDashboard.putNumber("Gyro Yaw", getYaw().getDegrees());
        SmartDashboard.putNumber("Drive P", driveP);
        SmartDashboard.putNumber("Drive I", driveI);
        SmartDashboard.putNumber("Drive D", driveD);
    }

    /** path stuff */
    public Command followTrajectoryCommand(PathPlannerTrajectory trajectory, boolean firstPath) {
        return new SequentialCommandGroup(
            new InstantCommand(() -> {
                if (firstPath)
                    this.resetOdometry(trajectory.getInitialHolonomicPose());
            }),
            new PPSwerveControllerCommand(
                trajectory,
                this::getPose,
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
                this::setModuleStates
            )
        );
    }

    private void setShuffleboard() {
        moduleContainer[0] = Shuffleboard.getTab("Modules")
                            .getLayout("Front Left Module", BuiltInLayouts.kList)
                            .withSize(2, 4)
                            .withPosition(0, 0);

        moduleContainer[1] = Shuffleboard.getTab("Modules")
                            .getLayout("Front Right Module", BuiltInLayouts.kList)
                            .withSize(2, 4)
                            .withPosition(2, 0);

        moduleContainer[2] = Shuffleboard.getTab("Modules")
                            .getLayout("Back Left Module", BuiltInLayouts.kList)
                            .withSize(2, 4)
                            .withPosition(4, 0);

        moduleContainer[3] = Shuffleboard.getTab("Modules")
                            .getLayout("Back Right Module", BuiltInLayouts.kList)
                            .withSize(2, 4)
                            .withPosition(6, 0);

        for (SwerveModule mod : swerveModules) {
            moduleContainer[mod.moduleNumber].addNumber("Cancoder Positon",
                    () -> mod.getCanCoder().getDegrees());
            moduleContainer[mod.moduleNumber].addNumber("Integrated Encoder",
                    () -> mod.getPosition().angle.getDegrees());
            moduleContainer[mod.moduleNumber].addNumber("Module Velocity",
                    () -> mod.getState().speedMetersPerSecond);
        }
    }
}