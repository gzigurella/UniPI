import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class PCLab {
    public static ReentrantReadWriteLock PClocks = new ReentrantReadWriteLock(true);
    public static ArrayList<ReentrantReadWriteLock> PCs = new ArrayList<>(20);

    public PCLab(){
        for(int i = 0; i < 20; i++){
            PCs.add(new ReentrantReadWriteLock(true));
        }
    }
}
