import java.util.Scanner;

public class Suu {
    public static final String LINE = "_________________________________________";

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

            System.out.println(LINE);
            System.out.println(input);
            System.out.println(LINE);
        }

        sc.close();

    }
}
