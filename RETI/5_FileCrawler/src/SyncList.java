import java.io.File;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

public class SyncList {
    private final LinkedList<File> dirs; //list of scanned directories
    private final AtomicBoolean done; //used to check whether we have completed to read
    private int size = 0;

    public SyncList(){
        done = new AtomicBoolean(false);
        dirs = new LinkedList<>();
    }

    public synchronized void push(File f){
        this.dirs.push(f);
        size++;
        this.notifyAll();
    }

    public synchronized File pop(){
        while(size == 0){
            if(this.done.get()){ //check if we are waiting to push values
                return null; //if not and size is zero, then we can return
            }else {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        size--;
        this.notifyAll();
        return this.dirs.pop();
    }

    public synchronized void setCompleted(){
        this.done.set(true); this.notifyAll();
    }

}