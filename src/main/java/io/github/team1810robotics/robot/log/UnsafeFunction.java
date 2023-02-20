package io.github.team1810robotics.robot.log;

@FunctionalInterface
public interface UnsafeFunction<T> {
    T run() throws Throwable;
}
