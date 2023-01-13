package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.log.Logger;

/** Class that "hides" the button bindings */
public final class IO {
    /**
     * The logger.
     *
     * @since 2018
     */
    //@SuppressWarnings("unused")
    private static final Logger LOG = new Logger();

    public static final Joystick leftJoystick = LOG.catchAll(() -> new Joystick(0));
    public static final Joystick rightJoystick = LOG.catchAll(() -> new Joystick(1));
    public static final XboxController xboxController = LOG.catchAll(() -> new XboxController(2));

    // Xbox buttons
    public static final JoystickButton ManipulatorXbox_B = LOG.catchAll(() -> new JoystickButton(xboxController, 2));
    public static final JoystickButton ManipulatorXbox_X = LOG.catchAll(() -> new JoystickButton(xboxController, 3));
    public static final JoystickButton ManipulatorXbox_A = LOG.catchAll(() -> new JoystickButton(xboxController, 1));
    public static final JoystickButton ManipulatorXbox_Y = LOG.catchAll(() -> new JoystickButton(xboxController, 4));
    public static final JoystickButton ManipulatorXbox_LB = LOG.catchAll(() -> new JoystickButton(xboxController, 5));
    public static final JoystickButton ManipulatorXbox_RB = LOG.catchAll(() -> new JoystickButton(xboxController, 6));
    public static final JoystickButton ManipulatorXbox_Back = LOG.catchAll(() -> new JoystickButton(xboxController, 7));
    public static final JoystickButton ManipulatorXbox_Start = LOG.catchAll(() -> new JoystickButton(xboxController, 8));
    public static final JoystickButton ManipulatorXbox_LStick = LOG.catchAll(() -> new JoystickButton(xboxController, 9));
    public static final JoystickButton ManipulatorXbox_RStick = LOG.catchAll(() -> new JoystickButton(xboxController, 10));

    // Joystick Buttons
    public static final JoystickButton LeftJoystick_Trigger = LOG.catchAll(() -> new JoystickButton(leftJoystick, 1));
    public static final JoystickButton LeftJoystick_Button2 = LOG.catchAll(() -> new JoystickButton(leftJoystick, 2));
    public static final JoystickButton LeftJoystick_Button3 = LOG.catchAll(() -> new JoystickButton(leftJoystick, 3));
    public static final JoystickButton LeftJoystick_Button4 = LOG.catchAll(() -> new JoystickButton(leftJoystick, 4));
    public static final JoystickButton LeftJoystick_Button5 = LOG.catchAll(() -> new JoystickButton(leftJoystick, 5));
    public static final JoystickButton LeftJoystick_Button6 = LOG.catchAll(() -> new JoystickButton(leftJoystick, 6));
    public static final JoystickButton LeftJoystick_Button7 = LOG.catchAll(() -> new JoystickButton(leftJoystick, 7));
    public static final JoystickButton LeftJoystick_Button8 = LOG.catchAll(() -> new JoystickButton(leftJoystick, 8));
    public static final JoystickButton LeftJoystick_Button9 = LOG.catchAll(() -> new JoystickButton(leftJoystick, 9));
    public static final JoystickButton LeftJoystick_Button10 = LOG.catchAll(() -> new JoystickButton(leftJoystick, 10));
    public static final JoystickButton LeftJoystick_Button11 = LOG.catchAll(() -> new JoystickButton(leftJoystick, 11));

    // Joystick Buttons
    public static final JoystickButton RightJoystick_Trigger = LOG.catchAll(() -> new JoystickButton(rightJoystick, 1));
    public static final JoystickButton RightJoystick_Button2 = LOG.catchAll(() -> new JoystickButton(rightJoystick, 2));
    public static final JoystickButton RightJoystick_Button3 = LOG.catchAll(() -> new JoystickButton(rightJoystick, 3));
    public static final JoystickButton RightJoystick_Button4 = LOG.catchAll(() -> new JoystickButton(rightJoystick, 4));
    public static final JoystickButton RightJoystick_Button5 = LOG.catchAll(() -> new JoystickButton(rightJoystick, 5));
    public static final JoystickButton RightJoystick_Button6 = LOG.catchAll(() -> new JoystickButton(rightJoystick, 6));
    public static final JoystickButton RightJoystick_Button7 = LOG.catchAll(() -> new JoystickButton(rightJoystick, 7));
    public static final JoystickButton RightJoystick_Button8 = LOG.catchAll(() -> new JoystickButton(rightJoystick, 8));
    public static final JoystickButton RightJoystick_Button9 = LOG.catchAll(() -> new JoystickButton(rightJoystick, 9));
    public static final JoystickButton RightJoystick_Button10 = LOG.catchAll(() -> new JoystickButton(rightJoystick, 10));
    public static final JoystickButton RightJoystick_Button11 = LOG.catchAll(() -> new JoystickButton(rightJoystick, 11));

    private IO() {/* what does sleep feel like */}
}