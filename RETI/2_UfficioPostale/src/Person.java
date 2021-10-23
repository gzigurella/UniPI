import java.util.concurrent.ThreadLocalRandom;

public class Person implements Runnable{
    final private int Id;

    public Person(int id){
        this.Id = id;
    }

    public int getId() {
        return Id;
    }

    @Override
    public void run() {
        System.out.printf("Person [%d] entered the second room\n", getId());
        int wait = ThreadLocalRandom.current().nextInt(2, 10); //generate a random int between 2 and 10
        try{
            Thread.sleep(wait * 1000L);
        }catch(InterruptedException e){
            System.out.printf("Person [%d] is leaving the room due to an emergency\n", getId());
            System.out.println(e.getMessage());
        }
        System.out.printf("Person [%d] is now exiting the office\n", getId());
    }
}
