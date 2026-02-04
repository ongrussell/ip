package duke;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Main class of the Suu chatbot.
 * Handles reading user commands and executing the corresponding task operations.
 *
 * <p>This class wires together {@link Ui}, {@link Parser}, {@link Storage}, and {@link TaskList},
 * and runs the main command-processing loop.</p>
 */
public class Suu {
    private static final String NAME = "Suu";

    private final Ui ui;
    private final Storage storage;
    private final TaskList tasks;

    /**
     * Constructs a Suu instance and attempts to load any previously saved tasks.
     *
     * <p>If loading fails, an empty {@link TaskList} is used and the error message is shown to the user.</p>
     */
    public Suu() {
        this.ui = new Ui();
        this.storage = new Storage("data", "Suu.txt");

        TaskList loaded;
        try {
            loaded = new TaskList(storage.load());
        } catch (SuuException e) {
            ui.showError(e.getMessage());
            loaded = new TaskList();
        }
        this.tasks = loaded;
    }

    /**
     * Runs the chatbot loop until the user exits.
     *
     * <p>This method continuously reads user input, parses it into a {@link CommandType},
     * executes the requested operation, and displays the result via {@link Ui}.</p>
     */
    public void run() {
        ui.showWelcome(NAME);

        while (true) {
            String input = ui.readCommand();

            try {
                CommandType command = Parser.parseCommandType(input);

                switch (command) {
                case BYE:
                    ui.showBye();
                    ui.close();
                    return;

                case LIST:
                    ui.showList(tasks);
                    break;

                case MARK:
                    markTask(input);
                    break;

                case UNMARK:
                    unmarkTask(input);
                    break;

                case TODO:
                    addTodo(input);
                    break;

                case DEADLINE:
                    addDeadline(input);
                    break;

                case EVENT:
                    addEvent(input);
                    break;

                case DELETE:
                    deleteTask(input);
                    break;
                case FIND:
                    findTasks(input);
                    break;

                default:
                    throw new SuuException("I don't know what that means? :o");
                }

            } catch (SuuException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    /**
     * Marks the task specified by the user input as done and saves the updated task list.
     *
     * @param input Full user input (e.g. {@code "mark 2"}).
     * @throws SuuException If the task index is invalid or saving fails.
     */
    private void markTask(String input) throws SuuException {
        int index = Parser.parseTaskIndex(input, tasks.size(), "mark");
        Task t = tasks.get(index);

        boolean wasMarked = t.isMarked();
        t.setMarked();

        try {
            storage.save(tasks.asList());
        } catch (SuuException e) {
            if (!wasMarked) {
                t.unmark();
            }
            throw e;
        }

        ui.showMark(t);
    }

    private void findTasks(String input) throws SuuException {
        String keyword = Parser.parseFindKeyword(input);
        java.util.ArrayList<Task> matches = tasks.find(keyword);
        ui.showFindResults(keyword, matches);
    }

    /**
     * Unmarks the task specified by the user input (sets it as not done) and saves the updated task list.
     *
     * @param input Full user input (e.g. {@code "unmark 2"}).
     * @throws SuuException If the task index is invalid or saving fails.
     */
    private void unmarkTask(String input) throws SuuException {
        int index = Parser.parseTaskIndex(input, tasks.size(), "unmark");
        Task t = tasks.get(index);

        boolean wasMarked = t.isMarked();
        t.unmark();

        try {
            storage.save(tasks.asList());
        } catch (SuuException e) {
            if (wasMarked) {
                t.setMarked();
            }
            throw e;
        }

        ui.showUnmark(t);
    }

    /**
     * Adds a new {@link Todo} task using the user input and saves the updated task list.
     *
     * @param input Full user input (e.g. {@code "todo read book"}).
     * @throws SuuException If the todo description is missing/invalid or saving fails.
     */
    private void addTodo(String input) throws SuuException {
        String desc = Parser.parseTodoDescription(input);

        Task t = new Todo(desc);
        tasks.add(t);

        try {
            storage.save(tasks.asList());
        } catch (SuuException e) {
            tasks.remove(tasks.size() - 1);
            throw e;
        }

        ui.showAdd(t, tasks.size());
    }

    /**
     * Adds a new {@link Deadline} task using the user input and saves the updated task list.
     *
     * <p>Expected input format: {@code deadline <desc> /by <yyyy-MM-dd>}</p>
     *
     * @param input Full user input.
     * @throws SuuException If the format/date is invalid or saving fails.
     */
    private void addDeadline(String input) throws SuuException {
        String[] parts = Parser.parseDeadline(input); // [desc, byText]

        LocalDate by;
        try {
            by = DateTimeUtil.parseDate(parts[1]);
        } catch (Exception e) {
            throw new SuuException("Invalid date. Use yyyy-MM-dd (e.g., 2019-10-15)");
        }

        Task t = new Deadline(parts[0], by);
        tasks.add(t);

        try {
            storage.save(tasks.asList());
        } catch (SuuException e) {
            tasks.remove(tasks.size() - 1);
            throw e;
        }

        ui.showAdd(t, tasks.size());
    }

    /**
     * Adds a new {@link Event} task using the user input and saves the updated task list.
     *
     * <p>Expected input format: {@code event <desc> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>}</p>
     *
     * @param input Full user input.
     * @throws SuuException If the format/date-time is invalid or saving fails.
     */
    private void addEvent(String input) throws SuuException {
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
        tasks.add(t);

        try {
            storage.save(tasks.asList());
        } catch (SuuException e) {
            tasks.remove(tasks.size() - 1);
            throw e;
        }

        ui.showAdd(t, tasks.size());
    }

    /**
     * Deletes the task specified by the user input and saves the updated task list.
     *
     * <p>If saving fails, the deletion is rolled back by inserting the task back into the list.</p>
     *
     * @param input Full user input (e.g. {@code "delete 2"}).
     * @throws SuuException If the task index is invalid or saving fails.
     */
    private void deleteTask(String input) throws SuuException {
        int index = Parser.parseTaskIndex(input, tasks.size(), "delete");

        Task removed = tasks.remove(index);

        try {
            storage.save(tasks.asList());
        } catch (SuuException e) {
            tasks.add(index, removed); // rollback
            throw e;
        }

        ui.showDelete(removed, tasks.size());
    }

    /**
     * Application entry point. Starts the chatbot and processes user commands until exit.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        new Suu().run();
    }
}
