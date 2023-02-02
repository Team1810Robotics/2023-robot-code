package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.ArmConstants.*;

public class LifterSubsystem extends SubsystemBase {

    private final CANSparkMax lifterMotor;
    private final RelativeEncoder lifterEncoder;
    // https://docs.wpilib.org/en/stable/docs/software/advanced-controls/controllers/pidcontroller.html
    private final PIDController pidController;

    public LifterSubsystem() {
        // TODO: ask about motor type
        lifterMotor = new CANSparkMax(LiftConstants.MOTOR_ID, MotorType.kBrushless);
        lifterEncoder = lifterMotor.getEncoder();

        pidController =
            new PIDController(LiftConstants.kP,
                              LiftConstants.kI,
                              LiftConstants.kD);
    }

    public void setpoint(double setpoint) {
        // TODO: look at this?
        lifterMotor.set(pidController.calculate(lifterEncoder.getPosition(), setpoint));
    }

    public void stop() {
        lifterMotor.set(0);
    }
}
