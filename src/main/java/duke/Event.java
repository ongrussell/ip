package duke;

import java.time.LocalDateTime;

/**
 * Represents an event task that occurs during a time period.
 * An event has a start date-time and an end date-time.
 */
public class Event extends Task {
    private final LocalDateTime from;
    private final LocalDateTime to;

    /**
     * Creates an event task with the given description and time range.
     *
     * @param description Description of the event.
     * @param from Start date-time of the event.
     * @param to End date-time of the event.
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the start date-time of this event.
     *
     * @return Event start date-time.
     */
    public LocalDateTime getFrom() {
        return from;
    }

    /**
     * Returns the end date-time of this event.
     *
     * @return Event end date-time.
     */
    public LocalDateTime getTo() {
        return to;
    }

    /**
     * Returns the string representation of this event for display to the user.
     *
     * @return Formatted event string.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + DateTimeUtil.formatDateTime(from)
                + " to: " + DateTimeUtil.formatDateTime(to) + ")";
    }
}
