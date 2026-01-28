package duke;

import java.time.LocalDateTime;

public class Event extends Task {
    private final LocalDateTime from;
    private final LocalDateTime to;

    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + DateTimeUtil.formatDateTime(from)
                + " to: " + DateTimeUtil.formatDateTime(to) + ")";
    }
}