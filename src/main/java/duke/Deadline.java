package duke;

import java.time.LocalDate;

/**
 * Represents a task that has a deadline date.
 */
public class Deadline extends Task {
    private final LocalDate by;

    /**
     * Creates a deadline task with the given description and due date.
     *
     * @param description Description of the deadline task.
     * @param by Due date of the task.
     */
    public Deadline(String description, LocalDate by) {
        super(description);
        assert by != null : "Deadline 'by' date must not be null";
        this.by = by;
    }


    /**
     * Returns the due date of this deadline task.
     *
     * @return Deadline due date.
     */
    public LocalDate getBy() {
        return by;
    }

    /**
     * Returns the string representation of this deadline task for display to the user.
     *
     * @return Formatted deadline string.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + DateTimeUtil.formatDate(by) + ")";
    }
}
