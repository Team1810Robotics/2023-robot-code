package io.github.team1810robotics.chargedup.log;

/** For running a function of any type in the logger */
@FunctionalInterface
public interface UnsafeFunction<T> {
    T run() throws Throwable;
}
