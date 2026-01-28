import java.time.LocalDate;
import java.time.LocalDateTime;

public class Suu {
    private static final String NAME = "Suu";

    private final Ui ui;
    private final Storage storage;
    private final TaskList tasks;

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

                    default:
                        throw new SuuException("I don't know what that means? :o");
                }

            } catch (SuuException e) {
                ui.showError(e.getMessage());
            }
        }
    }

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

    public static void main(String[] args) {
        new Suu().run();
    }
}