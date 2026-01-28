package duke;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @Test
    public void parseTaskIndex_validNumber_returnsCorrectIndex() throws Exception {
        int idx = Parser.parseTaskIndex("mark 2", 5, "mark");
        assertEquals(1, idx);
    }

    @Test
    public void parseTaskIndex_missingNumber_throwsException() {
        SuuException e = assertThrows(SuuException.class,
                () -> Parser.parseTaskIndex("mark", 5, "mark"));
        assertTrue(e.getMessage().toLowerCase().contains("task number"));
    }

    @Test
    public void parseTaskIndex_nonNumeric_throwsException() {
        SuuException e = assertThrows(SuuException.class,
                () -> Parser.parseTaskIndex("mark two", 5, "mark"));
        assertTrue(e.getMessage().toLowerCase().contains("number"));
    }

    @Test
    public void parseTaskIndex_outOfRange_throwsException() {
        SuuException e = assertThrows(SuuException.class,
                () -> Parser.parseTaskIndex("mark 10", 5, "mark"));
        assertTrue(e.getMessage().toLowerCase().contains("does not exist"));
    }
}