package duke;

import java.util.Scanner;

/**
 * Handles all interactions with the user via standard input and output.
 * This class is responsible for printing messages and reading commands.
 */
public class Ui {
    /**
     * Divider line used to separate sections in the UI output.
     */
    public static final String LINE = "_________________________________________";

    private final Scanner sc = new Scanner(System.in);

    /**
     * Prints the welcome message shown when the chatbot starts.
     *
     * @param name Name of the chatbot shown to the user.
     */
    public void showWelcome(String name) {
        System.out.println(LINE);
        System.out.println("Hello! I'm " + name);
        System.out.println("What can I do  for you?");
        System.out.println(LINE);
    }

    /**
     * Reads the next command entered by the user.
     *
     * @return Trimmed user input command.
     */
    public String readCommand() {
        return sc.nextLine().trim();
    }

    /**
     * Prints the goodbye message shown when the chatbot exits.
     */
    public void showBye() {
        System.out.println(LINE);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(LINE);
    }

    /**
     * Prints an error message to the user.
     *
     * @param message Error description to display.
     */
    public void showError(String message) {
        System.out.println(LINE);
        System.out.println("Oops! " + message);
        System.out.println(LINE);
    }

    /**
     * Displays all tasks currently in the task list.
     *
     * @param tasks Task list to display.
     */
    public void showList(TaskList tasks) {
        System.out.println(LINE);
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
        System.out.println(LINE);
    }

    /**
     * Displays confirmation that a task has been marked as done.
     *
     * @param task The task that was marked.
     */
    public void showMark(Task task) {
        System.out.println(LINE);
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
        System.out.println(LINE);
    }

    /**
     * Displays confirmation that a task has been unmarked (set as not done).
     *
     * @param task The task that was unmarked.
     */
    public void showUnmark(Task task) {
        System.out.println(LINE);
        System.out.println("OK! I've marked this task as not done yet:");
        System.out.println("  " + task);
        System.out.println(LINE);
    }

    /**
     * Displays confirmation that a task has been added.
     *
     * @param task The task that was added.
     * @param newSize The updated number of tasks in the list.
     */
    public void showAdd(Task task, int newSize) {
        System.out.println(LINE);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + newSize + " tasks in the list.");
        System.out.println(LINE);
    }

    /**
     * Displays confirmation that a task has been deleted.
     *
     * @param task The task that was deleted.
     * @param newSize The updated number of tasks in the list.
     */
    public void showDelete(Task task, int newSize) {
        System.out.println(LINE);
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + newSize + " tasks in the list.");
        System.out.println(LINE);
    }

    public void showFindResults(String keyword, java.util.ArrayList<Task> matches) {
        System.out.println(LINE);

        if (matches.isEmpty()) {
            System.out.println("No matching tasks found for: " + keyword);
        } else {
            System.out.println("Here are the matching tasks in your list:");
            for (int i = 0; i < matches.size(); i++) {
                System.out.println((i + 1) + ". " + matches.get(i));
            }
        }

        System.out.println(LINE);
    }

    /**
     * Releases resources held by the UI (closes the input scanner).
     */
    public void close() {
        sc.close();
    }
}
