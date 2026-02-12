package duke;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Utility methods and formatters for parsing and formatting dates and times used by the application.
 */
public class DateTimeUtil {

    /**
     * Input date format used for parsing dates (e.g. {@code 2019-10-15}).
     */
    public static final DateTimeFormatter INPUT_DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Input date-time format used for parsing date-time strings (e.g. {@code 2019-12-02 1800}).
     */
    public static final DateTimeFormatter INPUT_DATE_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    /**
     * Output date format used for displaying dates (e.g. {@code Oct 15 2019}).
     */
    public static final DateTimeFormatter OUTPUT_DATE = DateTimeFormatter.ofPattern("MMM d yyyy");

    /**
     * Output date-time format used for displaying date-times (e.g. {@code Dec 2 2019, 6:00pm}).
     */
    public static final DateTimeFormatter OUTPUT_DATE_TIME = DateTimeFormatter.ofPattern("MMM d yyyy, h:mma");

    /**
     * Parses a date string using {@link #INPUT_DATE}.
     *
     * @param s Date string to parse (expected format: {@code yyyy-MM-dd}).
     * @return Parsed {@link LocalDate}.
     */
    public static LocalDate parseDate(String s) {
        return LocalDate.parse(s.trim(), INPUT_DATE);
    }

    /**
     * Parses a date-time string using {@link #INPUT_DATE_TIME}.
     *
     * @param s Date-time string to parse (expected format: {@code yyyy-MM-dd HHmm}).
     * @return Parsed {@link LocalDateTime}.
     */
    public static LocalDateTime parseDateTime(String s) {
        return LocalDateTime.parse(s.trim(), INPUT_DATE_TIME);
    }

    /**
     * Formats a {@link LocalDate} for display using {@link #OUTPUT_DATE}.
     *
     * @param d Date to format.
     * @return Formatted date string.
     */
    public static String formatDate(LocalDate d) {
        return d.format(OUTPUT_DATE);
    }

    /**
     * Formats a {@link LocalDateTime} for display using {@link #OUTPUT_DATE_TIME}.
     *
     * @param dt Date-time to format.
     * @return Formatted date-time string.
     */
    public static String formatDateTime(LocalDateTime dt) {
        String s = dt.format(OUTPUT_DATE_TIME); // e.g. "Dec 2 2019, 6:00PM"
        return s.substring(0, s.length() - 2)
                + s.substring(s.length() - 2).toLowerCase(Locale.ENGLISH); // -> "pm"
    }

}
