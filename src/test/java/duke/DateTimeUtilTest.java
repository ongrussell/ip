package duke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import org.junit.jupiter.api.Test;

public class DateTimeUtilTest {

    @Test
    public void parseDate_valid_returnsLocalDate() {
        LocalDate d = DateTimeUtil.parseDate("2019-10-15");
        assertEquals(LocalDate.of(2019, 10, 15), d);
    }

    @Test
    public void parseDateTime_valid_returnsExpected() {
        Locale old = Locale.getDefault();
        try {
            Locale.setDefault(Locale.ENGLISH);

            LocalDateTime dt = DateTimeUtil.parseDateTime("2019-12-02 1800");
            assertEquals(LocalDateTime.of(2019, 12, 2, 18, 0), dt);

            String out = DateTimeUtil.formatDateTime(dt);
            assertEquals("Dec 2 2019, 6:00pm", out);
        } finally {
            Locale.setDefault(old);
        }
    }

    @Test
    public void parseDateTime_invalid_throws() {
        assertThrows(DateTimeParseException.class, () ->
                DateTimeUtil.parseDateTime("2019/12/02 1800"));
    }
}
