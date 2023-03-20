package io.github.team1810robotics.chargedup.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static io.github.team1810robotics.chargedup.Constants.ArmConstants.*;

import org.photonvision.PhotonCamera;

public class IntakeSubsystem extends SubsystemBase {
    private final CANSparkMax intakeMotor;
    private final PhotonCamera camera;
    private final DigitalInput lineBreak;

    public IntakeSubsystem() {
        intakeMotor = new CANSparkMax(IntakeConstants.MOTOR_ID, MotorType.kBrushless);
        intakeMotor.setInverted(IntakeConstants.MOTOR_INVERTED);

        camera = new PhotonCamera(IntakeConstants.CAMERA_NAME);

        lineBreak = new DigitalInput(IntakeConstants.LINE_BREAK_PORT);
    }

    public void intake(boolean in) {
        if (in) {
            intakeMotor.set(1);
        } else {
            intakeMotor.set(-1);
        }
    }

    public void intake(double speed) {
        intakeMotor.set(MathUtil.clamp(speed, -1, 1));
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
        intakeMotor.set(0);
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
