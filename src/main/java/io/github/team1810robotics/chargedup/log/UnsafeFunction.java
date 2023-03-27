package io.github.team1810robotics.chargedup.log;

@FunctionalInterface
public interface UnsafeFunction<T> {
    T run() throws Throwable;
}
