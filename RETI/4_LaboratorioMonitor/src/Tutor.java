import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Tutor {
    private final int nStudenti;
    private final int nTesisti;
    private final int nProfessori;
    private final ExecutorService E;
    private final LinkedList<Runnable> Queue = new LinkedList<>();

    public Tutor(int s, int p, int t){
        this.nStudenti = s;
        this.nProfessori = p;
        this.nTesisti = t;
        this.E = Executors.newCachedThreadPool();
    }

    public void openLab(){
        Laboratorio Lab = new Laboratorio();
        int k;
        System.out.println("\n<---- The Laboratory is open ---->");
        int p = this.nProfessori, s = this.nStudenti, t = this.nTesisti;
        while(p != 0 || s != 0 || t != 0){
            if(p != 0){
                k = ThreadLocalRandom.current().nextInt(1, 5);
                for (int i = 0; i < k; i++) {
                    Queue.add((new Docente(p, Lab)));
                }
                p--; //Keep decreasing the number of professors to add
            }
            cannotAccess();
            if(t != 0){
                k = ThreadLocalRandom.current().nextInt(1, 5);
                for (int i = 0; i < k; i++) {
                    Queue.add(new Tesista(t, Lab));
                }
                t--; //Keep decreasing the number of graduates to add
            }
            cannotAccess();
            if( s!= 0){
                k = ThreadLocalRandom.current().nextInt(1, 5);
                for (int i = 0; i < k; i++) {
                    Queue.add(new Studente(s, Lab));
                }
                s--; //Keep decreasing the number of students to add
            }
            cannotAccess();
        }
        exec(Shuffle(Queue)); //Randomize accesses to the Lab and then execute
    }

    public void cannotAccess(){ //Simulate the time between user accesses to the Lab
        try{
            Thread.sleep(100);
        }catch(InterruptedException e){
            System.out.println(e.getMessage());
        }
    }

    void arrayShuffle(Object[] arr){
        Random rand = new Random();
        for (int i = 0; i < arr.length - 1; i++) {

            // select index randomly
            int index = rand.nextInt(i + 1);

            // swapping between i and index
            Object g = arr[index];
            arr[index] = arr[i];
            arr[i] = g;
        }
    }

    public LinkedList<Runnable> Shuffle(LinkedList<Runnable> Q){
        Runnable[] R = new Runnable[Q.size()];
        for (int i = 0; i < R.length; i++) { //Convert List to array
            R[i] = Q.remove();
        }
        //Shuffle the array  5 to 100 times
        for (int i = 0; i < ThreadLocalRandom.current().nextInt(5, 100); i++) {
            arrayShuffle(R);
        }
        return new LinkedList<>(Arrays.asList(R)); //convert Array to list
    }

    public void exec(LinkedList<Runnable> Q){
        for (Runnable r: Q) {
            E.execute(r);
        }
    }

    public void closeLab(){
        try{
            if(!E.awaitTermination(10, TimeUnit.SECONDS)){
                E.shutdown();
            }
            if(!E.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS)) E.shutdownNow();
        }catch(InterruptedException e){
            System.out.println(e.getMessage());
        }finally {
            System.out.println("<---- The Laboratory is closed ---->");
        }
    }
}
