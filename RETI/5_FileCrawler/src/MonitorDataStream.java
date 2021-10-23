import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicBoolean;

//create monitor for the output stream to avoid concurrent access issues
public class MonitorDataStream {
    FileOutputStream outputStream; //the file or another instance where we will write data
    private final AtomicBoolean busy = new AtomicBoolean(false);

    public MonitorDataStream(FileOutputStream file) {
        this.outputStream = file;
    }

    //Ensure synchronized writes on file to avoid dirty writes
    public synchronized void write(String s) {
        while(busy.get()){
            try {
                this.wait();
            }catch(InterruptedException e){
                System.out.println(e.getMessage());
            }
        }

        //If not busy turn to busy and begin to write on file
        busy.set(true);
        try {
            byte[] b = s.getBytes(StandardCharsets.UTF_8); //convert string to bytes format
            outputStream.write(b); //write encoded bytes on chosen file
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        finally{
            busy.set(false);
        }
        this.notify();
    }
}