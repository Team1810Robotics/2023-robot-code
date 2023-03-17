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
        leftJoystick_Button11.onTrue(new GrabShelf(driveSubsystem, armSubsystem, intakeSubsystem, extenderSubsystem).andThen(new Reset(armSubsystem, extenderSubsystem)));
        // rightJoystick_Button9.onTrue(new InstantCommand(() -> driveSubsystem.zeroGyro()));

        setXboxManipulator();
        setManipulator();
    }

    public Command getAutonomousCommand() {
        return pathChooser.getSelected();
    }

    // please stop. : )
    private void setManipulator() {
        pipebomb_low.onTrue(new Arm(armSubsystem, ArmConstants.LOW));
        pipebomb_mid.onTrue(new Arm(armSubsystem, ArmConstants.MEDIUM));
        pipebomb_high.onTrue(new Arm(armSubsystem, ArmConstants.HIGH));
        pipebomb_reset.onTrue(new Reset(armSubsystem, extenderSubsystem));

        pipebomb_trimUp.whileTrue(new ApplyTrim(armSubsystem, Math.toRadians(0.5)));
        pipebomb_trimDown.whileTrue(new ApplyTrim(armSubsystem, Math.toRadians(-0.25)));

        pipebomb_altExtenderIn.whileTrue(new Intake(intakeSubsystem, true));
        pipebomb_altExtenderOut.whileTrue(new Intake(intakeSubsystem, false));

        pipebomb_altIntakeIn.whileTrue(new ExtenderBool(extenderSubsystem, true));
        pipebomb_altIntakeOut.whileTrue(new ExtenderBool(extenderSubsystem, false));
    }

    private void setXboxManipulator() {
        manipulatorXbox_A.onTrue(new Arm(armSubsystem, ArmConstants.LOW));
        manipulatorXbox_B.onTrue(new Arm(armSubsystem, ArmConstants.MEDIUM));
        manipulatorXbox_Y.onTrue(new Arm(armSubsystem, ArmConstants.HIGH));
        manipulatorXbox_X.onTrue(new Reset(armSubsystem, extenderSubsystem));

        manipulatorXbox_Start.whileTrue(new ApplyTrim(armSubsystem, Math.toRadians(0.25)));
        manipulatorXbox_Back.whileTrue(new ApplyTrim(armSubsystem, Math.toRadians(-0.25)));

        manipulatorXbox_RB.whileTrue(new Intake(intakeSubsystem, true));
        manipulatorXbox_LB.whileTrue(new Intake(intakeSubsystem, false));

        manipulatorXbox_RStick.whileTrue(new ExtenderBool(extenderSubsystem, false));
        manipulatorXbox_LStick.whileTrue(new ExtenderBool(extenderSubsystem, true));
    }
}
