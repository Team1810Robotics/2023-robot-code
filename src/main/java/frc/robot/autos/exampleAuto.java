package frc.robot.autos;

import static frc.robot.Constants.*;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;

import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class exampleAuto extends SequentialCommandGroup {
    public exampleAuto(DriveSubsystem driveSubsystem) {
        PathPlannerTrajectory trajectory1 = PathPlanner.loadPath("defaultPath",
                                AutoConstants.MAX_SPEED_METERS_PER_SECOND,
                                AutoConstants.MAX_ACCELERATION_METERS_PER_SECOND_SQUARED);

        addCommands(
            new InstantCommand(() -> driveSubsystem.zeroGyro())
        );
    }
}