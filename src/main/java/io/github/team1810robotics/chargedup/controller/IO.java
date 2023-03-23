package io.github.team1810robotics.chargedup.controller;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import io.github.team1810robotics.chargedup.log.Log;
import static io.github.team1810robotics.chargedup.Constants.*;

/** Class that "hides" the button bindings */
public final class IO {

    public static final Joystick leftJoystick = Log.catchAll(() -> new Joystick(OIConstants.MOVEMENT_JOYSTICK_PORT));
    public static final Joystick rightJoystick = Log.catchAll(() -> new Joystick(OIConstants.ROTATION_JOYSTICK_PORT));
    public static final Box box = Log.catchAll(() -> new Box(OIConstants.MANIPULATOR_CONTROLLER_PORT));
    public static final XboxController xboxController = new XboxController(3);

    // Xbox buttons
    public static final JoystickButton manipulatorXbox_A      = Log.catchAll(() -> new JoystickButton(xboxController, XboxController.Button.kA.value));
    public static final JoystickButton manipulatorXbox_B      = Log.catchAll(() -> new JoystickButton(xboxController, XboxController.Button.kB.value));
    public static final JoystickButton manipulatorXbox_X      = Log.catchAll(() -> new JoystickButton(xboxController, XboxController.Button.kX.value));
    public static final JoystickButton manipulatorXbox_Y      = Log.catchAll(() -> new JoystickButton(xboxController, XboxController.Button.kY.value));
    public static final JoystickButton manipulatorXbox_LB     = Log.catchAll(() -> new JoystickButton(xboxController, XboxController.Button.kLeftBumper.value));
    public static final JoystickButton manipulatorXbox_RB     = Log.catchAll(() -> new JoystickButton(xboxController, XboxController.Button.kRightBumper.value));
    public static final JoystickButton manipulatorXbox_Back   = Log.catchAll(() -> new JoystickButton(xboxController, XboxController.Button.kBack.value));
    public static final JoystickButton manipulatorXbox_Start  = Log.catchAll(() -> new JoystickButton(xboxController, XboxController.Button.kStart.value));
    public static final JoystickButton manipulatorXbox_LStick = Log.catchAll(() -> new JoystickButton(xboxController, XboxController.Button.kLeftStick.value));
    public static final JoystickButton manipulatorXbox_RStick = Log.catchAll(() -> new JoystickButton(xboxController, XboxController.Button.kRightStick.value));

    // Joystick Buttons
    public static final JoystickButton leftJoystick_Trigger  = Log.catchAll(() -> new JoystickButton(leftJoystick, 1));
    public static final JoystickButton leftJoystick_Button2  = Log.catchAll(() -> new JoystickButton(leftJoystick, 2));
    public static final JoystickButton leftJoystick_Button3  = Log.catchAll(() -> new JoystickButton(leftJoystick, 3));
    public static final JoystickButton leftJoystick_Button4  = Log.catchAll(() -> new JoystickButton(leftJoystick, 4));
    public static final JoystickButton leftJoystick_Button5  = Log.catchAll(() -> new JoystickButton(leftJoystick, 5));
    public static final JoystickButton leftJoystick_Button6  = Log.catchAll(() -> new JoystickButton(leftJoystick, 6));
    public static final JoystickButton leftJoystick_Button7  = Log.catchAll(() -> new JoystickButton(leftJoystick, 7));
    public static final JoystickButton leftJoystick_Button8  = Log.catchAll(() -> new JoystickButton(leftJoystick, 8));
    public static final JoystickButton leftJoystick_Button9  = Log.catchAll(() -> new JoystickButton(leftJoystick, 9));
    public static final JoystickButton leftJoystick_Button10 = Log.catchAll(() -> new JoystickButton(leftJoystick, 10));
    public static final JoystickButton leftJoystick_Button11 = Log.catchAll(() -> new JoystickButton(leftJoystick, 11));

    // Joystick Buttons
    public static final JoystickButton rightJoystick_Trigger  = Log.catchAll(() -> new JoystickButton(rightJoystick, 1));
    public static final JoystickButton rightJoystick_Button2  = Log.catchAll(() -> new JoystickButton(rightJoystick, 2));
    public static final JoystickButton rightJoystick_Button3  = Log.catchAll(() -> new JoystickButton(rightJoystick, 3));
    public static final JoystickButton rightJoystick_Button4  = Log.catchAll(() -> new JoystickButton(rightJoystick, 4));
    public static final JoystickButton rightJoystick_Button5  = Log.catchAll(() -> new JoystickButton(rightJoystick, 5));
    public static final JoystickButton rightJoystick_Button6  = Log.catchAll(() -> new JoystickButton(rightJoystick, 6));
    public static final JoystickButton rightJoystick_Button7  = Log.catchAll(() -> new JoystickButton(rightJoystick, 7));
    public static final JoystickButton rightJoystick_Button8  = Log.catchAll(() -> new JoystickButton(rightJoystick, 8));
    public static final JoystickButton rightJoystick_Button9  = Log.catchAll(() -> new JoystickButton(rightJoystick, 9));
    public static final JoystickButton rightJoystick_Button10 = Log.catchAll(() -> new JoystickButton(rightJoystick, 10));
    public static final JoystickButton rightJoystick_Button11 = Log.catchAll(() -> new JoystickButton(rightJoystick, 11));

    // manipulator buttons
    public static final JoystickButton box_reset          = Log.catchAll(() -> new JoystickButton(box, Box.Button.reset.value));
    public static final JoystickButton box_high           = Log.catchAll(() -> new JoystickButton(box, Box.Button.high.value));
    public static final JoystickButton box_mid            = Log.catchAll(() -> new JoystickButton(box, Box.Button.mid.value));
    public static final JoystickButton box_low            = Log.catchAll(() -> new JoystickButton(box, Box.Button.low.value));
    public static final JoystickButton box_trimDown       = Log.catchAll(() -> new JoystickButton(box, Box.Button.trimDown.value));
    public static final JoystickButton box_trimUp         = Log.catchAll(() -> new JoystickButton(box, Box.Button.trimUp.value));
    public static final JoystickButton box_altExtenderOut = Log.catchAll(() -> new JoystickButton(box, Box.Button.altExtenderOut.value));
    public static final JoystickButton box_altExtenderIn  = Log.catchAll(() -> new JoystickButton(box, Box.Button.altExtenderIn.value));
    public static final JoystickButton box_extenderIn     = Log.catchAll(() -> new JoystickButton(box, Box.Button.extenderIn.value));
    public static final JoystickButton box_extenderOut    = Log.catchAll(() -> new JoystickButton(box, Box.Button.extenderOut.value));
    public static final JoystickButton box_intake         = Log.catchAll(() -> new JoystickButton(box, Box.Button.intake.value));
    public static final JoystickButton box_outtake        = Log.catchAll(() -> new JoystickButton(box, Box.Button.outtake.value));

    private IO() {/* what does sleep feel like */}
}