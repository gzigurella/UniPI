import java.util.LinkedList;
import java.util.concurrent.*;

public class Office {
    private final ThreadPoolExecutor office;
    protected LinkedList<Person> queue;

    public Office(int k){
        System.out.println("Now opening the Office\n\n");
        this.office = new ThreadPoolExecutor(4, 4, 20, TimeUnit.SECONDS, new ArrayBlockingQueue<>(k));
        this.queue = new LinkedList<>(); //We use a linked list to keep the order
    }

    public void Entrance(int n) throws InterruptedException { //Executed in main thread
        System.out.printf("There are %d people inside the first room\n\n", n);
        for(int i = 1; i < n+1; i++){
            System.out.printf("Created ticket [%d] from the machine\n", i);
            Person P = new Person(i);
            this.queue.add(P); //append to the queue as the last element
        }
        while(!this.queue.isEmpty()){ // Try to move a person to second room
            if(this.office.getQueue().remainingCapacity() > 0){ //Check space in the second room's queue
                Person T = this.queue.remove();
                try{
                    SecondRoom(T);
                }catch(RejectedExecutionException e){ // cannot move the person, add it to the list again
                    this.queue.push(T); //push on top of the queue, to not lose order
                }
            }
        }
        closeOffice();
        System.out.println("Closing the Office\n");
    }

    public void SecondRoom(Person P){ //Executes in parallel threads
        office.execute(P);
    }

    public void closeOffice(){
        office.shutdown();
        try {
            if (!office.awaitTermination(60, TimeUnit.SECONDS)) {
                office.shutdownNow();
            }
        } catch (InterruptedException ex) {
            office.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
