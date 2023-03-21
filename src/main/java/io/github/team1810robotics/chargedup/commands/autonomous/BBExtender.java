package io.github.team1810robotics.chargedup.commands.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import io.github.team1810robotics.chargedup.Constants.AutoConstants;
import io.github.team1810robotics.chargedup.subsystems.ExtenderSubsystem;

/** Really should only be used in auto */
// BB stands for "bang-bang" control
public class BBExtender extends CommandBase {

    private ExtenderSubsystem extender;
    private double extenderAmount;

    private boolean direction;

    public BBExtender(ExtenderSubsystem extenderSubsystem, double extenderAmount) {
        this.extender = extenderSubsystem;
        this.extenderAmount = extenderAmount;

        addRequirements(extenderSubsystem);
    }

    @Override
    public void initialize() {
        direction = (extender.getDistance() <= extenderAmount);
    }

    @Override
    public void execute() {
        extender.move(direction);
    }

    @Override
    public boolean isFinished() {
        if (inRange(extender.getDistance() - extenderAmount, AutoConstants.EXTENDER_DEADBAND))
            return true;

        if (extender.getFarLS())
            return true;

        return false;
    }

    @Override
    public void end(boolean interrupted) {
        extender.stop();
    }

    /**
     * @param magnitude number you want to check against deadband
     * @param deadband number to act as the ±{@code deadband}
     * @return true - if in ±{@code deadband}
     */
    private boolean inRange(double magnitude, double deadband) {
        // -deadband ≤ magnitude ≤ deadband
        if (-deadband <= magnitude && magnitude <= deadband)
            return true;

        return false;
    }
}
