package io.github.team1810robotics.chargedup.commands.autonomous;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import io.github.team1810robotics.chargedup.Constants.ArmConstants;
import io.github.team1810robotics.chargedup.subsystems.ArmSubsystem;
import io.github.team1810robotics.chargedup.subsystems.DriveSubsystem;
import io.github.team1810robotics.chargedup.subsystems.ExtenderSubsystem;

public class AutoDock extends CommandBase {

    private DriveSubsystem drive;
    private ArmSubsystem arm;
    private ExtenderSubsystem extender;
    private Dock dock;

    public AutoDock(ArmSubsystem armSubsystem, DriveSubsystem driveSubsystem, ExtenderSubsystem extenderSubsystem) {
        this.drive = driveSubsystem;
        this.arm = armSubsystem;
        this.extender = extenderSubsystem;
        this.dock = new Dock(driveSubsystem);

        addRequirements(driveSubsystem);
    }

    @Override
    public void initialize() {
        arm.setGoal(ArmConstants.LOW);
    }

    @Override
    public void execute() {
        if (!extender.getCloseLS()) {
            extender.move(false);
        } else {
            extender.stop();
        }

        double speed = dock.autoBalance();
        drive.drive(new Translation2d(-speed, 0), 0, true, false);
    }

    @Override
    public void end(boolean interrupted) {
        drive.stop();
    }
}
