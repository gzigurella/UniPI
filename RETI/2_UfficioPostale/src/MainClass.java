import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {
        int people = 0; int k = 0;
        try{
            Scanner scanner = new Scanner(System.in).useLocale(Locale.getDefault());
            System.out.println("Insert the number of people:");
            people = scanner.nextInt();
            System.out.println("How many people can stay in the second room?");
            k = scanner.nextInt();
            scanner.close();
        }catch(InputMismatchException e){
            System.out.println(e.getMessage());
        }

        Office O = new Office(k);
        try{
            O.Entrance(people);
        }catch(InterruptedException e){
            System.out.println("The first room is on fire! Now exiting all the civilians");
            O.closeOffice();
        }
    }
}