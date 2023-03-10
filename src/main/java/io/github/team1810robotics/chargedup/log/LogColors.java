package io.github.team1810robotics.chargedup.log;

public class LogColors {

    public static final String Reset = "\033[0m";
    public static final String Start = "\033[";

    public enum Forground {
        Default("39"),
        Black("30"),
        Red("31"),
        Green("32"),
        Yellow("33"),
        Blue("34"),
        Magenta("35"),
        Cyan("36"),
        White("37");

        String code;

        public String get() {
            return code;
        }

        private Forground(String code) {
            this.code = code;
        }
    }

    public enum Background {
        Default("49"),
        Black("40"),
        Red("41"),
        Green("42"),
        Yellow("43"),
        Blue("44"),
        Magenta("45"),
        Cyan("46"),
        White("47");

        String code;

        public String get() {
            return code;
        }

        private Background(String code) {
            this.code = code;
        }
    }

    public enum Style {
        None(""),
        Bold("1"),
        Italic("3"),
        Underline("4"),
        Strikethrough("9");

        String code;

        public String get() {
            return code;
        }

        private Style(String code) {
            this.code = code;
        }
    }

    public static String color(LogLevel level) {
        switch (level) {
            /* case FATAL:
                return String.format("%s%s;%s;%sm%s%s",
                                     LogColors.Start,
                                     Forground.Black.get(),
                                     Background.Red.get(),
                                     Style.Bold.get(),
                                     level,
                                     LogColors.Reset);
            case ERROR:
                return String.format("%s%s;%sm%s%s",
                                     LogColors.Start,
                                     Forground.Red.get(),
                                     Style.Bold.get(),
                                     level,
                                     LogColors.Reset);
            case WARN:
                return String.format("%s%s;%sm%s%s",
                                     LogColors.Start,
                                     Forground.Yellow.get(),
                                     Style.Bold.get(),
                                     level,
                                     LogColors.Reset);
            case INFO:
                return String.format("%s%sm%s%s",
                                     LogColors.Start,
                                     Forground.White.get(),
                                     level,
                                     LogColors.Reset);

            case DEBUG:
                return String.format("%s%sm%s%s",
                                     LogColors.Start,
                                     Forground.Green.get(),
                                     level,
                                     LogColors.Reset);

            case TRACE:
                return String.format("%s%sm%s%s",
                                     LogColors.Start,
                                     Forground.Blue.get(),
                                     level,
                                     LogColors.Reset); */

            default:
                return String.format("%s", level);
        }
    }
}
