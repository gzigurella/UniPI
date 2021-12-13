import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/**
 * Il client può richiedere operazioni per:
 *
 * - registrare uno speaker a una sessione;
 * - ottenere il programma del congresso;
 *
 * Il client inoltra le richieste al server tramite il meccanismo di RMI. Prevedere, per ogni possibile operazione
 * una gestione di eventuali condizioni anomale (ad esempio la richiesta di registrazione a una giornata
 * e/o sessione inesistente oppure per la quale sono già stati coperti tutti gli spazi d’intervento)
 *
 * Il client è implementato come un processo ciclico che continua a fare richieste sincrone
 * fino a esaurire tutte le esigenze utente.
 * Stabilire una opportuna condizione di terminazione del processo di richiesta.
 */
public class Client {
    public static class ANSI{
        public static final String ANSI_RESET = "\u001B[0m";
        public static final String ANSI_YELLOW = "\u001B[33m";
        public static final String ANSI_GREEN = "\u001B[32m";
        public static final String ANSI_RED = "\u001B[31m";
    }

    public static String msg = """
            [-] Please choose an operation:
            [1] Add a new speaker
            [2] (DEBUG) Print the schedule on the server
            [3] Exit
            """;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Open scanner for future use
        Congress serverObj; // the remote server object
        Remote remoteObj; // remote object reference
        if(args.length != 2) {
            System.out.println(ANSI.ANSI_RED + "[-] Usage: java Client <host> <port>"+ ANSI.ANSI_RESET);
            System.exit(1); // Exit with error
        }
        try{
            System.out.println(ANSI.ANSI_YELLOW + "[+] Connecting to server..." + ANSI.ANSI_RESET);
            Registry registry = LocateRegistry.getRegistry(args[0], Integer.parseInt(args[1])); // get the registry
            remoteObj = registry.lookup("CONGRESS-SERVER"); // get the remote object
            System.out.println(ANSI.ANSI_GREEN + "[+] Server found: CONGRESS-SERVER" + ANSI.ANSI_RESET);
            serverObj = (Congress) remoteObj; // cast the remote object to the server interface
            System.out.println(ANSI.ANSI_GREEN + "[+] Connected to server" + ANSI.ANSI_RESET);

            switchOp(scanner, serverObj); // Get the operation to perform

        }catch (RemoteException | NotBoundException e){
            System.out.println(ANSI.ANSI_RED + "[-] Communication error: " + e.getMessage() + ANSI.ANSI_RESET);
            scanner.close(); // close the scanner
            System.exit(-1); // Exit with error
        }
        scanner.close(); // Close scanner
        System.exit(0); // Exit without error
    }

    public static boolean enroll(Congress cs, String name, int day, int slot){
        try{
            System.out.println(ANSI.ANSI_YELLOW+"[-] Enrolling speaker " + name + " to day " + day + " at slot " + slot+
                    ANSI.ANSI_RESET);
            cs.enrollSpeaker(name, day, slot);
        }catch (RemoteException e){
            System.out.println(ANSI.ANSI_RED + "[-] Communication error: " + e.getMessage() + ANSI.ANSI_RESET);
            return false;
        }
        return true;
    }

    public static boolean printSchedule(Congress cs){
        try{
            System.out.println(ANSI.ANSI_YELLOW+"[-] Printing schedule"+ANSI.ANSI_RESET);
            cs.printSchedule();
            System.out.println(ANSI.ANSI_GREEN+"[+] Schedule printed"+ANSI.ANSI_RESET);
        }catch (RemoteException e){
            System.out.println(ANSI.ANSI_RED + "[-] Communication error: " + e.getMessage() + ANSI.ANSI_RESET);
            return false;
        }
        return true;
    }

    public static void switchOp(Scanner input, Congress cs){
        while(true){
            System.out.println(ANSI.ANSI_YELLOW+"\n"+msg+ANSI.ANSI_RESET);
            // Get user input
            String op = input.nextLine();
            if(op.equals("3")){
                break; // Exit
            }
            // Check if input is valid
            if(!op.matches("[1-3]")){
                System.out.println(CongressMain.ANSI.ANSI_RED + "[-] Invalid input!" + CongressMain.ANSI.ANSI_RESET);
            }
            else{
                // Switch on input
                switch (op) {
                    case "1" -> {
                        try{
                            System.out.println("[-] Please enter the name of the speaker:");
                            String name = input.nextLine();
                            System.out.println("[-] Please enter the (numeric) day of the speaker:");
                            // get the integer value of the day
                            int day = Integer.parseInt(input.nextLine());
                            System.out.println("[-] Please enter the (numeric) slot of the speaker:");
                            // get the integer value of the slot
                            int slot = Integer.parseInt(input.nextLine());
                            // Call the remote method
                            if(!enroll(cs, name, day, slot)){
                                System.out.println(CongressMain.ANSI.ANSI_RED + "[-] Enrollment failed!" + CongressMain.ANSI.ANSI_RESET);
                                System.out.println(ANSI.ANSI_YELLOW + "[+] Exiting..." + ANSI.ANSI_RESET);
                                System.exit(-1); // Exit with error
                            }
                            System.out.println(ANSI.ANSI_GREEN+"[+] Enrollment successful of speaker " + name + " to day " + day +
                                    " at slot " + slot + ANSI.ANSI_RESET);
                        }catch(NumberFormatException e){
                            System.out.println(CongressMain.ANSI.ANSI_RED + "[-] Invalid input!" + CongressMain.ANSI.ANSI_RESET);
                        }
                    }
                    case "2" -> {
                        if(!printSchedule(cs)){
                            System.out.println(CongressMain.ANSI.ANSI_RED + "[-] Error printing schedule!" + CongressMain.ANSI.ANSI_RESET);
                            System.out.println(ANSI.ANSI_YELLOW + "[+] Exiting..." + ANSI.ANSI_RESET);
                            System.exit(-1); // Exit with error
                        }
                    }
                }
            }
        }
        System.out.println(ANSI.ANSI_GREEN + "[+] Exiting..." + ANSI.ANSI_RESET);
    }
}
