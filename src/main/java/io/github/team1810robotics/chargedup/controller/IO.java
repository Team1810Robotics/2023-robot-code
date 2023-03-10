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
    public static final Pipebomb pipebomb = Log.catchAll(() -> new Pipebomb(OIConstants.MANIPULATOR_CONTROLLER_PORT));
    public static final XboxController xboxController = new XboxController(3);

    // Xbox buttons
    public static final JoystickButton manipulatorXbox_B      = Log.catchAll(() -> new JoystickButton(xboxController, 2));
    public static final JoystickButton manipulatorXbox_X      = Log.catchAll(() -> new JoystickButton(xboxController, 3));
    public static final JoystickButton manipulatorXbox_A      = Log.catchAll(() -> new JoystickButton(xboxController, 1));
    public static final JoystickButton manipulatorXbox_Y      = Log.catchAll(() -> new JoystickButton(xboxController, 4));
    public static final JoystickButton manipulatorXbox_LB     = Log.catchAll(() -> new JoystickButton(xboxController, 5));
    public static final JoystickButton manipulatorXbox_RB     = Log.catchAll(() -> new JoystickButton(xboxController, 6));
    public static final JoystickButton manipulatorXbox_Back   = Log.catchAll(() -> new JoystickButton(xboxController, 7));
    public static final JoystickButton manipulatorXbox_Start  = Log.catchAll(() -> new JoystickButton(xboxController, 8));
    public static final JoystickButton manipulatorXbox_LStick = Log.catchAll(() -> new JoystickButton(xboxController, 9));
    public static final JoystickButton manipulatorXbox_RStick = Log.catchAll(() -> new JoystickButton(xboxController, 10));

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
    public static final JoystickButton blueSwitch    = Log.catchAll(() -> new JoystickButton(pipebomb, Pipebomb.Button.kBlueSwitch.value));
    public static final JoystickButton redSwitchHigh = Log.catchAll(() -> new JoystickButton(pipebomb, Pipebomb.Button.kRedSwitchH.value));
    public static final JoystickButton redSwitchLow  = Log.catchAll(() -> new JoystickButton(pipebomb, Pipebomb.Button.kRedSwitchL.value));
    public static final JoystickButton rotary0thPos  = Log.catchAll(() -> new JoystickButton(pipebomb, Pipebomb.Button.kRedSwitchL.value));
    public static final JoystickButton rotary1stPos  = Log.catchAll(() -> new JoystickButton(pipebomb, Pipebomb.Button.kRedSwitchL.value));
    public static final JoystickButton rotary2ndPos  = Log.catchAll(() -> new JoystickButton(pipebomb, Pipebomb.Button.kRedSwitchL.value));
    public static final JoystickButton rotary3rdPos  = Log.catchAll(() -> new JoystickButton(pipebomb, Pipebomb.Button.kRedSwitchL.value));
    public static final JoystickButton rotary4thPos  = Log.catchAll(() -> new JoystickButton(pipebomb, Pipebomb.Button.kRedSwitchL.value));
    public static final JoystickButton rotary5thPos  = Log.catchAll(() -> new JoystickButton(pipebomb, Pipebomb.Button.kRedSwitchL.value));

    private IO() {/* what does sleep feel like */}
}