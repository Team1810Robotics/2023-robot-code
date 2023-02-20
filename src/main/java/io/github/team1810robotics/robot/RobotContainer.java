package io.github.team1810robotics.robot;

import static io.github.team1810robotics.robot.controller.IO.*;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import io.github.team1810robotics.robot.commands.*;
import io.github.team1810robotics.robot.commands.autonomous.paths.AutoLine;
import io.github.team1810robotics.robot.commands.autonomous.paths.FullSpin;
import io.github.team1810robotics.robot.commands.autonomous.paths.SpinTest;
import io.github.team1810robotics.robot.commands.autonomous.paths.TestPathplanner;
import io.github.team1810robotics.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * 
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

    SendableChooser<Command> pathChooser = new SendableChooser<>();

    /* Subsystems */
    private final DriveSubsystem driveSubsystem = new DriveSubsystem();
    private final ExtenderSubsystem extenderSubsystem = new ExtenderSubsystem();

    private final Command testPathplanner = new TestPathplanner(driveSubsystem);
    private final Command autoline = new AutoLine(driveSubsystem);
    private final Command spinTest = new SpinTest(driveSubsystem);
    private final Command fullSpin = new FullSpin(driveSubsystem);

    public RobotContainer() {
        driveSubsystem.setDefaultCommand(
            new SwerveDrive(
                driveSubsystem,
                () -> -leftJoystick.getY(),
                () -> -leftJoystick.getX(),
                () -> -leftJoystick.getZ(),
                () -> true));

        pathChooser.setDefaultOption("Null Path", new InstantCommand(() -> {}));
        pathChooser.addOption("Test Pathplanner", testPathplanner);
        pathChooser.addOption("4m autoline", autoline);
        pathChooser.addOption("5m spin", spinTest);
        pathChooser.addOption("360 spin", fullSpin);
        Shuffleboard.getTab("Autonomous").add(pathChooser);

        configureButtonBindings();
    }

    private void configureButtonBindings() {
        leftJoystick_Button9.onTrue(new InstantCommand(() -> driveSubsystem.zeroGyro()));
        rightJoystick_Button9.onTrue(new InstantCommand(() -> driveSubsystem.zeroGyro()));

        redSwitchHigh.whileTrue(new Extender(extenderSubsystem, true));
        redSwitchLow.whileTrue(new Extender(extenderSubsystem, false));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        return pathChooser.getSelected();
    }
}
