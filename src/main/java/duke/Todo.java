package duke;

/**
 * Represents a todo task that has only a description (no date/time).
 */
public class Todo extends Task {

    /**
     * Creates a todo task with the given description.
     *
     * @param description Description of the todo task.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns the string representation of this todo task for display to the user.
     *
     * @return Formatted todo string.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

}
