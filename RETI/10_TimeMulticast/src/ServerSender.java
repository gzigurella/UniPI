import java.io.IOException;
import java.net.*;
import java.util.concurrent.ThreadLocalRandom;

public class ServerSender extends Thread{
    private InetAddress groupAddress;
    private static final int SERVER_PORT = 8888;
    private final int port;
    private DatagramSocket socket = null;


    public ServerSender(String groupAddress, int port) {
       this.port = port;
       try{
           this.socket = new DatagramSocket(SERVER_PORT);
           this.groupAddress = InetAddress.getByName(groupAddress);
       }catch (SocketException | UnknownHostException e){
           System.out.println(e.getMessage());
           System.exit(1); // Exit the program with error
       }
       if(!this.groupAddress.isMulticastAddress()){
           throw new IllegalArgumentException(groupAddress + " is not a multicast address.");
       }
    }

    @Override
    public void interrupt(){
        Thread.currentThread().interrupt(); // Call interrupt() method
        if(socket != null){
            socket.close(); // Close the socket if it is open
        }
    }

    public void run(){
        try{
            while(!Thread.currentThread().isInterrupted()){
                // Get current time as string
                String time = String.valueOf(System.currentTimeMillis());
                // Create a packet with the time string
                DatagramPacket packet = new DatagramPacket(
                        time.getBytes(), time.length(),
                        groupAddress, port);
                // Send the packet
                socket.send(packet);
                // Sleep for 5 seconds, simulate work
                Thread.sleep(5*1000L);
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Server is closing ...");
        }
    }
}
