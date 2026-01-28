package duke;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    public static final DateTimeFormatter INPUT_DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter INPUT_DATE_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");


    public static final DateTimeFormatter OUTPUT_DATE = DateTimeFormatter.ofPattern("MMM d yyyy");
    public static final DateTimeFormatter OUTPUT_DATE_TIME = DateTimeFormatter.ofPattern("MMM d yyyy, h:mma");

    public static LocalDate parseDate(String s) {
        return LocalDate.parse(s.trim(), INPUT_DATE);
    }

    public static LocalDateTime parseDateTime(String s) {
        return LocalDateTime.parse(s.trim(), INPUT_DATE_TIME);
    }

    public static String formatDate(LocalDate d) {
        return d.format(OUTPUT_DATE);
    }

    public static String formatDateTime(LocalDateTime dt) {
        return dt.format(OUTPUT_DATE_TIME);
    }
}