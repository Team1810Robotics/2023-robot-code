package io.github.team1810robotics.chargedup.commands.autonomous.paths;

import com.pathplanner.lib.PathPlanner;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import io.github.team1810robotics.chargedup.Constants.AutoConstants;
import io.github.team1810robotics.chargedup.commands.ApplyTrim;
import io.github.team1810robotics.chargedup.commands.Intake;
import io.github.team1810robotics.chargedup.commands.autonomous.FollowPath;
import io.github.team1810robotics.chargedup.subsystems.ArmSubsystem;
import io.github.team1810robotics.chargedup.subsystems.DriveSubsystem;
import io.github.team1810robotics.chargedup.subsystems.ExtenderSubsystem;
import io.github.team1810robotics.chargedup.subsystems.IntakeSubsystem;

public class GrabShelf extends ParallelDeadlineGroup {

    public GrabShelf(DriveSubsystem drive, ArmSubsystem arm, IntakeSubsystem intake, ExtenderSubsystem extender) {
        super(
            new FollowPath(PathPlanner.loadPath("grabShelf", AutoConstants.MAX_SPEED_METERS_PER_SECOND,
                                                             AutoConstants.MAX_ACCELERATION_METERS_PER_SECOND_SQUARED), drive),
            new ApplyTrim(arm, Math.toRadians(-5)), // TODO: tune value
            new Intake(intake, true)
        );
    }
}
