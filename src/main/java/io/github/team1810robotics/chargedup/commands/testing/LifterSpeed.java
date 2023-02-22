package io.github.team1810robotics.chargedup.commands.testing;

import edu.wpi.first.wpilibj2.command.CommandBase;
import io.github.team1810robotics.chargedup.subsystems.ArmSubsystem;

// "Why did you make that a command?"
public class LifterSpeed extends CommandBase {

    private ArmSubsystem arm;
    private double speed;

    public LifterSpeed(ArmSubsystem armSubsystem, double speed) {
        this.arm = armSubsystem;
        this.speed = speed;

        addRequirements(armSubsystem);
    }

    @Override
    public void execute() {
        arm.setSpeed(speed);
    }

    @Override
    public void end(boolean interrupted) {
        arm.stop();
    }
}
