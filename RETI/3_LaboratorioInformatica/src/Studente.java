import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Studente implements Runnable {
    private final ReentrantReadWriteLock.ReadLock LabAccess = PCLab.PClocks.readLock(); //Every student can access the Lab
    private final ArrayList<ReentrantReadWriteLock> PC = PCLab.PCs;
    private final int index;

    public Studente(int i) {
        this.index = i;
    }

    @Override
    public void run() {
        int k = ThreadLocalRandom.current().nextInt(1, Short.MAX_VALUE);
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        Lock PClock = lock.writeLock();

        for (int j = 0; j < k; j++) {
            //System.out.printf("Studente [%d] è entrato nel laboratorio\n", this.index);
            boolean Taskdone = false;
            while (!Taskdone) { //<-- Keep going until we get a hold on the resources
                int i = ThreadLocalRandom.current().nextInt(0, PC.size()); //Choose a random PC
                LabAccess.lock();
                try {
                    while (!Taskdone) {
                        PClock = PC.get(i).writeLock();
                        if (PC.get(i).getWriteHoldCount() == 0) {
                            PClock.lock();
                            System.out.printf("Studente [%d] sta usando il PC %d\n", this.index, i);
                            Thread.sleep(ThreadLocalRandom.current().nextInt(1, 5) * 1000L); //Simulate Busyness
                            Taskdone = true; //<-- We managed to use the resources
                        }
                    }
                } catch (InterruptedException e) {
                    System.out.printf("Lo studente [%d] è stato interrotto\n", this.index);
                } finally {
                    LabAccess.unlock();
                    PClock.unlock();
                    //System.out.printf("Studente [%d] sta uscendo dal Laboratorio\n", this.index);
                    try {
                        Thread.sleep(5 * 1000L); //wait before next execution
                    } catch (InterruptedException e) {
                        System.out.println("Studente -- Sleep between executions");
                    }
                }
            }
        }
    }
}

