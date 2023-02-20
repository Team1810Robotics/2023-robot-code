package io.github.team1810robotics.robot.log;

@FunctionalInterface
public interface UnsafeRunnable {
    void run() throws Throwable;
}
