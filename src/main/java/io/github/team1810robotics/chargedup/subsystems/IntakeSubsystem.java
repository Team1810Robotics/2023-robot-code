package io.github.team1810robotics.chargedup.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static io.github.team1810robotics.chargedup.Constants.ArmConstants.*;

import org.photonvision.PhotonCamera;

public class IntakeSubsystem extends SubsystemBase {
    private final CANSparkMax motor;
    private final PhotonCamera camera;
    private final DigitalInput lineBreak;

    public IntakeSubsystem() {
        motor = new CANSparkMax(IntakeConstants.MOTOR_ID, MotorType.kBrushless);
        motor.setInverted(IntakeConstants.MOTOR_INVERTED);

        camera = new PhotonCamera(IntakeConstants.CAMERA_NAME);

        lineBreak = new DigitalInput(IntakeConstants.LINE_BREAK_PORT);

        Shuffleboard.getTab("Arm").addBoolean("Beam Break", this::lineBreak);
        Shuffleboard.getTab("Arm").addBoolean("Intake Running", this::intakeMove);
    }

    public void intake(boolean in) {
        if (in) {
            motor.set(1);
        } else {
            motor.set(-1);
        }
    }

    public void forceDriverCamera() {
        camera.setDriverMode(true);
    }

    /**
     * By default the linebreak is HIGH if the
     * line is NOT broken and LOW if it is
     * @return TRUE - line broken <p>
     *         FALSE - line connected
     */
    public boolean lineBreak() {
        return !lineBreak.get();
    }

    public void stop() {
        motor.set(0);
    }

    public boolean intakeMove() {
        return Math.abs(motor.get()) >= 0.1;
    }

    public boolean hasCube() {
        var result = camera.getLatestResult();
        if (!result.hasTargets())
            return false;

        if (result.getBestTarget().getArea() <= IntakeConstants.MIN_TARGET_AREA)
            return false;

        return true;
    }
}
