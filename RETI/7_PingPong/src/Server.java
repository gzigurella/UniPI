import java.io.*;
import java.net.*;
import java.util.Random;

/*
 * Server to process ping requests over UDP.
 */
public class Server {
    public static class ANSI_COLORS{
        public static final String RESET = "\u001B[0m";
        public static final String CYAN = "\u001B[36m";
        public static final String GREEN = "\u001B[32m";
        public static final String RED = "\u001B[31m";
    }

    private static final double LOSS_RATE = 0.25;    // 30% chance of data loss
    private static final int AVERAGE_DELAY = 300;   // milliseconds
    public static DatagramSocket socket = null;     // UDP socket

    public static void main(String[] args) {
        // Get command line argument.
        if (args.length != 1) {
            System.out.println(ANSI_COLORS.RED+"Usage: ./program port"+ANSI_COLORS.RESET);
            return;
        }
        int port = Integer.parseInt(args[0]);

        // Create a datagram socket for receiving and sending UDP packets
        // through the port specified on the command line.
        try{
            socket = new DatagramSocket(port);
        }catch(SocketException S){
            System.out.println("Unable to start UDP server on port: "+port);
            System.exit(1);
        }

        System.out.println("UDP server started on Port: "+ port);
        int i = 0;

        // Main program loop
        while (true) {
            // Create a datagram packet to hold incoming UDP packet.
            DatagramPacket request = new DatagramPacket(new byte[1024], 1024);

            // Block until the host receives a UDP packet.
            try{
                socket.receive(request);
            }catch(IOException I){
                System.out.println("Unable to receive the Datagram through .");
                System.exit(1);
            }

            // Print the received data on screen.
            try{
                printReq(request);
            }catch(IOException e){
                i++;
                System.out.println("Error while reading received data. Datagram n. "+ i);
                break; //break if we cannot read the message
            }

            // Decide whether to reply, or simulate packet loss.
            if(simulateLoss(i++)) continue; //if data is loss then continue to next iteration
            // Simulate network delay.
            simulateDelay();

            // Send reply.
            Req R = new Req(request); //get information of the client that sent the ping
            DatagramPacket reply = new DatagramPacket(R.getMessage(), R.getMessage().length,
                    R.getHost(), R.getPort()); //create the reply's datagram
            try{
                socket.send(reply);
            }catch(IOException I){
                System.out.println("Unable to send datagram through UDP.");
            }


            System.out.println("Reply sent.");
        }

        socket.close();
        System.exit(0);
    }

    /**
     * Instructs the program to simulate the loss of data
     *
     * @param index is the index of the data the server tries to send back
     * @return true if we occurred in the chance of loss, else returns false
     */
    private static boolean simulateLoss(int index){
        Random rand = new Random();
        if(rand.nextDouble() < Server.LOSS_RATE){
            System.out.println("Pong n." + index + " not sent.");
            return true;
        }
        return false;
    }

    /**
     * Instructs the program to simulate the delay between sent packages
     */
    private static void simulateDelay(){
        Random rand = new Random();
        try{
            Thread.sleep((rand.nextInt(10-1)+1) * Server.AVERAGE_DELAY);
        } catch (InterruptedException e) {
            System.out.println("Program interrupted during delay simulation.");
        }
    }

    private static class Req{
        private final InetAddress host;
        private final int port;
        private final byte[] message;

        /**
         * Constructor method, gets information about the requester
         * @param DP is the datagram we used to get information about the requester
         */
        public Req(DatagramPacket DP){
            this.host = DP.getAddress();
            this.port = DP.getPort();
            this.message = DP.getData();
        }

        public int getPort() {
            return port;
        }

        public InetAddress getHost() {
            return host;
        }

        public byte[] getMessage() {
            return message;
        }
    }

    /*
     * Print ping data to the standard output stream.
     */
    private static void printReq(DatagramPacket request) throws IOException {
        // Obtain references to the packet's array of bytes.
        byte[] buf = request.getData();

        // Wrap the bytes in a byte array input stream,
        // so that you can read the data as a stream of bytes.

        // Wrap the byte array output stream in an input stream reader,
        // so you can read the data as a stream of characters.

        // Wrap the input stream reader in a buffered reader,
        // so you can read the character data a line at a time.
        // (A line is a sequence of chars terminated by any combination of \r and \n.)
        BufferedReader msgBuff = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(buf)));

        // The message data is contained in a single line, so read this line.
        String line = msgBuff.readLine();

        // Print host address and data received from it.
        System.out.println(
               "Received from " + ANSI_COLORS.CYAN +
               request.getAddress().getHostAddress() + request.getPort() + ANSI_COLORS.RESET +
               ": " + ANSI_COLORS.GREEN + line + ANSI_COLORS.RESET
        );
    }
}