import java.net.UnknownHostException;

public class Client {
    private static int port;
    private static String address;

    public static void main(String[] args) {
        ClientListener listener = null;
        if(args.length != 2) {
            System.out.println("Usage: java Client <address> <listening port> | port 8888 is reserved for Server");
            System.exit(1); // Exit with error
        }
        try{
            address = args[0]; // Get address
            port = Integer.parseInt(args[1]); // Get port
        }catch (NumberFormatException e){
            System.out.println("Port must be an integer");
        }

        try{
            listener = new ClientListener(address, port); // Create listener
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        listener.start(); // Start listener
    }
}
