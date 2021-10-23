import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Docente implements Runnable{
    private final ReentrantReadWriteLock.WriteLock LabAccess = PCLab.PClocks.writeLock(); //Nobody can access the Lab while occupied by a Professor
    private final int index;

    public Docente(int i){
        this.index = i;
        Thread.currentThread().setPriority(9);
    }

    @Override
    public void run(){
        int k = ThreadLocalRandom.current().nextInt(1, Short.MAX_VALUE);

        for(int i = 0; i < k; i++) {
            boolean Taskdone = false;
            //System.out.printf("Professore [%d] sta entrando nel laboratorio\n", this.index);
            while (!Taskdone) {
                LabAccess.lock();
                try {
                    System.out.printf("Professore [%d] usa il laboratorio\n", this.index);
                    Thread.sleep(ThreadLocalRandom.current().nextInt(1, 5) * 1000L); //Simulate busyness
                    Taskdone = true;
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                } finally {
                    LabAccess.unlock();
                    //System.out.printf("Professore [%d] sta uscendo dal laboratorio\n", this.index);
                    try {
                        Thread.sleep(5 * 1000L); //wait before next execution
                    } catch (InterruptedException e) {
                        System.out.printf("Il Professore [%d] è stato interrotto\n", this.index);
                    }
                }
            }
        }
    }
}
