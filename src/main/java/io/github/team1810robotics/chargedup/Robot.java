package io.github.team1810robotics.chargedup;

import static io.github.team1810robotics.chargedup.controller.IO.*;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
public class Robot extends TimedRobot {
    public static CTREConfigs ctreConfigs;

    private Command m_autonomousCommand;

    private RobotContainer m_robotContainer;

    @Override
    public void robotInit() {
        //PathPlannerServer.startServer(5811);
        ctreConfigs = new CTREConfigs();

        m_robotContainer = new RobotContainer();
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void disabledInit() {}

    @Override
    public void disabledPeriodic() {}

    @Override
    public void autonomousInit() {
        m_autonomousCommand = m_robotContainer.getAutonomousCommand();

        if (m_autonomousCommand != null) {
            m_autonomousCommand.schedule();
        }
    }

    @Override
    public void autonomousPeriodic() {}

    @Override
    public void teleopInit() {
        if (m_autonomousCommand != null) {
            m_autonomousCommand.cancel();
        }
    }

    @Override
    public void teleopPeriodic() {
        // fastest way to get it to work and im lazy
        if (pipebomb.getJoystickExtender() == 1) {
            m_robotContainer.extenderSubsystem.move(false);
        } else if (pipebomb.getJoystickExtender() == -1) {
            m_robotContainer.extenderSubsystem.move(true);
        } else {
            m_robotContainer.extenderSubsystem.stop();
        }

        if (pipebomb.getJoystickIntake() == 1) {
            m_robotContainer.intakeSubsystem.intake(true);
        } else if (pipebomb.getJoystickIntake() == -1) {
            m_robotContainer.intakeSubsystem.intake(false);
        } else {
            m_robotContainer.intakeSubsystem.stop();
        }
    }
}
