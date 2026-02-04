package duke;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the in-memory list of tasks and provides operations to manipulate it.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a task list using an existing list of tasks.
     *
     * @param tasks Existing list of tasks to use as the backing store.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the end of the list.
     *
     * @param t Task to add.
     */
    public void add(Task t) {
        tasks.add(t);
    }

    /**
     * Inserts a task at the specified index.
     *
     * @param index 0-based index at which the task should be inserted.
     * @param t Task to insert.
     */
    public void add(int index, Task t) {
        tasks.add(index, t);
    }

    /**
     * Removes the task at the specified index.
     *
     * @param index 0-based index of the task to remove.
     * @return The removed task.
     */
    public Task remove(int index) {
        return tasks.remove(index);
    }

    /**
     * Returns the task at the specified index.
     *
     * @param index 0-based index of the task to retrieve.
     * @return The task at the given index.
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return Current size of the task list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns tasks whose descriptions contain the given keyword (case-insensitive).
     *
     * @param keyword Keyword to search for.
     * @return Matching tasks.
     */
    public ArrayList<Task> find(String keyword) {
        ArrayList<Task> matches = new ArrayList<>();
        String target = keyword.toLowerCase();

        for (Task t : tasks) {
            if (t.getDescription().toLowerCase().contains(target)) {
                matches.add(t);
            }
        }
        return matches;
    }

    /**
     * Returns the underlying list of tasks as a {@link List}.
     *
     * <p>This is mainly used when saving tasks to storage.</p>
     *
     * @return Backing list of tasks.
     */
    public List<Task> asList() {
        return tasks;
    }
}
