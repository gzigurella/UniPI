//define package 
import PiCalc.*;

//import scanner from java.util
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {
        double accuracy;
        long timeout;
        try {
            Scanner input = new Scanner(System.in).useLocale(Locale.getDefault()); // scanner to take input
            System.out.print("Enter the accuracy: ");
            accuracy = input.nextDouble();
            System.out.print("Enter the number of seconds to wait before interrupting: ");
            timeout = input.nextInt() * 1000;
            input.close();
        } catch (InputMismatchException e) {
            System.out.println("Error: " + e);
            return;
        }
            
        PiCalc.PiCalculator piCalc = new PiCalc.PiCalculator(accuracy, 0);
        Thread t = new Thread(piCalc);

        try {
            t.start();
            System.out.println("\nCalculating");
            t.join(timeout);    //check for returned thread
            if(t.isAlive()){    // if thread is still alive 
                System.out.println("Now interrupting alive thread . . .");
                t.interrupt(); // interrupt it due to timeout
            }
        } catch (InterruptedException e) {
            System.out.println("Error: " + e);
        }
        System.out.println(piCalc.getPi());
        System.out.println("\nCalculation completed!\n");
    }
}
