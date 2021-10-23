import java.io.*;

/**
 * Si scriva un programma JAVA che riceve in ingresso un filepath che individua una directory D
 * stampa le informazioni del contenuto di quella directory e, ricorsivamente,
 * di tutti i file contenuti nelle sottodirectory di D
 *
 * Il programma deve essere strutturato come segue:
 * - attiva un thread produttore e un insieme di k thread consumatori
 * - il produttore comunica con i consumatori mediante una coda
 * - il produttore visita ricorsivamente la directory data ed, eventualmente tutte le sottodirectory e mette nella coda il nome di ogni directory individuata
 * - i consumatori prelevano dalla coda i nomi delle directories e stampano il loro contenuto (nomi dei file)
 * - la coda deve essere realizzata con una LinkedList.
 * - Ricordiamo che una Linked List non è una struttura thread-safe.
 */



public class FileCrawler {

    //ANSI Colors
        public static final String ANSI_RESET = "\u001B[0m";
        public static final String ANSI_CYAN = "\u001B[36m";
        public static final String ANSI_GREEN = "\u001B[32m";
        public static final String ANSI_RED = "\u001B[31m";

    static MonitorDataStream monitor; //declare monitor as global variable, later accessed

    public static void main(String[] args){
        String path = null; //Init path to scan
        int k = 1; //Default number of consumer threads to use

        try{
            FileOutputStream file = new FileOutputStream("./result.txt"); //file to store the output of our file crawler
            monitor = new MonitorDataStream(file);
        }catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        SyncList dirList = new SyncList(); //Init the list of scanned directories

        try{
            k = Integer.parseInt(args[1]); //get the number of consumer threads to create
            path = args[0]; //get the path to scan
        }catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("Usage: ./FileCrawler <absolute-path-to-explore> <consumer-threads-number>");
            System.exit(-1);
        }
        assert path != null; //interrupt the main thread if there is no given path

        File D = new File(path); //Init Directory data structure
        assert_path(D); //check if path exists and if it's a directory
        System.out.println(ANSI_CYAN + "\n<--- Starting to scan " + ANSI_RESET + path + ANSI_CYAN + " --->\n" + ANSI_RESET);

        //starts k file readers thread - default 1;
        for(int i = 0; i < k; i++){
            Consumer consumer = new Consumer(dirList);
            Thread tConsumer = new Thread(consumer);
            tConsumer.start();
            System.out.println(ANSI_RED + "Consumer " + ANSI_RESET + (i+1) + " is ready");
        }

        //Producer push directories in the list
        Producer producer = new Producer(dirList, path);
        Thread tProducer = new Thread(producer);
        tProducer.start();
        System.out.println(ANSI_GREEN +"Producer " + ANSI_RESET + "is ready");

        try{
            tProducer.join();
        }catch(InterruptedException e){
            System.out.println(e.getMessage());
        }
        System.out.println(ANSI_CYAN +"\n<--- Folder explored, writing results --->\n" + ANSI_RESET);
    }

    public static void assert_path(File F){
        if(!F.exists()){
            System.out.printf("%s the given path is not valid\n", F.getAbsolutePath());
            System.exit(-1); //Exit unsuccessful due to caught exception
        }
        if(!F.isDirectory()){
            System.out.printf("%s is not a directory\n", F.getAbsolutePath());
            System.exit(-1); //Exit unsuccessful due to caught exception
        }
    }
}