import java.io.IOException;

public class ServerMain {
    private static class ASCII_COLOR {
        public static final String ANSI_RESET = "\u001B[0m";
        public static final String ANSI_RED = "\u001B[31m";
        public static final String ANSI_GREEN = "\u001B[32m";
        public static final String ANSI_YELLOW = "\u001B[33m";
        public static final String ANSI_CYAN = "\u001B[36m";
    }

    private static int port;
    private static String address;

    public static void main(String[] args) {
        if(args.length != 2) {
            System.out.println(ASCII_COLOR.ANSI_RED+
                    "Usage: java ServerMain"+
                    ASCII_COLOR.ANSI_YELLOW +
                    " <Address> <target port>" +
                    ASCII_COLOR.ANSI_RESET);
            System.exit(1); // Exit with error
        }

        // Set up the server
        try{
            address = args[0]; // Get address
            port = Integer.parseInt(args[1]); // Get port
        }catch (NumberFormatException n){
            System.out.println(ASCII_COLOR.ANSI_RED+"Chosen port is not a number!"+ASCII_COLOR.ANSI_RESET);
            System.exit(1); // Exit with error
        }

        // Create the server object
        ServerSender MTS = null;
        try{
             MTS = new ServerSender(address, port); // Create new server worker
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            System.exit(1); // Exit with error
        }

        // Start the server as a thread
        Thread ServerThread = new Thread(MTS); // Create new thread
        ServerThread.start(); // Start thread
        System.out.println(ASCII_COLOR.ANSI_GREEN+"Server started at "+ASCII_COLOR.ANSI_RESET + address + ":" + port);
        System.out.println(ASCII_COLOR.ANSI_CYAN+"Press ENTER to stop the server"+ASCII_COLOR.ANSI_RESET);

        // Main waits for user input to stop the server
        try{
            System.in.read(); // Get user input
            MTS.interrupt(); // Interrupt server worker
            ServerThread.join(3000);
        } catch (IOException | InterruptedException e) {
            ; //Ignore exception
        }
        System.out.println(ASCII_COLOR.ANSI_YELLOW+"Server stopped!"+ASCII_COLOR.ANSI_RESET);
        System.exit(0); // Exit without error
    }
}
