package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.ArmConstants.*;

public class IntakeSubsystem extends SubsystemBase {
    private final CANSparkMax intakeMotor;
    private final ColorSensorV3[] colorSensors = new ColorSensorV3[2];
    // TODO: line break thing

    public IntakeSubsystem() {
        intakeMotor = new CANSparkMax(IntakeConstants.MOTOR_ID, MotorType.kBrushless);
        intakeMotor.setInverted(IntakeConstants.MOTOR_INVERTED);
    }

    public Color readColor() {
        // TODO: read proximity and return color from closer color sensor
        return new Color(0, 0, 0);
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
