package duke;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Main class of the Suu chatbot.
 *
 * <p>In Level-10 (JavaFX), Suu should be used in a GUI-friendly way:
 * the UI calls {@link #getResponse(String)} for each user input, and shows the returned text.</p>
 *
 * <p>This class maintains the in-memory task list and uses {@link Storage} to persist tasks.</p>
 */
public class Suu {
    private static final String NAME = "Suu";

    private final Storage storage;
    private final TaskList tasks;

    private boolean isExit;
    private String loadErrorMessage;

    /**
     * Constructs a Suu instance and attempts to load any previously saved tasks.
     *
     * <p>If loading fails, Suu will start with an empty task list and store the error message
     * so the UI can display it in the welcome message.</p>
     */
    public Suu() {
        this.storage = new Storage("data", "Suu.txt");

        TaskList loaded;
        try {
            loaded = new TaskList(storage.load());
        } catch (SuuException e) {
            loaded = new TaskList();
            loadErrorMessage = e.getMessage();
        }
        this.tasks = loaded;
        this.isExit = false;
    }

    /**
     * Returns Suu's welcome message (for displaying as the first message in the UI).
     *
     * @return Welcome message for the user.
     */
    public String getWelcome() {
        StringBuilder sb = new StringBuilder();
        sb.append("Hello! I'm ").append(NAME).append("\n");
        sb.append("What can I do for you?");
        if (loadErrorMessage != null) {
            sb.append("\n\n");
            sb.append("Oops! ").append(loadErrorMessage);
        }
        return sb.toString();
    }

    /**
     * Returns whether Suu should exit the application.
     *
     * <p>This becomes {@code true} after the user enters the {@code bye} command.</p>
     *
     * @return {@code true} if the application should exit, {@code false} otherwise.
     */
    public boolean isExit() {
        return isExit;
    }

    /**
     * Processes a single line of user input and returns Suu's response as a string.
     *
     * <p>This is the main entry point used by the JavaFX UI. The UI should call this method
     * each time the user sends a message.</p>
     *
     * @param input Full user input line.
     * @return Response text to be displayed to the user.
     */
    public String getResponse(String input) {
        try {
            CommandType command = Parser.parseCommandType(input);

            switch (command) {
            case BYE:
                isExit = true;
                return "Bye. Hope to see you again soon!";

            case LIST:
                return formatList();

            case MARK:
                return formatMark(input);

            case UNMARK:
                return formatUnmark(input);

            case TODO:
                return formatAddTodo(input);

            case DEADLINE:
                return formatAddDeadline(input);

            case EVENT:
                return formatAddEvent(input);

            case DELETE:
                return formatDelete(input);

            case FIND:
                return formatFind(input);

            default:
                return "I don't know what that means? :o";
            }
        } catch (SuuException e) {
            return "Oops! " + e.getMessage();
        }
    }

    /**
     * Formats and returns the list of tasks as a printable string.
     *
     * @return A user-friendly string representation of the current task list.
     */
    private String formatList() {
        if (tasks.size() == 0) {
            return "Here are the tasks in your list:\n  (none)";
        }

        StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
        }
        return sb.toString().trim();
    }

    /**
     * Marks the specified task as done and persists the updated task list.
     *
     * @param input Full user input (e.g. {@code "mark 2"}).
     * @return Response message confirming the task was marked.
     * @throws SuuException If the task index is invalid or saving fails.
     */
    private String formatMark(String input) throws SuuException {
        int index = Parser.parseTaskIndex(input, tasks.size(), "mark");
        Task t = tasks.get(index);

        boolean wasMarked = t.isMarked();
        t.setMarked();

        saveWithRollback(() -> {
            if (!wasMarked) {
                t.unmark();
            }
        });

        return "Nice! I've marked this task as done:\n  " + t;
    }

    /**
     * Unmarks the specified task (sets it as not done) and persists the updated task list.
     *
     * @param input Full user input (e.g. {@code "unmark 2"}).
     * @return Response message confirming the task was unmarked.
     * @throws SuuException If the task index is invalid or saving fails.
     */
    private String formatUnmark(String input) throws SuuException {
        int index = Parser.parseTaskIndex(input, tasks.size(), "unmark");
        Task t = tasks.get(index);

        boolean wasMarked = t.isMarked();
        t.unmark();

        saveWithRollback(() -> {
            if (wasMarked) {
                t.setMarked();
            }
        });

        return "OK! I've marked this task as not done yet:\n  " + t;
    }

    /**
     * Adds a new {@link Todo} task and persists the updated task list.
     *
     * @param input Full user input (e.g. {@code "todo read book"}).
     * @return Response message confirming the task was added.
     * @throws SuuException If the description is invalid or saving fails.
     */
    private String formatAddTodo(String input) throws SuuException {
        String desc = Parser.parseTodoDescription(input);

        Task t = new Todo(desc);
        int addedIndex = tasks.size();
        tasks.add(t);

        saveWithRollback(() -> tasks.remove(addedIndex));

        return "Got it. I've added this task:\n  " + t
                + "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    /**
     * Adds a new {@link Deadline} task and persists the updated task list.
     *
     * @param input Full user input (e.g. {@code "deadline return book /by 2019-10-15"}).
     * @return Response message confirming the task was added.
     * @throws SuuException If parsing fails, date is invalid, or saving fails.
     */
    private String formatAddDeadline(String input) throws SuuException {
        String[] parts = Parser.parseDeadline(input); // [desc, byText]

        LocalDate by;
        try {
            by = DateTimeUtil.parseDate(parts[1]);
        } catch (Exception e) {
            throw new SuuException("Invalid date. Use yyyy-MM-dd (e.g., 2019-10-15)");
        }

        Task t = new Deadline(parts[0], by);
        int addedIndex = tasks.size();
        tasks.add(t);

        saveWithRollback(() -> tasks.remove(addedIndex));

        return "Got it. I've added this task:\n  " + t
                + "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    /**
     * Adds a new {@link Event} task and persists the updated task list.
     *
     * @param input Full user input (e.g. {@code "event meeting /from 2019-12-02 1800 /to 2019-12-02 2000"}).
     * @return Response message confirming the task was added.
     * @throws SuuException If parsing fails, date/time is invalid, or saving fails.
     */
    private String formatAddEvent(String input) throws SuuException {
        String[] parts = Parser.parseEvent(input); // [desc, fromText, toText]

        LocalDateTime from;
        LocalDateTime to;
        try {
            from = DateTimeUtil.parseDateTime(parts[1]);
            to = DateTimeUtil.parseDateTime(parts[2]);
        } catch (Exception e) {
            throw new SuuException("Invalid date/time. Use yyyy-MM-dd HHmm (e.g., 2019-12-02 1800)");
        }

        Task t = new Event(parts[0], from, to);
        int addedIndex = tasks.size();
        tasks.add(t);

        saveWithRollback(() -> tasks.remove(addedIndex));

        return "Got it. I've added this task:\n  " + t
                + "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    /**
     * Deletes the specified task and persists the updated task list.
     *
     * <p>If saving fails, the deletion is rolled back by inserting the task back into the list.</p>
     *
     * @param input Full user input (e.g. {@code "delete 2"}).
     * @return Response message confirming the task was deleted.
     * @throws SuuException If parsing fails, index is invalid, or saving fails.
     */
    private String formatDelete(String input) throws SuuException {
        int index = Parser.parseTaskIndex(input, tasks.size(), "delete");
        Task removed = tasks.remove(index);

        saveWithRollback(() -> tasks.add(index, removed));

        return "Noted. I've removed this task:\n  " + removed
                + "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    /**
     * Finds tasks whose descriptions contain the given keyword and formats the result for display.
     *
     * @param input Full user input (e.g. {@code "find book"}).
     * @return Response message listing matching tasks, or stating none were found.
     * @throws SuuException If the keyword is missing/empty.
     */
    private String formatFind(String input) throws SuuException {
        String keyword = Parser.parseFindKeyword(input);
        ArrayList<Task> matches = tasks.find(keyword);

        if (matches.isEmpty()) {
            return "No matching tasks found for: " + keyword;
        }

        StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
        for (int i = 0; i < matches.size(); i++) {
            sb.append(i + 1).append(". ").append(matches.get(i)).append("\n");
        }
        return sb.toString().trim();
    }

    /**
     * Saves the current task list to storage, rolling back a prior in-memory change if saving fails.
     *
     * <p>This method is used to keep the in-memory {@link TaskList} and on-disk data consistent.
     * Callers should first apply an in-memory mutation (e.g., add/remove/mark a task), then
     * provide a rollback action that reverses that mutation. If {@link Storage#save(java.util.List)}
     * throws a {@link SuuException}, the rollback action is executed and the exception is rethrown.</p>
     *
     * @param rollback A runnable that reverses the in-memory change made by the caller.
     * @throws SuuException If saving to storage fails.
     */
    private void saveWithRollback(Runnable rollback) throws SuuException {
        assert rollback != null : "Rollback action must not be null";
        try {
            storage.save(tasks.asList());
        } catch (SuuException e) {
            rollback.run();
            throw e;
        }
    }
}

