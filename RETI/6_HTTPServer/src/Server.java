import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Server {

    public static class ANSI_COLORS{
        public static final String RESET = "\u001B[0m";
        public static final String CYAN = "\u001B[36m";
        public static final String GREEN = "\u001B[32m";
        public static final String RED = "\u001B[31m";
    }

    private static final int CORE_POOL_SIZE = 4;
    private static final int MAXI_POOL_SIZE = 26;
    private static final int KEEPALIVE_TIME = 20;
    static String PATH;

    public static void main(String[] args) throws IOException {
        if(args.length < 3){
            System.out.println("\nUsage: -$java ./HTTPServer <PORT> <FILES PATH> <REQUESTS_BEFORE_SHUTDOWN> (-u for unlimited request)");
            System.exit(0);
        }
        //get program arguments
        final int PORT = Integer.parseInt(args[0]);
        PATH = args[1] + "/";

        //check if server will accept unlimited requests or a given number of requests
        int requestsBeforeShutdown = args[2].equals("-u") ?  -1 : (Integer.parseInt(args[2]));
        ServerSocket serverSocket = null;
        try{
            serverSocket = new ServerSocket(PORT);
            // Print operation status
            System.out.println(
                    ANSI_COLORS.CYAN+
                    "Server start: "+
                    ANSI_COLORS.GREEN+"OK"
                    +ANSI_COLORS.RESET
            );
            Thread.sleep(100);
            status(PORT);
        }catch (IOException | InterruptedException e){
            // Print operation status
            if(e instanceof InterruptedException){
                System.out.println(
                        ANSI_COLORS.CYAN+
                        "Server start: "+
                        ANSI_COLORS.RED+"SIG_INT"
                        +ANSI_COLORS.RESET
                );
            }
            else System.out.println(
                    ANSI_COLORS.CYAN+
                    "Server start: "+
                    ANSI_COLORS.RED+"FAIL"
                    +ANSI_COLORS.RESET
            );
            // Print exception message
            System.out.println(ANSI_COLORS.RED + e.getMessage() + ANSI_COLORS.RESET);
            bruteExit();
        }

        LinkedBlockingQueue<Runnable> requestQueue = new LinkedBlockingQueue<>(8);
        ThreadPoolExecutor clientExecutor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAXI_POOL_SIZE,
                KEEPALIVE_TIME,
                TimeUnit.SECONDS,
                requestQueue);

        int requestCount = 0; // used to track the number of requests from clients

        // Every client is handled by a thread in the pool
        // Waits for requestBeforeShutdown requests, or handles infinite requests on default options.
        if(requestsBeforeShutdown != -1){
            while(requestCount < requestsBeforeShutdown){
                try{
                    handler(serverSocket, clientExecutor); //handle client request
                    requestCount++;
                }catch (IOException e){
                    System.out.println(e.getMessage());
                    break;
                }
            }
        }
        else
            while(true){
                try{
                    handler(serverSocket, clientExecutor); //handle client request
                }catch(IOException e){
                    System.out.println(e.getMessage());
                    break;
                }
            }

        clientExecutor.shutdown();// Wait till termination of current tasks before closing the server.
        serverSocket.close();     // Do not accept connections anymore, close the server.
        System.exit(0);    // Exit with success :-)
    }

    private static void status(int PORT) throws InterruptedException{
        System.out.println("\nHTTP SERVER STARTED PORT: " + PORT);
        System.out.println("FILES DIRECTORY: " + PATH);
        Thread.sleep(100);
        System.out.println(ANSI_COLORS.CYAN+"Server Directory Setup: "+ ANSI_COLORS.GREEN +"OK"+ANSI_COLORS.RESET);
        System.out.println("Connect to http://localhost:" + PORT+"\n");
    }

    private static void handler(ServerSocket serverSocket, ThreadPoolExecutor clientExecutor) throws IOException{
        Socket client = serverSocket.accept();
        System.out.println("Client request: "+ ANSI_COLORS.GREEN +"Accepted!"+ ANSI_COLORS.RESET);
        clientExecutor.submit(new Handler(client));
        System.out.println("Client request: "+ ANSI_COLORS.GREEN +"Satisfied\n"+ ANSI_COLORS.RESET);
    }

    private static void bruteExit(){
        System.exit(-1);
    }
}