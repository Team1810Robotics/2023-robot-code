package frc.robot;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.commands.*;
import frc.robot.commands.autonomous.paths.AutoLine;
import frc.robot.commands.autonomous.paths.SpinTest;
import frc.robot.commands.autonomous.paths.TestPathplanner;
import frc.robot.subsystems.*;

import static frc.robot.IO.*;

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

    private final Command testPathplanner = new TestPathplanner(driveSubsystem);
    private final Command autoline = new AutoLine(driveSubsystem);
    private final Command spinTest = new SpinTest(driveSubsystem);

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
        pathChooser.addOption("4m Path", autoline);
        pathChooser.addOption("3m spin", spinTest);
        Shuffleboard.getTab("Autonomous").add(pathChooser);

        configureButtonBindings();
    }

    private void configureButtonBindings() {
        leftJoystick_Button9.onTrue(new InstantCommand(() -> driveSubsystem.zeroGyro()));
        rightJoystick_Button9.onTrue(new InstantCommand(() -> driveSubsystem.zeroGyro()));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // FIXME
        return autoline;
        // return pathChooser.getSelected();
    }
}
