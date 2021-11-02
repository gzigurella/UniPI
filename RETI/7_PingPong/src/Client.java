import java.io.*;
import java.net.*;
import java.util.*;

/*
 * Client to generate a ping requests over UDP.
 * Code has started in the PingServer.java
 */

public class Client {
    public static class ANSI_COLORS{
        public static final String RESET = "\u001B[0m";
        public static final String CYAN = "\u001B[36m";
        public static final String GREEN = "\u001B[32m";
        public static final String RED = "\u001B[31m";
    }

    private static final int MAX_TIMEOUT = 2 * 1000;	// milliseconds
    public static DatagramSocket socket = null;     // UDP socket
    public static InetAddress server = null;        // Server address

    public static void main(String[] args) {
        // Get command line arguments.
        if (args.length != 2) {
            System.out.println(ANSI_COLORS.RED+"Usage: ./program serverName port"+ANSI_COLORS.RESET);
            return;
        }
        // Port number to access
        int port = Integer.parseInt(args[1]);

        try{
            // Server to Ping (must run before client)
            server = InetAddress.getByName(args[0]);

            // Create a datagram socket for sending and receiving UDP packets
            // through the port specified on the command line.
            socket = new DatagramSocket(4444);
        }catch(UnknownHostException | SocketException H){
            if(H instanceof UnknownHostException) System.out.println("ERR -arg 1 | Unknown host name "+ args[0]);
            else System.out.println("ERR -arg 2 | Unable to start UDP socket.");
            System.exit(1);
        }


        int sequence_number = 0;
        int failures = 0; //used to count the number of replies
        long min, max;
        ArrayList<Long> time = new ArrayList<>();
        // Processing loop.
        while (sequence_number < 10) {
            // Timestamp in ms when we send it
            Date now = new Date();
            long send_time = now.getTime();
            byte[] message = msgBuilder(sequence_number, send_time);
            // Create a datagram packet to send as a UDP packet.
            DatagramPacket ping = new DatagramPacket(message, message.length, server, port);

            // Send the Ping datagram to the specified server
            try{
                socket.send(ping);
            }catch(IOException ex){
                System.out.println("Unable to write request to Server");
                System.exit(1);
            }

            // Try to receive the packet - but it can fail (timeout)
            try {
                // Set up the timeout 1000 ms = 1 sec
                socket.setSoTimeout(MAX_TIMEOUT);
                // Set up a UPD packet for receiving
                DatagramPacket response = new DatagramPacket(new byte[1024], 1024);
                // Try to receive the response from the ping
                socket.receive(response);
                // If the response is received, the code will continue here, otherwise it will continue in the catch

                // timestamp for when we received the packet
                now = new Date();
                long receive_time = now.getTime();
                time.add((receive_time - send_time));
                // Print the packet and the delay
                printData(response, receive_time - send_time);
            } catch (IOException e) {
                // Print which packet has timed out
                System.out.println(" Timeout from "+ ANSI_COLORS.CYAN +
                        server.getHostAddress() + ANSI_COLORS.RESET +
                        ": " + ANSI_COLORS.RED +
                        "PING "+sequence_number+" -------------" +
                        ANSI_COLORS.RESET + "\t*");
                failures++;
            }
            // next packet
            sequence_number++;
        }
        Collections.sort(time); //sort array containing the delay
        min = time.get(1);  //get the first non-zero element
        max = time.get(time.size() - 1); //get the greatest element
        long sum = 0;
        for (Long aLong : time) {
            sum += aLong;
        }
        float avg = (float)(sum/time.size());
        System.out.printf(ANSI_COLORS.CYAN+"""
                                            
                        ---- PING Statistics ----
                        """
                        + ANSI_COLORS.RESET +
                        """
                        %d packets transmitted, %d packets received, %d%% packets loss
                        round-trip (ms) min/avg/max = %d/%.2f/%d
                        """,
                sequence_number,(sequence_number - failures), (failures * 10), min, avg, max);
    }

    private static byte[] msgBuilder(int sequence_number, long time){
        // Create string to send, and transfer i to a Byte Array
        String str = "PING " + sequence_number + " " + time + " \n";
        return str.getBytes();
    }

    /*
     * Print ping data to the standard output stream.
     * slightly different from Server
     */
    private static void printData(DatagramPacket request, long delayTime) throws IOException
    {
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
                        ": " + ANSI_COLORS.GREEN +
                        line + ANSI_COLORS.RESET +" RTT: " + delayTime );
    }
}