package io.github.team1810robotics.chargedup.log;

@FunctionalInterface
public interface UnsafeRunnable {
    void run() throws Throwable;
}
