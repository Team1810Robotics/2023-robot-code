package io.github.team1810robotics.chargedup.commands.autonomous;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import io.github.team1810robotics.chargedup.subsystems.DriveSubsystem;

public class AutoDock extends CommandBase {

    private DriveSubsystem drive;
    private Dock dock;

    public AutoDock(DriveSubsystem driveSubsystem) {
        this.drive = driveSubsystem;
        this.dock = new Dock(driveSubsystem);

        addRequirements(driveSubsystem);
    }

    @Override
    public void execute() {
        double speed = dock.autoBalance();
        drive.drive(new Translation2d(speed, 0), 0, true, false);
    }

    @Override
    public void end(boolean interrupted) {
        drive.stop();
    }
}
