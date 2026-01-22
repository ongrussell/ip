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

        while(true){
            input = sc.nextLine();

            if(input.equals("bye")){
                System.out.println(LINE);
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(LINE);
                break;
            }

            if(input.equals("list")){
                System.out.println(LINE);
                System.out.println("Here are the tasks in your list:");

                for(int i = 0; i < tasks.size(); i++) {
                    System.out.println((i + 1) + ". " + tasks.get(i));
                }
                System.out.println(LINE);
                continue;
            }

            if(input.startsWith("mark ")) {
                int index = Integer.parseInt(input.split(" ")[1]) - 1;
                System.out.println(LINE);
                if (index < 0 || index >= tasks.size()) {
                    System.out.println("Invalid task number!");
                } else {
                    tasks.get(index).setMarked();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(" " + tasks.get(index));
                }

                System.out.println(LINE);
                continue;
            }

            if(input.startsWith("unmark ")) {
                int index = Integer.parseInt(input.split(" ")[1]) - 1;
                System.out.println(LINE);
                if (index < 0 || index >= tasks.size()) {
                    System.out.println("Invalid task number!");
                }
                else {
                    tasks.get(index).unmark();
                    System.out.println("OK! I've marked this task as not done yet:");
                    System.out.println(" " + tasks.get(index));
                }
                System.out.println(LINE);
                continue;
            }
            if(input.startsWith("todo ")) {
                String description = input.substring(5).trim();
                Task task = new Todo(description);
                tasks.add(task);

                System.out.println(LINE);
                System.out.println("Got it. I've added this task:");
                System.out.println(" " + task);
                System.out.println("Now you have " + tasks.size() + " tasks in your list.");
                System.out.println(LINE);
            }

            if(input.startsWith("deadline ")) {
                String rest = input.substring(9).trim();
                String[] parts =  rest.split(" /by ", 2);
                System.out.println(LINE);

                if(parts.length < 2){
                    System.out.println("Invalid deadline format! Use: deadline <Task> /by <Time>");
                    System.out.println(LINE);
                    continue;
                }
                String description = parts[0].trim();
                String deadline = parts[1].trim();
                Task t = new Deadline(description, deadline);
                tasks.add(t);

                System.out.println("Got it. I've added this task:");
                System.out.println(" " + t);
                System.out.println("Now you have " + tasks.size() + " tasks in your list.");
                System.out.println(LINE);
                continue;
            }

            if(input.startsWith("event ")) {
                String rest = input.substring(6).trim();
                String[] firstSplit =  rest.split(" /from ", 2);
                System.out.println(LINE);

                if(firstSplit.length < 2){
                    System.out.println("Invalid event format! Use: event <Task> /from <Start> /to <End>");
                    System.out.println(LINE);
                    continue;
                }
                String description = firstSplit[0].trim();
                String[] secondSplit = firstSplit[1].split(" /to ", 2);

                if(secondSplit.length < 2){
                    System.out.println("Invalid event format! Use: event <Task> /from <Start> /to <End>");
                    System.out.println(LINE);
                    continue;
                }
                String start = secondSplit[0].trim();
                String end = secondSplit[1].trim();

                Task task = new Event(description, start, end);
                tasks.add(task);
                System.out.println("Got it. I've added this task:");
                System.out.println(" " + task);
                System.out.println("Now you have " + tasks.size() + " tasks in your list.");
                System.out.println(LINE);
                continue;
            }

//            tasks.add(new Task(input));
//
//            System.out.println(LINE);
//            System.out.println("added: " + input);
//            System.out.println(LINE);
        }

        sc.close();

    }
}
