import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.nio.ByteBuffer;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class JSONreader {
    public static void main(String[] args) {
        String result = null;
        // read the json file
        try{
             result = Producer.produce();
        }catch(IOException ex) {
            System.out.println("Errore durante la lettura del file JSON");
            System.exit(-1);
        }
        System.out.println(result);
    }

    public static class Consumer implements Runnable{
        private final Account account;
        private final AtomicIntegerArray array;

        public Consumer(Account account, AtomicIntegerArray array){
            this.account = account;
            this.array = array;
        }

        @Override
        public void run() {
            for(Payment payment: account.getPayments()){
                array.getAndIncrement(payment.getCausale());
            }
        }
    }

    public static class Producer{
        private static final AtomicIntegerArray Causali = new AtomicIntegerArray(5);

        public static String produce() throws IOException {
            FileChannel fileChannel = FileChannel.open(
                    Paths.get("Acc.json"),
                    StandardOpenOption.READ
            );
            StringBuilder sb = new StringBuilder(); // To avoid waste of memory due to multiple string reads

            ByteBuffer buffer = ByteBuffer.allocate((int)fileChannel.size()); // Allocate the buffer for the whole file
            fileChannel.read(buffer); // Read the whole file into the buffer

            buffer.flip(); // Flip the buffer to make it ready to read

            while(buffer.hasRemaining()){
                sb.append((char)buffer.get()); // Read the next character
            }

            // parse the JSON with GSON from the string
            Gson gson = new Gson();
            ArrayList<Account> accounts = gson.fromJson(sb.toString(),
                    new TypeToken<ArrayList<Account>>(){}.getType());
            ExecutorService exec = Executors.newCachedThreadPool();

            for (Account account: accounts){
                exec.execute(new Consumer(account, Causali));
            }

            // Create the String to print
            ArrayList<String> typeCausali = Tipi();
            StringBuilder result = new StringBuilder();
            for(int j = 0; j < 5; j++){
                result.append("\n")
                      .append(typeCausali.get(j))
                      .append(":\t")
                      .append(Causali.get(j));
            }
            exec.shutdown();
            return result.toString();
        }

        private static ArrayList<String> Tipi(){
            ArrayList<String> typeCausali = new ArrayList<>();
            typeCausali.add("Bonifico");
            typeCausali.add("Accredito");
            typeCausali.add("Bollettino");
            typeCausali.add("F24");
            typeCausali.add("PagoBancomat");
            return typeCausali;
        }
    }
}
