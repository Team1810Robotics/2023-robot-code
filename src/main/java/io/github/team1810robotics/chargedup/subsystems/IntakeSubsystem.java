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

        /** debug stuff */
        Shuffleboard.getTab("Arm").addBoolean("Beam Break", this::lineBreak);
        Shuffleboard.getTab("Arm").addBoolean("Intake Running", this::intakeMove);
    }

    /** found making a boolean control the motor easiest */
    public void intake(boolean in) {
        if (in) {
            motor.set(1);
        } else {
            motor.set(-1);
        }
    }

    /** attempting to set the intake camera to be in color
     * it didnt want to seem to work for us or if it did it would revert
     * halfway through the match
     */
    public void forceDriverCamera() {
        camera.setDriverMode(true);
    }

    /**
     * Switch is active low now
     * @return TRUE - line broken <p>
     *         FALSE - line connected
     */
    public boolean lineBreak() {
        // default is active high. invert to make it active low
        return !lineBreak.get();
    }

    /** wrapper fn to stop the motor */
    public void stop() {
        motor.set(0);
    }

    // thing that josh wanted me to add
    /** @return true if the intake is moving */
    public boolean intakeMove() {
        // take the absolue value of the motor velocity and if its moving return true
        return Math.abs(motor.get()) >= 0.1;
    }
}
