import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class JSONmaker {
    private static class ASCII_COLORS{
        public static final String ANSI_RESET = "\u001B[0m";
        public static final String ANSI_RED = "\u001B[31m";
        public static final String ANSI_GREEN = "\u001B[32m";
        public static final String ANSI_YELLOW = "\u001B[33m";
        public static final String ANSI_CYAN = "\u001B[36m";
    }
    private final static int _500MEGABYTES_ = 500 * 1024;
    private final static int nAccounts = 100;
    private final static int nTransactions = 1000;
    private final static String[] names = { "Gabriele", "Marco", "Luigi", "Gianmarco", "Francesco", "Matteo",
            "Michele", "Samuele", "Paolo", "Ilenia", "Ilaria", "Arianna", "Manuela", "Carla", "Chiara", "Maria",
            "Sara", "Ambra", "Giulia"
    };
    private final static String[] surnames = { "Neri", "Bianchi", "Verdi", "Rossi", "Chiari", "Scuri", "Giovani",
            "Vecchi", "De Guille", "Sagrigenti", "Salamone", "Signorini", "Zini", "Cavallaro"
    };

    public static void main(String[] args) {
        WritableByteChannel file = null;
        try{
            //Try ti open as writable the file or create it if it doesn't exist
            file = FileChannel.open(
                    Paths.get("Acc.json"),
                    StandardOpenOption.WRITE,
                    StandardOpenOption.CREATE
            );
        }catch (IOException ex){
            System.out.println(ASCII_COLORS.ANSI_RED + "Errore nell'apertura del file" + ASCII_COLORS.ANSI_RESET);
            System.exit(-1);
        }

        ByteBuffer buffer = ByteBuffer.allocate(_500MEGABYTES_); // Make space for 500 MB of data
        ArrayList<Account> accounts = new ArrayList<>(nAccounts); // Create an array of accounts
        System.out.println(ASCII_COLORS.ANSI_CYAN + "Generating accounts ..." + ASCII_COLORS.ANSI_RESET);
        for(int i = 0; i < nAccounts; i++){
            accounts.add(newRandAcc()); // Add a new random account to the list
        }

        System.out.println(ASCII_COLORS.ANSI_YELLOW + "Generating JSON File ..." + ASCII_COLORS.ANSI_RESET);
        buffer.flip(); // Make the buffer ready to be written
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(accounts); // Convert the accounts to a json string
        buffer = ByteBuffer.wrap(json.getBytes()); // Write data to the buffer
        assert file != null;
        try{
            file.write(buffer); // Write the buffer to the file
        }catch(IOException ex) {
            System.out.println(ASCII_COLORS.ANSI_RED + "Errore nella scrittura del file" + ASCII_COLORS.ANSI_RESET);
            System.exit(-1);
        }
        buffer.clear(); // Make the buffer ready to be read
        System.out.println(ASCII_COLORS.ANSI_GREEN+ "JSON File generated!" + ASCII_COLORS.ANSI_RESET);
    }

    private static Account newRandAcc(){
        // Create a string for a full name with names and surnames
        String fullname = (names[(int) (Math.random() * names.length)]
                + " " +
                surnames[(int) (Math.random() * surnames.length)]);
        // Create a random number of transactions
        int nTrans = (int) (Math.random() * nTransactions);
        Account acc = new Account(fullname, new ArrayList<>(nTrans));

        for (int i = 0; i < nTrans; i++){
            acc.addPayment(new Payment(randDate(), ThreadLocalRandom.current().nextInt(5)));
        }
        return acc;
    }

    private static String randDate(){
        // Create a random date in the format dd/mm/yyyy
        int day = (int) (Math.random() * 31);
        int month = (int) (Math.random() * 12);
        int year = (int) (Math.random() * 2) + 2019; // transitions of the last two years
        return day + "/" + month + "/" + year;
    }
}
