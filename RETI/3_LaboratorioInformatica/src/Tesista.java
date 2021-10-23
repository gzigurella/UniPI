import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Tesista implements Runnable {
    private final ReentrantReadWriteLock.ReadLock LabAccess = PCLab.PClocks.readLock(); //Every Graduate can access the Lab
    private final ReentrantReadWriteLock.WriteLock PC; //Only one graduate at the time can lock the PC

    private final int index;
    private final int PCIdx;

    public Tesista(int i) {
        this.index = i;
        this.PCIdx = i % 20; //index must be between the range of the computers (0,19)
        PC = PCLab.PCs.get(this.PCIdx).writeLock();
    }

    @Override
    public void run() {
        int k = ThreadLocalRandom.current().nextInt(1, Short.MAX_VALUE);

        for (int i = 0; i < k; i++) {
            boolean Taskdone = false;
            //System.out.printf("Tesista [%d] sta provando ad accedere al pc\n", index);
            while (!Taskdone) {
                LabAccess.lock();
                try {
                    PC.lock();
                    System.out.printf("Tesista [%d] sta usando il pc %d\n", index, PCIdx);
                    Thread.sleep(ThreadLocalRandom.current().nextInt(1, 5) * 1000L); //Simulate Busyness
                    Taskdone = true;
                } catch (InterruptedException e) {
                    System.out.printf("Il Tesista [%d] è stato interrotto\n", this.index);
                } finally {
                    LabAccess.unlock();
                    PC.unlock();
                    //System.out.printf("Tesista [%d] sta uscendo dal laboratorio\n", this.index);
                    try {
                        Thread.sleep(5 * 1000L); //wait before next execution
                    } catch (InterruptedException e) {
                        System.out.println("Tesista -- Sleep between executions");
                    }
                }
            }
        }
    }
}

