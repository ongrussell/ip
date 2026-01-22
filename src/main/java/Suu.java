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
                tasks.get(index).setMarked();
                System.out.println(LINE);
                System.out.println("Nice! I've marked this task as done:");
                System.out.println(" " + tasks.get(index));
                System.out.println(LINE);
                continue;
            }

            if(input.startsWith("unmark ")) {
                int index = Integer.parseInt(input.split(" ")[1]) - 1;
                tasks.get(index).unmark();
                System.out.println(LINE);
                System.out.println("OK! I've marked this task as not done yet:");
                System.out.println(" " + tasks.get(index));
                System.out.println(LINE);
                continue;
            }
            tasks.add(new Task(input));

            System.out.println(LINE);
            System.out.println("added: " + input);
            System.out.println(LINE);
        }

        sc.close();

    }
}
