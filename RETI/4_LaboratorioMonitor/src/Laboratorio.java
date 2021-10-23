import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
/*
 * Laboratory class is used to synchronize the use of the computers and the laboratory itself
 */
public class Laboratorio {
    protected static AtomicBoolean access = new AtomicBoolean(true);
    public static ArrayList<Integer> PC = new ArrayList<>(20);

    public Laboratorio(){
        for (int i = 0; i < 20; i++) {
            PC.add(i);
        }
    }

    public void use(Runnable R) throws InterruptedException {
        String type = null; int identifier = -1;
        synchronized (this) {
            while (!access.getAcquire()) this.wait();
            if (access.compareAndExchange(true, false)) {
                if (R instanceof Docente) {
                    type = ((Docente) R).getType();
                    identifier = ((Docente) R).getId();
                    System.out.printf("Docente [%d] sta usando il Lab\n", ((Docente) R).getId());
                } else if (R instanceof Tesista) {
                    type = ((Tesista) R).getType();
                    identifier = ((Tesista) R).getId();
                    int indexPC = PC.get(((Tesista) R).getId());
                    System.out.printf("Tesista [%d] sta usando il PC %d\n", ((Tesista) R).getId(), indexPC);
                } else if (R instanceof Studente) {
                    type = ((Studente) R).getType();
                    identifier = ((Studente) R).getId();
                    int randomPC = PC.get(ThreadLocalRandom.current().nextInt(1, 20));
                    System.out.printf("Studente [%d] sta usando il PC %d\n", ((Studente) R).getId(), randomPC);
                }
            }
            Thread.sleep(ThreadLocalRandom.current().nextInt(1, 5) * 1000L); //Simulate work
            System.out.printf("%s [%d] sta uscendo dal laboratorio\n", type, identifier);
            access.set(true);
            this.notifyAll();
        }
    }
}
