import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * Make a software, an echo client using NIO as non-blocking I/O.
 * We send a request to the client and wait for the response.
 */
public class EchoClient {
    private static class ASCII_COLORS{
        public static final String ANSI_RESET = "\u001B[0m";
        public static final String ANSI_GREEN = "\u001B[32m";
        public static final String ANSI_CYAN = "\u001B[36m";
    }

    private static BufferedReader input;
    private static int DEFAULT_PORT = 8888;

    public static void main(String[] args) {
        int port = DEFAULT_PORT;
        if (args.length > 0) {
            try{
                port = Integer.parseInt(args[0]);
            }catch (NumberFormatException n){
                System.out.println("Invalid port number: " + args[0]);
            }
        }
        InetSocketAddress address = null; Selector selector = null;
        try{
            InetAddress host = InetAddress.getByName("localhost");
            address = new InetSocketAddress(host, port);
            System.out.println(ASCII_COLORS.ANSI_CYAN +"[Client] "+ ASCII_COLORS.ANSI_RESET + "Connecting to " + ASCII_COLORS.ANSI_GREEN + address.getHostName() + ":"
                    + address.getPort() + ASCII_COLORS.ANSI_RESET);
        } catch (UnknownHostException e) {
            System.out.println("Unknown host: " + e.getMessage());
        }

        try{
             selector = Selector.open();
        } catch (IOException e) {
            System.out.println("Error opening selector: " + e.getMessage());
        }
        clientConfig(address, selector);
        System.out.println(ASCII_COLORS.ANSI_CYAN +"[Client] "+ ASCII_COLORS.ANSI_RESET +"Closing connection, Goodbye");
    }

    public static void clientConfig(InetSocketAddress address, Selector selector) {
        try(SocketChannel socketChannel = SocketChannel.open()) {
            socketChannel.configureBlocking(false); // non-blocking mode
            socketChannel.connect(address); // connect to server

            // Setup channel for operations
            socketChannel.register(selector, SelectionKey.OP_CONNECT |
                    SelectionKey.OP_READ | SelectionKey.OP_WRITE);

            // Get input from user
            input = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                // Wait for an event
                if(selector.select() > 0) {
                    boolean done = processSelect(selector.selectedKeys());
                    if (done) {
                        break;
                    }
                }
            }
        }catch (IOException e){
            System.out.println(ASCII_COLORS.ANSI_CYAN+"[Client] "+ASCII_COLORS.ANSI_RESET+"Error opening socket channel: " + e.getMessage());
        }
    }

    public static boolean processSelect(Set<SelectionKey> selectedKeys) throws IOException {
        SelectionKey key = null;
        Iterator<SelectionKey> iterator = null;
        iterator = selectedKeys.iterator();
        while (iterator.hasNext()) {
            key = iterator.next(); // get key
            iterator.remove(); // Remove the key from the ready key set

            if (key.isConnectable()) {
                boolean connected = processConnect(key);
                if (!connected) {
                    return true; // Exit
                }
            }
            if (key.isReadable()) {
                String msg = "";
                try{
                     msg = processRead(key);
                } catch (CharacterCodingException e) {
                    System.out.println(ASCII_COLORS.ANSI_CYAN+"[Client] "+ASCII_COLORS.ANSI_RESET+"Error decoding message: " + e.getMessage());
                }
                System.out.println(ASCII_COLORS.ANSI_GREEN+"[Server] "+ ASCII_COLORS.ANSI_RESET + msg);
            }

            if (key.isWritable()) {
                try{
                    String msg = getUserInput();
                    if (msg.equalsIgnoreCase("exit")) {
                        processWrite(key, msg);
                        return true; // Exit
                    }
                    processWrite(key, msg);
                }catch (IOException e) {
                    System.out.println(ASCII_COLORS.ANSI_CYAN + "[Client] " + ASCII_COLORS.ANSI_RESET +"Error reading user input: " + e.getMessage());
                }
            }

        }
        return false; // Not done yet
    }

    private static void processWrite(SelectionKey key, String msg) {
        SocketChannel sChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
        try{
            sChannel.write(buffer);
        } catch (IOException e) {
            System.out.println(ASCII_COLORS.ANSI_CYAN+"[Client] "+ASCII_COLORS.ANSI_RESET+"Error writing to socket channel: " + e.getMessage());
        }
        try{
            sChannel.register(key.selector(), SelectionKey.OP_READ);
        } catch (ClosedChannelException e) {
            System.out.println(ASCII_COLORS.ANSI_CYAN+"[Client] "+ASCII_COLORS.ANSI_RESET+"Error registering channel: " + e.getMessage());
        }
    }

    private static String processRead(SelectionKey key) throws CharacterCodingException {
        SocketChannel sChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try{
            sChannel.read(buffer);
        } catch (IOException e) {
            System.out.println(ASCII_COLORS.ANSI_CYAN+"[Client]"+ASCII_COLORS.ANSI_RESET+"Error reading from socket channel: " + e.getMessage());
        }
        buffer.flip();
        Charset charset = StandardCharsets.UTF_8;
        CharsetDecoder decoder = charset.newDecoder();
        CharBuffer charBuffer = decoder.decode(buffer);
        try{
            sChannel.register(key.selector(), SelectionKey.OP_WRITE);
        } catch (ClosedChannelException e) {
            System.out.println(ASCII_COLORS.ANSI_CYAN+"[Client]"+ASCII_COLORS.ANSI_RESET+"Error registering channel: " + e.getMessage());
        }
        return charBuffer.toString();
    }

    private static boolean processConnect(SelectionKey key) {
        SocketChannel channel = (SocketChannel) key.channel();
        try {
            // Call the finishConnect() in a
            // loop as it is non-blocking for your channel
            while (channel.isConnectionPending()) {
                channel.finishConnect();
            }
        } catch (IOException e) {
            key.cancel(); // Cancel the channel's registration with the selector
            System.out.println(ASCII_COLORS.ANSI_CYAN+"[Client]"+ASCII_COLORS.ANSI_RESET+"Error connecting to server: " + e.getMessage());
            return false;
        }
        return true;
    }

    public static String getUserInput() throws IOException {
        String promptMsg = "Please enter a message: \t\t(type 'exit' to quit)\n";
        System.out.print(ASCII_COLORS.ANSI_CYAN +"[Client] "+ ASCII_COLORS.ANSI_RESET +promptMsg);
        return input.readLine();
    }
}
