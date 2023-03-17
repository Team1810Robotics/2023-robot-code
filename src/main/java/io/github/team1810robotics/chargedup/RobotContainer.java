package io.github.team1810robotics.chargedup;

import static io.github.team1810robotics.chargedup.controller.IO.*;

import java.util.ArrayList;
import java.util.List;

import static io.github.team1810robotics.chargedup.Constants.*;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import io.github.team1810robotics.chargedup.commands.*;
import io.github.team1810robotics.chargedup.subsystems.*;
import io.github.team1810robotics.chargedup.commands.autonomous.AutoDock;
import io.github.team1810robotics.chargedup.commands.autonomous.paths.GrabShelf;
import io.github.team1810robotics.chargedup.commands.autonomous.scoring.*;

public class RobotContainer {

    List<SendableChooser<Command>> autoChooser = new ArrayList<>();

    /* Subsystems */
    public final DriveSubsystem driveSubsystem = new DriveSubsystem();
    public final ExtenderSubsystem extenderSubsystem = new ExtenderSubsystem();
    public final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
    public final ArmSubsystem armSubsystem = new ArmSubsystem(extenderSubsystem::getDistance);

    public RobotContainer() {
        driveSubsystem.setDefaultCommand(
            new SwerveDrive(
                driveSubsystem,
                () -> -leftJoystick.getY(),
                () -> -leftJoystick.getX(),
                () -> -leftJoystick.getZ(),
                () -> true));

        populateAutoChooser();
        for (var choices : autoChooser) {
            Shuffleboard.getTab("Autonomous").add(choices);
        }

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
        return sequenceAutoChooserCommands(0.5);
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

        pipebomb_altIntakeIn.whileTrue(new Extender(extenderSubsystem, true));
        pipebomb_altIntakeOut.whileTrue(new Extender(extenderSubsystem, false));
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

        manipulatorXbox_RStick.whileTrue(new Extender(extenderSubsystem, false));
        manipulatorXbox_LStick.whileTrue(new Extender(extenderSubsystem, true));
    }

    private void populateAutoChooser() {
        for (var c : autoChooser) {
            c.setDefaultOption("Null", new InstantCommand());
        }

        var score = autoChooser.get(0);
        var dock = autoChooser.get(1);

        score.addOption("Cone Hi", new HighCone(armSubsystem, extenderSubsystem, intakeSubsystem));
        score.addOption("Cone Mid", new MidCone(armSubsystem, extenderSubsystem, intakeSubsystem));
        score.addOption("Cone Low", new LowCone(armSubsystem, extenderSubsystem, intakeSubsystem));
        score.addOption("Cube Hi", new HighCube(armSubsystem, extenderSubsystem, intakeSubsystem));
        score.addOption("Cube Mid", new MidCube(armSubsystem, extenderSubsystem, intakeSubsystem));
        score.addOption("Cube Low", new LowCube(armSubsystem, extenderSubsystem, intakeSubsystem));
        score.addOption("Auto Dock", new AutoDock(driveSubsystem));

        dock.addOption("Don't Dock", new InstantCommand());
        dock.addOption("Dock", new AutoDock(driveSubsystem));
    }

    /** builds a SequentialCommandGroup for auto */
    private Command sequenceAutoChooserCommands(double delay) {
        Command out = new InstantCommand();
        for (var c : autoChooser) {
            out = out.andThen(new WaitCommand(delay), c.getSelected());
        }
        return out;
    }
}
