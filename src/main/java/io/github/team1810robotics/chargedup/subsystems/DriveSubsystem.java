package io.github.team1810robotics.chargedup.subsystems;

import io.github.team1810robotics.chargedup.SwerveModule;
import io.github.team1810robotics.chargedup.commands.autonomous.ResetExtender;

import static io.github.team1810robotics.chargedup.Constants.*;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;

import com.ctre.phoenix.sensors.Pigeon2;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardContainer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase {
    public SwerveDriveOdometry swerveOdometry;
    public SwerveModule swerveModules[];
    public Pigeon2 gyro;
    private ShuffleboardContainer moduleContainer[] = new ShuffleboardContainer[4];

    public DriveSubsystem() {
        gyro = new Pigeon2(DriveConstants.PIGEON_ID);
        gyro.configFactoryDefault();
        zeroGyro();

        // make an array of the swerve module objects
        // its just easier this way
        swerveModules = new SwerveModule[] {
            new SwerveModule(0, DriveConstants.FL.CONSTANTS),
            new SwerveModule(1, DriveConstants.FR.CONSTANTS),
            new SwerveModule(2, DriveConstants.BL.CONSTANTS),
            new SwerveModule(3, DriveConstants.BR.CONSTANTS)
        };

        // set up the odometry
        swerveOdometry = new SwerveDriveOdometry(DriveConstants.SWERVE_KINEMATICS, getGyroYaw(), getModulePositions());

        // set debug stuff
        setShuffleboard();
    }

    public void drive(Translation2d translation, double rotation, boolean fieldRelative, boolean isOpenLoop) {
        // calculate the swerve module states based off of the input from the controllers
        SwerveModuleState[] swerveModuleStates =
            DriveConstants.SWERVE_KINEMATICS.toSwerveModuleStates(
                fieldRelative ?
                    // accounts for the gyro allowing field relativity
                    ChassisSpeeds.fromFieldRelativeSpeeds(
                            translation.getX(),
                            translation.getY(),
                            rotation,
                            getGyroYaw()) :
                    // just speeds and rotation (bot relativity)
                    new ChassisSpeeds(
                            translation.getX(),
                            translation.getY(),
                            rotation));
        // make sure that one module isnt going to fast and scale them down
        // proportionally if one is
        SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, ((DriverStation.isAutonomousEnabled()) ? AutoConstants.MAX_SPEED : DriveConstants.MAX_SPEED));

        // apply the states to each corresponding module
        for (SwerveModule mod : swerveModules) {
            mod.setDesiredState(swerveModuleStates[mod.moduleNumber], isOpenLoop);
        }
    }

    // Used by SwerveControllerCommand in Auto
    public void setModuleStates(SwerveModuleState[] desiredStates) {
        // make sure that one module isnt going to fast and scale them down
        // proportionally if one is
        SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, AutoConstants.MAX_SPEED);

        // apply the desired state to each module
        for (SwerveModule mod : swerveModules) {
            mod.setDesiredState(desiredStates[mod.moduleNumber], false);
        }
    }

    /**
     * pose repersents the position on the field and the rotation of the bot
     * @return the pose of the bot
     */
    public Pose2d getPose() {
        return swerveOdometry.getPoseMeters();
    }

    /** resets the odometry to a supplied pose with the module states and gyro yaw */
    public void resetOdometry(Pose2d pose) {
        swerveOdometry.resetPosition(getGyroYaw(), getModulePositions(), pose);
    }

    /**
     * state is the angle of the module and the speed its at
     * @return the module states as and array of four modules
     */
    public SwerveModuleState[] getModuleStates() {
        SwerveModuleState[] states = new SwerveModuleState[4];
        // loops through the modules and places them into the above array in
        // the correct spot with their state info
        for (SwerveModule mod : swerveModules) {
            states[mod.moduleNumber] = mod.getState();
        }
        return states;
    }

    /**
     * position is the angle of the module and the distance its traveled
     * @return the module positions as an array of four modules
     */
    public SwerveModulePosition[] getModulePositions() {
        SwerveModulePosition[] positions = new SwerveModulePosition[4];
        // loops through the modules and places them into the above array in
        // the correct spot with their position info
        for (SwerveModule mod : swerveModules) {
            positions[mod.moduleNumber] = mod.getPosition();
        }
        return positions;
    }

    /** zeros the gyro */
    public void zeroGyro() {
        gyro.setYaw(0);
    }

    // yaw in deg
    /**
     * setter wrapper fot the gyro
     * also prints it out i guess
     */
    public void setGyroYaw(double yaw) {
        System.out.println("Set Yaw: " + yaw);
        gyro.setYaw(yaw);
    }

    /** @return the gyro yaw while inverting if needed */
    public Rotation2d getGyroYaw() {
        return (DriveConstants.INVERT_GYRO) ? Rotation2d.fromDegrees(360 - gyro.getYaw()) : Rotation2d.fromDegrees(gyro.getYaw());
    }

    /** calls the stop method for each module to stop the all */
    public void stop() {
        for (SwerveModule module : swerveModules) {
            module.stop();
        }
    }

    /** method that is meant to lock the wheels
     * but i dont think it actually ever worked
     */
    private void setWheelsX() {
        swerveModules[0].setDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(45)),  false);
        swerveModules[1].setDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(-45)), false);
        swerveModules[2].setDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(-45)), false);
        swerveModules[3].setDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(45)),  false);
    }

    /**
     * 1108 based auto balancing
     * sets up a race between doing nothing for 15 secs and balancing
     * whichever finishes first cancels the other on
     */
    public CommandBase autoBalance(ArmSubsystem arm, ExtenderSubsystem extender) {
        return Commands.race(
                Commands.sequence(
                    // reset the arm and put it low
                    new ResetExtender(extender),
                    new InstantCommand(() -> arm.setGoal(ArmConstants.LOW), arm),
                    // drive back fast until you see a large angle increase
                    Commands.run(
                        () -> drive(new Translation2d(-1 / AutoConstants.MAX_SPEED, 0), 0, false, false), this).alongWith(new PrintCommand("slow start"))
                            .until(() -> Math.abs(getRoll()) >= 25),
                    // dive back slower until the angle drops below 12 deg
                    Commands.run(
                        () -> drive(new Translation2d(-0.3 / AutoConstants.MAX_SPEED, 0), 0, false, false), this).alongWith(new PrintCommand("int 1"))
                            .until(() -> Math.abs(getRoll()) <= 12),
                    // Commands.run(
                    //     () -> drive(new Translation2d(-0.3 / AutoConstants.MAX_SPEED, 0), 0, false, false), this).alongWith(new PrintCommand("int 2"))
                    //         .until(() -> Math.abs(getRoll()) >= 15),
                    // TBH IDK why this is here it really isnt needed
                    Commands.run(
                        () -> drive(new Translation2d(-0.22 / AutoConstants.MAX_SPEED, 0), 0, false, false), this).alongWith(new PrintCommand("finish"))
                            .until(() -> Math.abs(getRoll()) <= 12.55),
                    // lock wheels
                    Commands.run(this::setWheelsX, this)),
                Commands.waitSeconds(15));
    }

    private double getRoll() {
        // not a direct return because we wanted to log it to test
        // should probably be a direct return now
        var r = gyro.getRoll();
        return r;
    }

    @Override
    public void periodic() {
        swerveOdometry.update(getGyroYaw(), getModulePositions());
        SmartDashboard.putNumber("Gyro Yaw", getGyroYaw().getDegrees());
        SmartDashboard.putNumber("Gyro Temp in F", gyro.getTemp() * (9. / 5.) + 32);
        SmartDashboard.putBoolean("Gyro Zero", (Math.abs(getGyroYaw().getDegrees()) % 360) < 0.5);
    }

    // sets the shuffleboard a few values from the modules
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

        // needed for auto development
        Shuffleboard.getTab("Autonomous").addDouble("Roll", this::getRoll);
    }
}