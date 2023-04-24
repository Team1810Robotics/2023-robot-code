package io.github.team1810robotics.chargedup.log;

/** For running a runnable in the logger */
@FunctionalInterface
public interface UnsafeRunnable {
    void run() throws Throwable;
}
