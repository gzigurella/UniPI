import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class CongressMain {
    public static class ANSI {
        public static final String ANSI_RESET = "\u001B[0m";
        public static final String ANSI_YELLOW = "\u001B[33m";
        public static final String ANSI_GREEN = "\u001B[32m";
        public static final String ANSI_RED = "\u001B[31m";
    }

    private static final int DAYS = 3;
    private static final int SESSIONS = 12;
    private static final int MAX_SPEAKERS = 5;
    private static final int PORT = 8080;

    public static void main(String[] args) {
        try {
            System.out.println(ANSI.ANSI_YELLOW + "[-] Starting server..." + ANSI.ANSI_RESET);
            CongressServer congressServer = new CongressServer(DAYS, SESSIONS, MAX_SPEAKERS);
            Congress congressStub = (Congress) UnicastRemoteObject.exportObject(congressServer, PORT);

            LocateRegistry.createRegistry(PORT);
            Registry registry = LocateRegistry.getRegistry("localhost", PORT);
            System.out.println(ANSI.ANSI_GREEN + "[+] Remote server registered successfully!" + ANSI.ANSI_RESET);

            registry.rebind("CONGRESS-SERVER", congressStub);
            System.out.println(ANSI.ANSI_GREEN + "[+] Server started!\r" + ANSI.ANSI_RESET +
                    "\tConnect to server with: localhost:" + PORT + "/CONGRESS-SERVER");

        }catch(RemoteException e){
            System.out.println(ANSI.ANSI_RED + "[-] RMI communication error: " + e.getMessage() + ANSI.ANSI_RESET);
            System.exit(-1); // Exit with error
        }
    }
}