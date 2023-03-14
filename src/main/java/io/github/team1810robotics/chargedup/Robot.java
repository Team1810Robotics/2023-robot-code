package io.github.team1810robotics.chargedup;

import static io.github.team1810robotics.chargedup.controller.IO.*;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import io.github.team1810robotics.chargedup.commands.ExtenderBool;
import io.github.team1810robotics.chargedup.commands.Intake;

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
        SmartDashboard.putNumber("extender encoder", m_robotContainer.extenderSubsystem.getDistance());

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
            new ExtenderBool(m_robotContainer.extenderSubsystem, false);
        } else if (pipebomb.getJoystickExtender() == -1) {
            new ExtenderBool(m_robotContainer.extenderSubsystem, true);
        }

        if (pipebomb.getJoystickIntake() == 1) {
            new Intake(m_robotContainer.intakeSubsystem, true);
        } else if (pipebomb.getJoystickIntake() == -1) {
            new Intake(m_robotContainer.intakeSubsystem, false);
        }
    }
}
