package io.github.team1810robotics.chargedup.commands;

import io.github.team1810robotics.chargedup.subsystems.DriveSubsystem;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;

import static io.github.team1810robotics.chargedup.Constants.*;


public class SwerveDrive extends CommandBase {
    private DriveSubsystem driveSubsystem;
    private DoubleSupplier translationX;
    private DoubleSupplier translationY;
    private DoubleSupplier thetaSupplier;
    private boolean fieldOriented;

    public SwerveDrive(DriveSubsystem driveSubsystem, DoubleSupplier translationX, DoubleSupplier translationY, DoubleSupplier thetaSupplier, BooleanSupplier fieldOriented) {
        this.driveSubsystem = driveSubsystem;
        addRequirements(driveSubsystem);

        this.translationX = translationX;
        this.translationY = translationY;
        this.thetaSupplier = thetaSupplier;
        this.fieldOriented = fieldOriented.getAsBoolean();
    }

    @Override
    public void execute() {
        /* Get Values, Deadband*/
        double translationXValue = MathUtil.applyDeadband(translationX.getAsDouble(),  OIConstants.DEADBAND);
        double translationYValue = MathUtil.applyDeadband(translationY.getAsDouble(),  OIConstants.DEADBAND);
        double thetaValue        = MathUtil.applyDeadband(thetaSupplier.getAsDouble(), OIConstants.DEADBAND);

        /* Drive */
        driveSubsystem.drive(
            new Translation2d(translationXValue, translationYValue).times(DriveConstants.MAX_SPEED),
            thetaValue * DriveConstants.MAX_ANGULAR_VELOCITY,
            fieldOriented,
            true
        );
    }

    @Override
    public void end(boolean interrupted) {
        driveSubsystem.stop();
    }
}