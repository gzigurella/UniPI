//MIAO https://www.demo2s.com/java/java-non-blocking-socket-channel-echo-server-example.html

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * Make an echo server with Java NIO and a Selector with non-blocking channels.
 * The server appends received message to the string "Echoed by server: " and send it back to client.
 */
public class EchoServer {
    private static class ASCII_COLORS{
        public static final String ANSI_RESET = "\u001B[0m";
        public static final String ANSI_RED = "\u001B[31m";
        public static final String ANSI_CYAN = "\u001B[36m";
        public static final String ANSI_YELLOW = "\u001B[33m";
    }

    public static int DEFAULT_PORT = 8888;

    public static void main(String[] args) {
        int port;
        if(args.length > 0) {
            try{
                port = Integer.parseInt(args[0]);
            }catch(RuntimeException ex) {
                port = DEFAULT_PORT;
            }
        }
        else port = DEFAULT_PORT;
        System.out.println(ASCII_COLORS.ANSI_CYAN + "Starting Echo server on port: " + port + ASCII_COLORS.ANSI_RESET);
        try{
            server(port);
        }catch (IOException e){
            System.out.println(ASCII_COLORS.ANSI_RED + "Could not start server on port: " + port + ASCII_COLORS.ANSI_RESET);
        }
        System.out.println(ASCII_COLORS.ANSI_RED + "Echo Server stopped" + ASCII_COLORS.ANSI_RESET);
    }

    public static void server(int port) throws IOException {
        ServerSocketChannel serverChannel = ServerSocketChannel.open(); // Start ServerSocket channel
        serverChannel.socket().bind(new InetSocketAddress("localhost", port)); // Bind the channel to the port
        serverChannel.configureBlocking(false); // Set as non blocking

        Selector Multiplexer = Selector.open(); // Start Selector
        serverChannel.register(Multiplexer, SelectionKey.OP_ACCEPT); // Register the channel with the selector

        while (true) {
            if(Multiplexer.select() <= 0) continue; // If there is a channel ready to be processed
            Set<SelectionKey> keys = Multiplexer.selectedKeys(); // Get the set of keys
            keyHandler(keys); // Handle the channel
        }
    }

    public static void keyHandler(Set<SelectionKey> keys) {
        SelectionKey key = null;
        Iterator<SelectionKey> iter = keys.iterator();
        while(iter.hasNext()){
            key = iter.next(); // Get the key
            iter.remove(); // Remove the key from the set so that it won't be processed again
            if(key.isAcceptable()){
                try{
                    acceptProcess(key);
                }catch (IOException e){
                    System.out.println(ASCII_COLORS.ANSI_RED + "Error during accept process" + ASCII_COLORS.ANSI_RESET);
                    key.cancel(); //Avoid entering in infinite loop
                    break;
                }
            }
            else if(key.isReadable()){
                try{
                    String msg = readProcess(key);
                    if (msg.equalsIgnoreCase("exit")){
                        key.cancel(); // Cancel the key if the client sent "exit"
                        System.out.println(ASCII_COLORS.ANSI_RED + "Client disconnected" + ASCII_COLORS.ANSI_RESET);
                    }
                    else if(msg.length() > 0){
                        echo(key, msg); // Echo the message
                    }
                }catch (IOException e){
                    System.out.println(ASCII_COLORS.ANSI_RED + "Error during read process, client disconnected?" + ASCII_COLORS.ANSI_RESET);
                    key.cancel(); //Avoid entering in infinite loop
                    break;
                }
            }
        }
    }

    public static void acceptProcess(SelectionKey key) throws IOException {
        ServerSocketChannel server = (ServerSocketChannel) key.channel(); //Establish connection
        SocketChannel client = server.accept(); // Accept connection
        client.configureBlocking(false); // Set the client channel to non-blocking mode

        System.out.println(ASCII_COLORS.ANSI_YELLOW + "Connection accepted from " + client+ ASCII_COLORS.ANSI_RESET);
        client.register(key.selector(), SelectionKey.OP_READ); // Register the channel with the selector
    }

    public static String readProcess(SelectionKey key) throws IOException {
        SocketChannel sChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int bytesCount = sChannel.read(buffer);
        String msg = "";

        if (bytesCount > 0) {
            buffer.flip();
            Charset charset = StandardCharsets.UTF_8;
            CharsetDecoder decoder = charset.newDecoder();
            CharBuffer charBuffer = decoder.decode(buffer);
            msg = charBuffer.toString();
        }
        if (msg.equalsIgnoreCase("exit")) return "exit";
        System.out.println(ASCII_COLORS.ANSI_YELLOW +"Received Message: [" + msg +"]"+ ASCII_COLORS.ANSI_RESET);
        return "Echoed by server: " + msg;
    }

    public static void echo(SelectionKey key, String msg) throws IOException {
        SocketChannel sChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
        sChannel.write(buffer);
        System.out.println(ASCII_COLORS.ANSI_YELLOW + "Sent Message: [" + msg +"]"+ ASCII_COLORS.ANSI_RESET);
    }
}
