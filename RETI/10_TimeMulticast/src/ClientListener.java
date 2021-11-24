import java.io.IOException;
import java.net.*;
import java.util.Calendar;

public class ClientListener extends Thread{
    private static class ASCII_COLOR {
        public static final String ANSI_RESET = "\u001B[0m";
        public static final String ANSI_RED = "\u001B[31m";
        public static final String ANSI_CYAN = "\u001B[36m";
        public static final String ANSI_GREEN = "\u001B[32m";
        public static final String ANSI_YELLOW = "\u001B[33m";
    }

    private int port;
    private final int len = 64;
    private MulticastSocket socket;
    private InetAddress group;

    public ClientListener(String group, int port) throws UnknownHostException, IllegalArgumentException {
        this.port = port;
        this.group = InetAddress.getByName(group);
        // Check if group is a multicast address
        if(!this.group.isMulticastAddress()) {
            throw new IllegalArgumentException(ASCII_COLOR.ANSI_RED +
                    group + " is not a multicast address."+
                    ASCII_COLOR.ANSI_RESET);
        }
    }

    @Override
    public void interrupt() {
        try {
            // Leave the multicast group
            InetSocketAddress group = new InetSocketAddress(this.group, this.port);
            socket.leaveGroup(group, NetworkInterface.getByInetAddress(InetAddress.getLocalHost()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        socket.close(); // Close multicast listener
        try {
            this.join(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("\n"+ASCII_COLOR.ANSI_GREEN+"Client closed"+ASCII_COLOR.ANSI_RESET);
    }

    public void run(){
        socket = null;
        try {
            socket = new MulticastSocket(port);
            socket.setTimeToLive(1); // Set TTL to 1
            // Get Socket address to join the multicast group
            InetSocketAddress group = new InetSocketAddress(this.group, this.port);
            // Get local network interface
            NetworkInterface netInt = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
            // Join the multicast group
            socket.joinGroup(group, netInt);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(ASCII_COLOR.ANSI_GREEN+"Client started"+ASCII_COLOR.ANSI_RESET);

        byte[] buffer = new byte[len]; // Create a buffer to store the message
        System.out.println(ASCII_COLOR.ANSI_CYAN+"Listening on port "+ ASCII_COLOR.ANSI_RESET + port);
        for (int i = 0; i < 10; i++) {
            DatagramPacket dat = new DatagramPacket(buffer, buffer.length);
            assert socket != null;
            try {
                socket.receive(dat);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            String timestamp = new String(dat.getData(), dat.getOffset(), dat.getLength());
            printd(timestamp);
        }
        this.interrupt(); // Close the multicast listener
    }

    private void printd(String timestamp) {
        Calendar c = Calendar.getInstance();
        try{
            long millis = Long.parseLong(timestamp);
            c.setTimeInMillis(millis);
            // Remove \n for an auto-updated timestamp
            System.out.print(ASCII_COLOR.ANSI_YELLOW); // Print timestamp in yellow
            System.out.printf("\r%d/%d/%d %d:%d:%2d\n",
                    c.get(Calendar.DAY_OF_MONTH),
                    c.get(Calendar.MONTH),
                    c.get(Calendar.YEAR),
                    c.get(Calendar.HOUR_OF_DAY),
                    c.get(Calendar.MINUTE),
                    c.get(Calendar.SECOND));
            System.out.print(ASCII_COLOR.ANSI_RESET); // Reset color
        }
        catch (NumberFormatException e){
            System.out.println(ASCII_COLOR.ANSI_RED+"Wrong timestamp format!"+ASCII_COLOR.ANSI_RESET);
        }
    }
}
