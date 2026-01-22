import java.util.ArrayList;
import java.util.Scanner;

public class Suu {
    public static final String LINE = "_________________________________________";
    public static ArrayList<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {
        String name = "Suu";
        String input = "";
        Scanner sc = new Scanner(System.in);

        System.out.println(LINE);
        System.out.println("Hello! I'm " + name);
        System.out.println("What can I do  for you?");
        System.out.println(LINE);

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
        tasks.get(index).setMarked();

        System.out.println(LINE);
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + tasks.get(index));
        System.out.println(LINE);
    }

    public static void unmarkTask(String input) throws SuuException {
        int index = parseTaskNumber(input, "unmark");
        tasks.get(index).unmark();

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
        // "todo borrow book"
        String desc = input.replaceFirst("todo", "").trim();

        if (desc.isEmpty()) {
            throw new SuuException("The description of a todo cannot be empty.");
        }

        Task t = new Todo(desc);
        tasks.add(t);

        System.out.println(LINE);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + t);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        System.out.println(LINE);
    }

    public static void addDeadline(String input) throws SuuException {
        // "deadline return book /by Sunday"
        String rest = input.replaceFirst("deadline", "").trim();

        if (rest.isEmpty()) {
            throw new SuuException("The description of a deadline cannot be empty.");
        }

        String[] parts = rest.split(" /by ", 2);
        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new SuuException("Use this format: deadline <task> /by <time>");
        }

        String desc = parts[0].trim();
        String by = parts[1].trim();

        Task t = new Deadline(desc, by);
        tasks.add(t);

        System.out.println(LINE);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + t);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        System.out.println(LINE);
    }

    public static void addEvent(String input) throws SuuException {
        // "event meeting /from Mon 2pm /to 4pm"
        String rest = input.replaceFirst("event", "").trim();

        if (rest.isEmpty()) {
            throw new SuuException("The description of an event cannot be empty.");
        }

        String[] firstSplit = rest.split(" /from ", 2);
        if (firstSplit.length < 2 || firstSplit[0].trim().isEmpty()) {
            throw new SuuException("Use this format: event <task> /from <start> /to <end>");
        }

        String desc = firstSplit[0].trim();
        String[] secondSplit = firstSplit[1].split(" /to ", 2);

        if (secondSplit.length < 2 || secondSplit[0].trim().isEmpty() || secondSplit[1].trim().isEmpty()) {
            throw new SuuException("Use this format: event <task> /from <start> /to <end>");
        }

        String from = secondSplit[0].trim();
        String to = secondSplit[1].trim();

        Task t = new Event(desc, from, to);
        tasks.add(t);

        System.out.println(LINE);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + t);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        System.out.println(LINE);
    }

    public static void deleteTask(String input) throws SuuException {
        int index = parseTaskNumber(input, "delete");

        Task removed = tasks.remove(index);

        System.out.println(LINE);
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + removed);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        System.out.println(LINE);
    }
}
