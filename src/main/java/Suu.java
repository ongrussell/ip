import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Suu {
    public static final String LINE = "_________________________________________";
    public static ArrayList<Task> tasks = new ArrayList<>();
    public static final Storage storage = new Storage("data", "Suu.txt");

    public static void main(String[] args) {
        String name = "Suu";
        String input = "";
        Scanner sc = new Scanner(System.in);

        System.out.println(LINE);
        System.out.println("Hello! I'm " + name);
        System.out.println("What can I do  for you?");
        System.out.println(LINE);

        try {
            tasks = storage.load();
        } catch (SuuException e) {

            tasks = new ArrayList<>();
            System.out.println(LINE);
            System.out.println("Oops! " + e.getMessage());
            System.out.println("Starting with an empty task list.");
            System.out.println(LINE);
        }

        while (true) {
            input = sc.nextLine().trim();

            try {
                CommandType command = CommandType.from(input);

                switch (command) {
                    case BYE:
                        System.out.println(LINE);
                        System.out.println("Bye. Hope to see you again soon!");
                        System.out.println(LINE);
                        sc.close();
                        return;

                    case LIST:
                        printList();
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
                System.out.println(LINE);
                System.out.println("Oops! " + e.getMessage());
                System.out.println(LINE);
            }
        }

    }

    //Helper methods
    public static void printList() {
        System.out.println(LINE);
        System.out.println("Here are the tasks in your list:");

        for(int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
        System.out.println(LINE);
    }

    public static void markTask(String input) throws SuuException {
        int index = parseTaskNumber(input, "mark");

        Task t = tasks.get(index);
        boolean wasMarked = t.isMarked();
        t.setMarked();

        try {
            storage.save(tasks);
        } catch (SuuException e) {
            if (!wasMarked) {
                t.unmark();
            }
            throw e;
        }
        System.out.println(LINE);
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + tasks.get(index));
        System.out.println(LINE);
    }

    public static void unmarkTask(String input) throws SuuException {
        int index = parseTaskNumber(input, "unmark");

        Task t = tasks.get(index);
        boolean wasMarked = t.isMarked();

        t.unmark();
        try {
            storage.save(tasks);
        } catch (SuuException e) {
            if (wasMarked) {
                t.setMarked();
            }
            throw e;
        }

        System.out.println(LINE);
        System.out.println("OK! I've marked this task as not done yet:");
        System.out.println("  " + tasks.get(index));
        System.out.println(LINE);
    }

    public static int parseTaskNumber(String input, String commandWord) throws SuuException {
        String[] parts = input.split(" ");

        if (parts.length < 2) {
            throw new SuuException("Please provide a task number. Example: " + commandWord + " 2");
        }

        int taskNum;
        try {
            taskNum = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            throw new SuuException("Task number must be a number. Example: " + commandWord + " 2");
        }

        int index = taskNum - 1;

        if (index < 0 || index >= tasks.size()) {
            throw new SuuException("That task number does not exist.");
        }

        return index;
    }

    public static void addTodo(String input) throws SuuException {
        String desc = input.replaceFirst("todo", "").trim();
        if (desc.isEmpty()) {
            throw new SuuException("The description of a todo cannot be empty.");
        }

        Task t = new Todo(desc);
        tasks.add(t);

        try {
            storage.save(tasks);
        } catch (SuuException e) {
            tasks.remove(tasks.size() - 1);
            throw e;
        }
        System.out.println(LINE);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + t);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        System.out.println(LINE);
    }

    public static void addDeadline(String input) throws SuuException {
        String parts = input.replaceFirst("deadline", "").trim();

        String[] split = parts.split("/by", 2);
        if (split.length < 2) {
            throw new SuuException("Use: deadline <task> /by yyyy-MM-dd (e.g., 2019-10-15)");
        }

        String desc = split[0].trim();
        String byText = split[1].trim();

        if (desc.isEmpty()) {
            throw new SuuException("The description of a deadline cannot be empty.");
        }
        if (byText.isEmpty()) {
            throw new SuuException("The /by date cannot be empty.");
        }

        LocalDate by;
        try {
            by = DateTimeUtil.parseDate(byText);
        } catch (Exception e) {
            throw new SuuException("Invalid date. Use yyyy-MM-dd (e.g., 2019-10-15)");
        }

        Task t = new Deadline(desc, by);
        tasks.add(t);

        // your Level-7 autosave
        try {
            storage.save(tasks);
        } catch (SuuException e) {
            tasks.remove(tasks.size() - 1);
            throw e;
        }

        System.out.println(LINE);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + t);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        System.out.println(LINE);
    }

    public static void addEvent(String input) throws SuuException {
        String parts = input.replaceFirst("event", "").trim();

        String[] splitDesc = parts.split("/from", 2);
        if (splitDesc.length < 2) {
            throw new SuuException("Use: event <task> /from yyyy-MM-dd HHmm /to yyyy-MM-dd HHmm");
        }

        String desc = splitDesc[0].trim();
        if (desc.isEmpty()) {
            throw new SuuException("The description of an event cannot be empty.");
        }

        String[] splitFromTo = splitDesc[1].split("/to", 2);
        if (splitFromTo.length < 2) {
            throw new SuuException("Use: event <task> /from yyyy-MM-dd HHmm /to yyyy-MM-dd HHmm");
        }

        String fromText = splitFromTo[0].trim();
        String toText = splitFromTo[1].trim();

        if (fromText.isEmpty() || toText.isEmpty()) {
            throw new SuuException("Both /from and /to must be provided.");
        }

        LocalDateTime from;
        LocalDateTime to;
        try {
            from = DateTimeUtil.parseDateTime(fromText);
            to = DateTimeUtil.parseDateTime(toText);
        } catch (Exception e) {
            throw new SuuException("Invalid date/time. Use yyyy-MM-dd HHmm (e.g., 2019-12-02 1800)");
        }

        Task t = new Event(desc, from, to);
        tasks.add(t);

        // your Level-7 autosave
        try {
            storage.save(tasks);
        } catch (SuuException e) {
            tasks.remove(tasks.size() - 1);
            throw e;
        }

        System.out.println(LINE);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + t);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        System.out.println(LINE);
    }

    public static void deleteTask(String input) throws SuuException {
        int index = parseTaskNumber(input, "delete");

        Task removed = tasks.remove(index);

        try {
            storage.save(tasks);
        } catch (SuuException e) {
            tasks.add(index, removed);
            throw e;
        }
        System.out.println(LINE);
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + removed);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        System.out.println(LINE);
    }
}
