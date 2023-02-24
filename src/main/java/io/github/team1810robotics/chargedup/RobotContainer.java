package io.github.team1810robotics.chargedup;

import static io.github.team1810robotics.chargedup.controller.IO.*;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import io.github.team1810robotics.chargedup.commands.*;
import io.github.team1810robotics.chargedup.commands.autonomous.paths.AutoLine;
import io.github.team1810robotics.chargedup.commands.autonomous.paths.FullSpin;
import io.github.team1810robotics.chargedup.commands.autonomous.paths.SpinTest;
import io.github.team1810robotics.chargedup.commands.autonomous.paths.TestPathplanner;
import io.github.team1810robotics.chargedup.commands.testing.ExtenderBool;
import io.github.team1810robotics.chargedup.commands.testing.LifterSpeed;
import io.github.team1810robotics.chargedup.subsystems.*;

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
    public final DriveSubsystem driveSubsystem = new DriveSubsystem();
    public final ExtenderSubsystem extenderSubsystem = new ExtenderSubsystem();
    public final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
    public final ArmSubsystem armSubsystem = new ArmSubsystem();

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
        // rightJoystick_Button9.onTrue(new InstantCommand(() -> driveSubsystem.zeroGyro()));

        manipulatorXbox_X.whileTrue(new ExtenderBool(extenderSubsystem, true));
        manipulatorXbox_B.whileTrue(new ExtenderBool(extenderSubsystem, false));

        manipulatorXbox_A.whileTrue(new LifterSpeed(armSubsystem,  1));
        manipulatorXbox_Y.whileTrue(new LifterSpeed(armSubsystem, -1));

        manipulatorXbox_LStick.whileTrue(new Intake(intakeSubsystem, true));
        manipulatorXbox_RStick.whileTrue(new Intake(intakeSubsystem, false));

        redSwitchHigh.whileTrue(new Intake(intakeSubsystem, true));
        redSwitchLow.whileTrue(new Intake(intakeSubsystem, false));

        rotary0thPos.whileTrue(new ArmExtender(armSubsystem, extenderSubsystem, Math.toRadians(0),  0));
        rotary1stPos.whileTrue(new ArmExtender(armSubsystem, extenderSubsystem, Math.toRadians(18), 0));
        rotary2ndPos.whileTrue(new ArmExtender(armSubsystem, extenderSubsystem, Math.toRadians(36), 0));
        rotary3rdPos.whileTrue(new ArmExtender(armSubsystem, extenderSubsystem, Math.toRadians(54), 0));
        rotary4thPos.whileTrue(new ArmExtender(armSubsystem, extenderSubsystem, Math.toRadians(72), 0));
        rotary5thPos.whileTrue(new ArmExtender(armSubsystem, extenderSubsystem, Math.toRadians(90), 0));
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
