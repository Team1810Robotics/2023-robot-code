package frc.robot.commands;

import frc.robot.subsystems.DriveSubsystem;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;

import static frc.robot.Constants.*;


public class SwerveDrive extends CommandBase {
    private DriveSubsystem driveSubsystem;
    private DoubleSupplier translationX;
    private DoubleSupplier translationY;
    private DoubleSupplier thetaSupplier;
    private BooleanSupplier fieldOriented;

    public SwerveDrive(DriveSubsystem driveSubsystem, DoubleSupplier translationX, DoubleSupplier translationY, DoubleSupplier thetaSupplier, BooleanSupplier fieldOriented) {
        this.driveSubsystem = driveSubsystem;
        addRequirements(driveSubsystem);

        this.translationX = translationX;
        this.translationY = translationY;
        this.thetaSupplier = thetaSupplier;
        this.fieldOriented = fieldOriented;
    }

    @Override
    public void execute() {
        /* Get Values, Deadband*/
        double translationXValue = MathUtil.applyDeadband(translationX.getAsDouble(), OIConstants.DEADBAND);
        double translationYValue = MathUtil.applyDeadband(translationY.getAsDouble(), OIConstants.DEADBAND);
        double thetaValue = MathUtil.applyDeadband(thetaSupplier.getAsDouble(), OIConstants.DEADBAND);

        /* Drive */
        driveSubsystem.drive(
            new Translation2d(translationXValue, translationYValue).times(DriveConstants.maxSpeed), 
            thetaValue * DriveConstants.maxAngularVelocity, 
            fieldOriented.getAsBoolean(), 
            true
        );
    }
}