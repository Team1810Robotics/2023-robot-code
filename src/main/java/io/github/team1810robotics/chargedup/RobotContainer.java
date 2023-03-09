package io.github.team1810robotics.chargedup;

import static io.github.team1810robotics.chargedup.controller.IO.*;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import io.github.team1810robotics.chargedup.Constants.ArmConstants;
import io.github.team1810robotics.chargedup.commands.*;
import io.github.team1810robotics.chargedup.subsystems.*;
import io.github.team1810robotics.chargedup.commands.autonomous.paths.*;


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
    public final ArmSubsystem armSubsystem = new ArmSubsystem(extenderSubsystem::getDistance);

    private final Command testPathplanner = new TestPathplanner(driveSubsystem);
    private final Command autoline = new AutoLine(driveSubsystem);
    private final Command spinTest = new SpinTest(driveSubsystem);
    private final Command fullSpin = new FullSpin(driveSubsystem);

    private double trim = 0;

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

        setManipulator();

    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        return pathChooser.getSelected();
    }

    // please stop. : )
    private void setManipulator() {
        manipulatorXbox_A.onTrue(new Arm(armSubsystem, ArmConstants.LOW, trim))
                            .onFalse(new InstantCommand(() -> { trim = 0; }));
        manipulatorXbox_B.onTrue(new Arm(armSubsystem, ArmConstants.MEDIUM, trim))
                            .onFalse(new InstantCommand(() -> { trim = 0; }));
        manipulatorXbox_Y.onTrue(new Arm(armSubsystem, ArmConstants.HIGH, trim))
                            .onFalse(new InstantCommand(() -> { trim = 0; }));
        manipulatorXbox_X.onTrue(new Arm(armSubsystem, ArmConstants.SHELF, trim))
                            .onFalse(new InstantCommand(() -> { trim = 0; }));

        manipulatorXbox_Start.whileTrue(new InstantCommand(() -> setTrim(Math.toRadians(10))));
        manipulatorXbox_Start.whileTrue(new InstantCommand(() -> setTrim(Math.toRadians(-10))));

        manipulatorXbox_RB.whileTrue(new Intake(intakeSubsystem, true));
        manipulatorXbox_LB.whileTrue(new Intake(intakeSubsystem, false));

        manipulatorXbox_RStick.whileTrue(new ExtenderBool(extenderSubsystem, true));
        manipulatorXbox_LStick.whileTrue(new ExtenderBool(extenderSubsystem, false));
    }

    private void setTrim(double incrementValue) {
        trim += incrementValue;
    }
}
