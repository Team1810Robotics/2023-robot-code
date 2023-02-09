package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.ArmConstants.*;

public class IntakeSubsystem extends SubsystemBase {
    private final CANSparkMax intakeMotor;
    // TODO: line break thing

    public IntakeSubsystem() {
        intakeMotor = new CANSparkMax(IntakeConstants.MOTOR_ID, MotorType.kBrushless);
        intakeMotor.setInverted(IntakeConstants.MOTOR_INVERTED);
    }

    public void intake(boolean in) {
        if (in) {
            intakeMotor.set(1);
        } else {
            intakeMotor.set(-1);
        }
    }

    public void stop() {
        intakeMotor.set(0);
    }
}
