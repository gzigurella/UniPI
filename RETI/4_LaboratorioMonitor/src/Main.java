import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int nProf = 5;
        int nTesisti = 5;
        int nStudenti = 10;
        try (Scanner in = new Scanner(System.in).useLocale(Locale.getDefault())) {
            System.out.println("Quanti professori?");
            nProf = in.nextInt();
            System.out.println("Quanti tesisti?");
            nTesisti = in.nextInt();
            System.out.println("Quanti studenti?");
            nStudenti = in.nextInt();
        } catch (InputMismatchException E) {
            System.out.println(E.getMessage());
        }

        Tutor tutor = new Tutor(nStudenti, nProf, nTesisti);
        tutor.openLab();
        tutor.closeLab();
    }
}
