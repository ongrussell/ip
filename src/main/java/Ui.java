import java.util.Scanner;

public class Ui {
    public static final String LINE = "_________________________________________";
    private final Scanner sc = new Scanner(System.in);

    public void showWelcome(String name) {
        System.out.println(LINE);
        System.out.println("Hello! I'm " + name);
        System.out.println("What can I do  for you?");
        System.out.println(LINE);
    }

    public String readCommand() {
        return sc.nextLine().trim();
    }

    public void showBye() {
        System.out.println(LINE);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(LINE);
    }

    public void showError(String message) {
        System.out.println(LINE);
        System.out.println("Oops! " + message);
        System.out.println(LINE);
    }

    public void showList(TaskList tasks) {
        System.out.println(LINE);
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
        System.out.println(LINE);
    }

    public void showMark(Task task) {
        System.out.println(LINE);
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
        System.out.println(LINE);
    }

    public void showUnmark(Task task) {
        System.out.println(LINE);
        System.out.println("OK! I've marked this task as not done yet:");
        System.out.println("  " + task);
        System.out.println(LINE);
    }

    public void showAdd(Task task, int newSize) {
        System.out.println(LINE);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + newSize + " tasks in the list.");
        System.out.println(LINE);
    }

    public void showDelete(Task task, int newSize) {
        System.out.println(LINE);
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + newSize + " tasks in the list.");
        System.out.println(LINE);
    }

    public void close() {
        sc.close();
    }
}