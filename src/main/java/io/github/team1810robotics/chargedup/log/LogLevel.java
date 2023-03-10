package io.github.team1810robotics.chargedup.log;

public enum LogLevel {
    /**
     * One or more key business functionalities are not working and the whole
     * system doesn't fulfill the business functionalities
     */
    FATAL(0),

    /**
     * One or more functionalities are not working, preventing some
     * functionalities from working correctly
     */
    ERROR(1),

    /**
     * Unexpected behavior happened inside the application, but it is
     * continuing its work and the key business features are operating as
     * expected
     */
    WARN(2),

    /**
     * An event happened, the event is purely informative and can be ignored
     * during normal operations
     */
    INFO(3),

    /**
     * A log level used for events considered to be useful during software
     * debugging when more granular information is needed
     */
    DEBUG(4),

    /**
     * A log level describing events showing step by step execution of your
     * code that can be ignored during the standard operation, but may be
     * useful during extended debugging sessions
     */
    TRACE(5);

    int levelId;

    public int id() {
        return levelId;
    }

    private LogLevel(int levelId) {
        this.levelId = levelId;
    }
}
