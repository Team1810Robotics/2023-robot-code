package io.github.team1810robotics.chargedup.log;

import java.util.Calendar;

public class Log {
    /**
     * Logs something.
     *
     * @param level   The level of importance of the log message.
     * @param message The message being logged.
     */
    public static void log(LogLevel level, String message) {
        try {
            Calendar cal = Calendar.getInstance();
            String msg = String.format("[%02d:%02d:%02d.%03d] [%s] %s%n", cal.get(Calendar.HOUR),
                    cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND), cal.get(Calendar.MILLISECOND), level, message);
            System.out.printf(msg);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    /**
     * Logs a formatted string.
     *
     * @param level  The level of what is being logged.
     * @param format The format of the log statement.
     * @param args   the arguments for the format string that the log came from.
     */
    public static void log(LogLevel level, String format, Object... args) {
        log(level, String.format(format, args));
    }

    /**
     * Logs a throwable.
     *
     * @param level     The level of what is being logged.
     * @param throwable The throwable being logged.
     * @param message   The message being logged.
     */
    public static void log(LogLevel level, Throwable throwable, String message) {
        log(level, message);
        log(level, "%s: %s", throwable.getClass().getName(), throwable.getMessage());
        for (StackTraceElement element : throwable.getStackTrace()) {
            log(level, "  at %s", element);
        }
        if (throwable.getCause() != null) {
            log(level, throwable.getCause(), "Caused by:");
        }
    }

    /**
     * Logs a throwable formatted with object.
     *
     * @param level     The level of what is being logged.
     * @param throwable The throwable being logged.
     * @param format    The format of what is being logged.
     * @param args      the arguments for the format string.
     */
    public static void log(LogLevel level, Throwable throwable, String format, Object... args) {
        log(level, throwable, String.format(format, args));
    }

    /**
     * Logs a throwable.
     *
     * @param level     The level of what is being logged.
     * @param throwable The throwable being logged.
     */
    public static void log(LogLevel level, Throwable throwable) {
        log(level, throwable, "An Unhandled Exception has Occured");
    }

    /**
     * Logs a trace.
     *
     * @param message The message of what is being traced.
     */
    public static void trace(String message) {
        log(LogLevel.TRACE, message);
    }

    /**
     * Logs a formatted trace.
     *
     * @param format The format of what is being logged.
     * @param args   the arguments for the format string being logged
     */
    public static void trace(String format, Object... args) {
        log(LogLevel.TRACE, format, args);
    }

    /**
     * Logs a trace with a throwable.
     *
     * @param throwable The throwable being logged as a trace.
     * @param message   The message being logged with the trace.
     */
    public static void trace(Throwable throwable, String message) {
        log(LogLevel.TRACE, throwable, message);
    }

    /**
     * Logs a trace with a throwable and a format.
     *
     * @param throwable The throwable being logged with a trace.
     * @param format    The format of logging.
     * @param args      the arguments for the format string of whats being logged.
     */
    public static void trace(Throwable throwable, String format, Object... args) {
        log(LogLevel.TRACE, throwable, format, args);
    }

    /**
     * Logs a throwable at trace level.
     *
     * @param throwable The throwable to be logged.
     */
    public static void trace(Throwable throwable) {
        log(LogLevel.TRACE, throwable);
    }

    /**
     * Logs something with level of enter (entering a method).
     *
     * @param method The method that was entered.
     */
    public static void enter(String method) {
        trace("ENTER %s", method);
    }

    /**
     * Logs something with a level of leave (leaving a method).
     *
     * @param method The method that is being left.
     */
    public static void leave(String method) {
        trace("LEAVE %s", method);
    }

    /**
     * Logs something with the level of debug.
     *
     * @param message The message being logged.
     */
    public static void debug(String message) {
        log(LogLevel.DEBUG, message);
    }

    /**
     * Logs something formatted with level of importance of debug.
     *
     * @param format The format of what is being logged.
     * @param args   the arguments for the format string of the log.
     */
    public static void debug(String format, Object... args) {
        log(LogLevel.DEBUG, format, args);
    }

    /**
     * Logs a throwable with a message at level of debug.
     *
     * @param throwable The throwable to log.
     * @param message   The message to log with the throwable.
     */
    public static void debug(Throwable throwable, String message) {
        log(LogLevel.DEBUG, throwable, message);
    }

    /**
     * Logs a formatted throwable.
     *
     * @param throwable The throwable to log.
     * @param format    The format to log the throwable.
     * @param args      the arguments for the format string to log with the
     *                  throwable.
     */
    public static void debug(Throwable throwable, String format, Object... args) {
        log(LogLevel.DEBUG, throwable, format, args);
    }

    /**
     * Logs a throwable with the level of debug
     *
     * @param throwable The throwable to log
     */
    public static void debug(Throwable throwable) {
        log(LogLevel.DEBUG, throwable);
    }

    /**
     * Logs a message with the level of info.
     *
     * @param message The message to log.
     */
    public static void info(String message) {
        log(LogLevel.INFO, message);
    }

    /**
     * Logs a formatted message with level info.
     *
     * @param format The format to log the info.
     * @param args   the arguments for the format string to log the info.
     */
    public static void info(String format, Object... args) {
        log(LogLevel.INFO, format, args);
    }

    /**
     * Logs a throwable with level of info with a message.
     *
     * @param throwable The throwable to log.
     * @param message   The message to log with the throwable.
     */
    public static void info(Throwable throwable, String message) {
        log(LogLevel.INFO, throwable, message);
    }

    /**
     * Logs a throwable formatted with the level of info.
     *
     * @param throwable The throwable to log.
     * @param format    The format to use to log the throwable.
     * @param args      the arguments for the format string to log with the
     *                  throwable.
     */
    public static void info(Throwable throwable, String format, Object... args) {
        log(LogLevel.INFO, throwable, format, args);
    }

    /**
     * Logs a throwable with the level of info.
     *
     * @param throwable the throwable to log.
     */
    public static void info(Throwable throwable) {
        log(LogLevel.INFO, throwable);
    }

    /**
     * Logs a message with level of warning.
     *
     * @param message The message to warn.
     */
    public static void warn(String message) {
        log(LogLevel.WARN, message);
    }

    /**
     * Logs something with the level warn formatted.
     *
     * @param format The format to log the message.
     * @param args   The message to log.
     */
    public static void warn(String format, Object... args) {
        log(LogLevel.WARN, format, args);
    }

    /**
     * Logs a throwable with a message with level of warn.
     *
     * @param throwable The throwable to log.
     * @param message   The message to log with the throwable.
     */
    public static void warn(Throwable throwable, String message) {
        log(LogLevel.WARN, throwable, message);
    }

    /**
     * Logs a throwable formatted with the class.
     *
     * @param throwable The throwable to log.
     * @param format    The format to log it as.
     * @param args      The class that the message came from.
     */
    public static void warn(Throwable throwable, String format, Object... args) {
        log(LogLevel.WARN, throwable, format, args);
    }

    /**
     * Logs a throwable with as a warn message.
     *
     * @param throwable The throwable to log.
     */
    public static void warn(Throwable throwable) {
        log(LogLevel.WARN, throwable);
    }

    /**
     * Logs a message at error level.
     *
     * @param message The message to log.
     */
    public static void error(String message) {
        log(LogLevel.ERROR, message);
    }

    /**
     * Log a class with an error.
     *
     * @param format The format for the log.
     * @param args   the arguments for the format string throwing the error.
     */
    public static void error(String format, Object... args) {
        log(LogLevel.ERROR, format, args);
    }

    /**
     * Logs a throwable as an error level message with a message.
     *
     * @param throwable The throwable to log.
     * @param message   The message to log with the throwable.
     */
    public static void error(Throwable throwable, String message) {
        log(LogLevel.ERROR, throwable, message);
    }

    /**
     * Logs a throwable formatted with the class that it came from.
     *
     * @param throwable The throwable to log.
     * @param format    The format to log the message.
     * @param args      The arguments for the format string.
     */
    public static void error(Throwable throwable, String format, Object... args) {
        log(LogLevel.ERROR, throwable, format, args);
    }

    /**
     * Logs a throwable at error level.
     *
     * @param throwable The throwable to log.
     */
    public static void error(Throwable throwable) {
        log(LogLevel.ERROR, throwable);
    }

    /**
     * Logs a message with the level of fatal.
     *
     * @param message The message to log.
     */
    public static void fatal(String message) {
        log(LogLevel.FATAL, message);
    }

    /**
     * Logs that a fatal error occured in an object.
     *
     * @param format The format to use to log.
     * @param args   the arguments for the format string that the fatal log came
     *               from.
     */
    public static void fatal(String format, Object... args) {
        log(LogLevel.FATAL, format, args);
    }

    /**
     * Logs a throwable and message with level at fatal.
     *
     * @param throwable The throwable to log.
     * @param message   The message to log with the throwable.
     */
    public static void fatal(Throwable throwable, String message) {
        log(LogLevel.FATAL, throwable, message);
    }

    /**
     * Logs a formatted throwable with the arguments for the format string that it
     * came from.
     *
     * @param throwable The throwable to log.
     * @param format    The format to log the throwable.
     * @param args      the arguments for the format string.
     */
    public static void fatal(Throwable throwable, String format, Object... args) {
        log(LogLevel.FATAL, throwable, format, args);
    }

    /**
     * Logs a throwable with level fatal.
     *
     * @param throwable The throwable to log.
     */
    public static void fatal(Throwable throwable) {
        log(LogLevel.FATAL, throwable);
    }

    /**
     * Logs a throwable as an uncaught exception.
     *
     * @param throwable The throwable to log.
     */
    public static void uncaught(Throwable throwable) {
        fatal(throwable, "Uncaught exception");
    }

    /**
     * Catches an unsafe runnable.
     *
     * @param code The code that needs to be caught.
     */
    public static void catchAll(UnsafeRunnable code) {
        try {
            code.run();
        } catch (Throwable t) {
            uncaught(t);
        }
    }

    /**
     * Catches unsafe code.
     *
     * @param code The code to catch.
     * @param def
     * @return
     */
    public static <T> T catchAll(UnsafeFunction<T> code, T def) {
        try {
            return code.run();
        } catch (Throwable t) {
            uncaught(t);
            return def;
        }
    }

    public static <T> T catchAll(UnsafeFunction<T> code) {
        return catchAll(code, null);
    }
}
