import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Tutor {
    private final int nStudenti;
    private final int nTesisti;
    private final int nProfessori;

    public Tutor(int s, int p, int t){
        this.nStudenti = s;
        this.nProfessori = p;
        this.nTesisti = t;
    }

    public void use(){
        new PCLab();
        ExecutorService UserPool = Executors.newFixedThreadPool(nProfessori+nTesisti+nStudenti);
        int p = this.nProfessori, s = this.nStudenti, t = this.nTesisti;
        while(p != 0 || s != 0 || t != 0){
            if(p != 0){
                UserPool.execute(new Docente(p));
                p--; //Keep decreasing the number of professors to add
            }
            if(t != 0){
                UserPool.execute(new Tesista(t));
                t--; //Keep decreasing the number of graduates to add
            }
            if( s!= 0){
                UserPool.execute(new Studente(s));
                s--; //Keep decreasing the number of students to add
            }
        }

        // Terminate Threads
        close(UserPool);
    }

    public void close(ExecutorService E){
        E.shutdown();
        try{
            if(!E.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS)) E.shutdownNow();
        }catch(InterruptedException e){
            System.out.println(e.getMessage());
        }finally {
            E.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
