package io.github.team1810robotics.chargedup;

import static io.github.team1810robotics.chargedup.controller.IO.*;

import static io.github.team1810robotics.chargedup.Constants.*;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import io.github.team1810robotics.chargedup.commands.*;
import io.github.team1810robotics.chargedup.subsystems.*;
import io.github.team1810robotics.chargedup.commands.autonomous.ResetExtender;
import io.github.team1810robotics.chargedup.commands.autonomous.ScoreOutsideCube;
import io.github.team1810robotics.chargedup.commands.autonomous.scoring.*;

public class RobotContainer {

    private SendableChooser<Command> score = new SendableChooser<>();
    private SendableChooser<Command> dock = new SendableChooser<>();
    private SendableChooser<Command> path = new SendableChooser<>();
    public Command autoCommand = null;

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

        configureButtonBindings();
    }

    private void configureButtonBindings() {
        leftJoystick_Button9.onTrue(new InstantCommand(() -> driveSubsystem.zeroGyro()));
        // rightJoystick_Button9.onTrue(new InstantCommand(() -> driveSubsystem.zeroGyro()));

        // setXboxManipulator();
        setManipulator();
    }

    public Command getAutonomousCommand() {
        return sequenceAutoChooserCommands();
    }

    // please stop. : )
    private void setManipulator() {
        box_low.onTrue(new Arm(armSubsystem, ArmConstants.LOW));
        box_mid.onTrue(new Arm(armSubsystem, ArmConstants.MEDIUM));
        box_high.onTrue(new Arm(armSubsystem, ArmConstants.HIGH));
        box_reset.onTrue(new Reset(armSubsystem, extenderSubsystem));

        box_trimUp.whileTrue(new ApplyTrim(armSubsystem, Math.toRadians(0.5)));
        box_trimDown.whileTrue(new ApplyTrim(armSubsystem, Math.toRadians(-0.25)));

        box_altExtenderIn.whileTrue(new Arm(armSubsystem, ArmConstants.HIGH));
        box_altExtenderOut.whileTrue(new Arm(armSubsystem, Math.toRadians(5)));

        box_extenderIn.whileTrue(new Extender(extenderSubsystem, true));
        box_extenderOut.whileTrue(new Extender(extenderSubsystem, false));

        box_intake.whileTrue(new Intake(intakeSubsystem, true));
        box_outtake.whileTrue(new Intake(intakeSubsystem, false));
    }

    /* private void setXboxManipulator() {
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
    } */

    private void populateAutoChooser() {

        score.setDefaultOption("Don't Score", new InstantCommand());
        score.addOption("Run Cube", new ScoreOutsideCube(driveSubsystem, extenderSubsystem, armSubsystem, intakeSubsystem));
        score.addOption("Cone Hi", new HighCone(armSubsystem, extenderSubsystem, intakeSubsystem));
        score.addOption("Cone Mid", new MidCone(armSubsystem, extenderSubsystem, intakeSubsystem));
        score.addOption("Cube Hi", new HighCube(armSubsystem, extenderSubsystem, intakeSubsystem));
        score.addOption("Cube Mid", new MidCube(armSubsystem, extenderSubsystem, intakeSubsystem));
        Shuffleboard.getTab("Autonomous").add("Score", score).withSize(2, 1).withPosition(0, 0);

        path.setDefaultOption("No Path", new ResetExtender(extenderSubsystem));
        // path.setDefaultOption("Grab & ready Dock", new GrabDock(driveSubsystem, extenderSubsystem, armSubsystem, intakeSubsystem));
        // path.setDefaultOption("2 Piece", new ScoreOutsideCube(driveSubsystem, extenderSubsystem, armSubsystem, intakeSubsystem));
        Shuffleboard.getTab("Autonomous").add("Path", path).withSize(2, 1).withPosition(6, 0);

        dock.setDefaultOption("Don't Dock", new InstantCommand());
        dock.addOption("Dock", driveSubsystem.autoBalance(armSubsystem, extenderSubsystem));
        Shuffleboard.getTab("Autonomous").add("Dock", dock).withSize(2, 1).withPosition(2, 0);
    }

    private Command sequenceAutoChooserCommands() {
        return score.getSelected()
                    .andThen(path.getSelected())
                    .andThen(dock.getSelected());
    }
}
