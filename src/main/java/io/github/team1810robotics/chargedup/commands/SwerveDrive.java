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
    // need to supply the values in a double supplier so that the new
    // controller values keep getting used and dont have stale input values
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
        // apply the deadband to the input to make sure an input too small
        // doesnt make it through or to make it less touchy
        double translationXValue = MathUtil.applyDeadband(translationX.getAsDouble(),  OIConstants.DEADBAND);
        double translationYValue = MathUtil.applyDeadband(translationY.getAsDouble(),  OIConstants.DEADBAND);
        double thetaValue        = MathUtil.applyDeadband(thetaSupplier.getAsDouble(), OIConstants.DEADBAND);

        // multiply the values by their max to scale them up
        // and give to the drive method
        driveSubsystem.drive(
            new Translation2d(translationXValue, translationYValue).times(DriveConstants.MAX_SPEED),
            thetaValue * DriveConstants.MAX_ANGULAR_VELOCITY,
            fieldOriented,
            true
        );
    }

    // when command becomes unscheduled stop the modules so the bot doesnt
    // continue moving
    @Override
    public void end(boolean interrupted) {
        driveSubsystem.stop();
    }
}