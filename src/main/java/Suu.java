import java.util.ArrayList;
import java.util.Scanner;

public class Suu {
    public static final String LINE = "_________________________________________";
    public static ArrayList<String> tasks = new ArrayList<>();

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
                for(int i = 0; i < tasks.size(); i++) {
                    System.out.println((i + 1) + ". " + tasks.get(i));
                }
                System.out.println(LINE);
                continue;
            }
            tasks.add(input);

            System.out.println(LINE);
            System.out.println("added: " + input);
            System.out.println(LINE);
        }

        sc.close();

    }
}
