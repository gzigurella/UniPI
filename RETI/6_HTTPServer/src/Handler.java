import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

public record Handler(Socket clientSocket) implements Runnable {

    @Override
    public void run() {
        System.out.println("<-- Client connected. (" + Thread.currentThread() + ") -->");

        BufferedReader inFromClient; //used to read request
        String request; //used to store the request

        while (true) {
            try {
                //read request type from STDIN
                inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                if (!(request = inFromClient.readLine()).isEmpty()) {
                    break; //break once we get a non-empty line from STDIN
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        try {
            sendResponse(request, clientSocket); //build request and send it to server
            inFromClient.close(); //close reader buffer
            clientSocket.close(); //close connection from client-side, deny new requests from current client
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("<-- Client disconnected. (" + Thread.currentThread() + ")-->");
    }

    private void sendResponse(String requestLine, Socket clientSocket) throws IOException {

        //used to send data to client from server
        OutputStream response = clientSocket.getOutputStream();

        //need to tokenize requestLine and get request needed from client, as well as file extension
        StringTokenizer tokenizer = new StringTokenizer(requestLine, "/ ");
        StringBuilder responseHeaders; //used to build response string
        String extension;   //used to switch between files extensions

        //base path to look for contents
        final String basePath = Server.PATH;

        ArrayList<String> requestArray = new ArrayList<>();

        while (tokenizer.hasMoreTokens()) {
            //keep adding the next request until all tokens are consumed
            requestArray.add(tokenizer.nextToken());
        }

        //if request file is null, redirect to ./WebServer/index.html
        if (requestArray.get(1).equals("HTTP")) {
            requestArray.add(1, "index.html");
        }

        requestLine = basePath + requestArray.get(1);

        //read requested content file
        File requestedFile = new File(requestLine);
        FileInputStream fileInputStream;
        System.out.println(requestedFile);
        try {
            fileInputStream = new FileInputStream(requestedFile);
        } catch (FileNotFoundException e) {

            //if not found send 404 code to client, and return
            System.out.println("404\t|\tFile not found!");
            response.write(fileNotFound());
            return;
        }

        //get file extension to specify content type
        int index = requestArray.get(1).indexOf(".");
        extension = requestArray.get(1).substring(index);


        //--> building headers start
        System.out.println("Providing resource: " + requestedFile);
        responseHeaders = new StringBuilder(
                """
                HTTP/1.1 200 OK\r
                Server: HTTP_Java_Server\r
                """);

        switch (extension) {
            case ".txt" -> responseHeaders.append("Content-Type: text/plain\r\n");
            case ".jpg" -> responseHeaders.append("Content-Type: image/jpeg\r\n");
            case ".html" -> responseHeaders.append("Content-Type: text/html\r\n");
            case ".gif" -> responseHeaders.append("Content-Type: image/gif\r\n");
            default -> {
                //Do nothing
            }
        }
        responseHeaders.append("\r\n");
        //--> end of headers building


        try {

            byte[] responseContent = new byte[(int) requestedFile.length()];
            assert response != null;

            //read response from the server
            if (fileInputStream.read(responseContent) == -1) {
                System.out.println("Failed to read file.");
                response.close();
                return;
            }

            //sending everything to client
            response.write(responseHeaders.toString().getBytes());
            response.write(responseContent);
            response.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    //html string builder for 404 error
    private byte[] fileNotFound() {
        String message = """
                HTTP/1.1 404 Not Found\r
                Server: HTTP_Java_Server\r
                Content-Type: text/html\r
                \r
                <h1>ERROR 404&nbsp;</h1>
                <p>Not Found</p>""";
        return message.getBytes();
    }

}