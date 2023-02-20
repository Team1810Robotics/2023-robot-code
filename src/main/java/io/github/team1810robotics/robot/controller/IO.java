package io.github.team1810robotics.robot.controller;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import io.github.team1810robotics.robot.log.Logger;
import static io.github.team1810robotics.robot.Constants.*;

/** Class that "hides" the button bindings */
public final class IO {
    /**
     * The logger.
     *
     * @since 2018
     */
    //@SuppressWarnings("unused")
    private static final Logger LOG = new Logger();

    public static final Joystick leftJoystick = LOG.catchAll(() -> new Joystick(OIConstants.MOVEMENT_JOYSTICK_PORT));
    public static final Joystick rightJoystick = LOG.catchAll(() -> new Joystick(OIConstants.ROTATION_JOYSTICK_PORT));
    public static final Pipebomb pipebomb = LOG.catchAll(() -> new Pipebomb(OIConstants.MANIPULATOR_CONTROLLER_PORT));

    // Xbox buttons
    /* public static final JoystickButton manipulatorXbox_B      = LOG.catchAll(() -> new JoystickButton(xboxController, 2));
    public static final JoystickButton manipulatorXbox_X      = LOG.catchAll(() -> new JoystickButton(xboxController, 3));
    public static final JoystickButton manipulatorXbox_A      = LOG.catchAll(() -> new JoystickButton(xboxController, 1));
    public static final JoystickButton manipulatorXbox_Y      = LOG.catchAll(() -> new JoystickButton(xboxController, 4));
    public static final JoystickButton manipulatorXbox_LB     = LOG.catchAll(() -> new JoystickButton(xboxController, 5));
    public static final JoystickButton manipulatorXbox_RB     = LOG.catchAll(() -> new JoystickButton(xboxController, 6));
    public static final JoystickButton manipulatorXbox_Back   = LOG.catchAll(() -> new JoystickButton(xboxController, 7));
    public static final JoystickButton manipulatorXbox_Start  = LOG.catchAll(() -> new JoystickButton(xboxController, 8));
    public static final JoystickButton manipulatorXbox_LStick = LOG.catchAll(() -> new JoystickButton(xboxController, 9));
    public static final JoystickButton manipulatorXbox_RStick = LOG.catchAll(() -> new JoystickButton(xboxController, 10)); */

    // Joystick Buttons
    public static final JoystickButton leftJoystick_Trigger  = LOG.catchAll(() -> new JoystickButton(leftJoystick, 1));
    public static final JoystickButton leftJoystick_Button2  = LOG.catchAll(() -> new JoystickButton(leftJoystick, 2));
    public static final JoystickButton leftJoystick_Button3  = LOG.catchAll(() -> new JoystickButton(leftJoystick, 3));
    public static final JoystickButton leftJoystick_Button4  = LOG.catchAll(() -> new JoystickButton(leftJoystick, 4));
    public static final JoystickButton leftJoystick_Button5  = LOG.catchAll(() -> new JoystickButton(leftJoystick, 5));
    public static final JoystickButton leftJoystick_Button6  = LOG.catchAll(() -> new JoystickButton(leftJoystick, 6));
    public static final JoystickButton leftJoystick_Button7  = LOG.catchAll(() -> new JoystickButton(leftJoystick, 7));
    public static final JoystickButton leftJoystick_Button8  = LOG.catchAll(() -> new JoystickButton(leftJoystick, 8));
    public static final JoystickButton leftJoystick_Button9  = LOG.catchAll(() -> new JoystickButton(leftJoystick, 9));
    public static final JoystickButton leftJoystick_Button10 = LOG.catchAll(() -> new JoystickButton(leftJoystick, 10));
    public static final JoystickButton leftJoystick_Button11 = LOG.catchAll(() -> new JoystickButton(leftJoystick, 11));

    // Joystick Buttons
    public static final JoystickButton rightJoystick_Trigger  = LOG.catchAll(() -> new JoystickButton(rightJoystick, 1));
    public static final JoystickButton rightJoystick_Button2  = LOG.catchAll(() -> new JoystickButton(rightJoystick, 2));
    public static final JoystickButton rightJoystick_Button3  = LOG.catchAll(() -> new JoystickButton(rightJoystick, 3));
    public static final JoystickButton rightJoystick_Button4  = LOG.catchAll(() -> new JoystickButton(rightJoystick, 4));
    public static final JoystickButton rightJoystick_Button5  = LOG.catchAll(() -> new JoystickButton(rightJoystick, 5));
    public static final JoystickButton rightJoystick_Button6  = LOG.catchAll(() -> new JoystickButton(rightJoystick, 6));
    public static final JoystickButton rightJoystick_Button7  = LOG.catchAll(() -> new JoystickButton(rightJoystick, 7));
    public static final JoystickButton rightJoystick_Button8  = LOG.catchAll(() -> new JoystickButton(rightJoystick, 8));
    public static final JoystickButton rightJoystick_Button9  = LOG.catchAll(() -> new JoystickButton(rightJoystick, 9));
    public static final JoystickButton rightJoystick_Button10 = LOG.catchAll(() -> new JoystickButton(rightJoystick, 10));
    public static final JoystickButton rightJoystick_Button11 = LOG.catchAll(() -> new JoystickButton(rightJoystick, 11));

    // manipulator buttons
    public static final JoystickButton blueSwitch    = LOG.catchAll(() -> new JoystickButton(pipebomb, Pipebomb.Button.kBlueSwitch.value));
    public static final JoystickButton redSwitchHigh = LOG.catchAll(() -> new JoystickButton(pipebomb, Pipebomb.Button.kRedSwitchH.value));
    public static final JoystickButton redSwitchLow  = LOG.catchAll(() -> new JoystickButton(pipebomb, Pipebomb.Button.kRedSwitchL.value));

    private IO() {/* what does sleep feel like */}
}