package io.github.team1810robotics.chargedup.commands.testing;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;
import io.github.team1810robotics.chargedup.subsystems.ArmSubsystem;

/** Bang Bang arm control */
public class BBArm extends CommandBase {

    private ArmSubsystem arm;
    private double setpoint;

    private boolean up;

    public BBArm(ArmSubsystem armSubsystem, double setpointAngleRadians) {
        this.arm = armSubsystem;
        this.setpoint = setpointAngleRadians;

        addRequirements(armSubsystem);
    }

    @Override
    public void initialize() {
        double currentAngle = arm.getDistance();
        up = (currentAngle >= setpoint);
    }

    @Override
    public void execute() {
        double speed = 0.75;
        speed *= (up) ? 1 : -1;

        arm.setSpeed(speed);
    }

    @Override
    public boolean isFinished() {
        if (MathUtil.applyDeadband(arm.getDistance(), 0.1) == 0) // TODO: try diffrent constants for the deadband
            return true;

        return false;
    }

    @Override
    public void end(boolean interrupted) {
        arm.stop();
    }
}
