package duke;

/**
 * Represents a generic task with a description and a completion status.
 * Specific task types (e.g. {@link Todo}, {@link Deadline}, {@link Event}) can extend this class.
 */
public class Task {
    private String description;
    private boolean isMarked;

    /**
     * Creates a task with the given description.
     * The task is initially marked as not done.
     *
     * @param description Description of the task.
     */
    public Task(String description) {
        assert description != null : "Task description must not be null";
        this.description = description;
        this.isMarked = false;
    }


    /**
     * Marks this task as done.
     */
    public void setMarked() {
        isMarked = true;
    }

    /**
     * Marks this task as not done.
     */
    public void unmark() {
        isMarked = false;
    }

    /**
     * Returns the description of this task.
     *
     * @return Task description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns whether this task is marked as done.
     *
     * @return {@code true} if marked as done, {@code false} otherwise.
     */
    public boolean isMarked() {
        return isMarked;
    }

    /**
     * Returns the string representation of this task for display to the user.
     *
     * @return Formatted task string.
     */
    @Override
    public String toString() {
        String status = isMarked ? "X" : " ";
        return "[" + status + "] " + description;
    }
}
