public class Tesista implements Runnable{
    private final Laboratorio L;
    public int id;

    public Tesista(int i, Laboratorio l){
        this.id = i;
        this.L = l;
    }

    public String getType() {
        return "Tesista";
    }

    public int getId(){
        return this.id;
    }

    @Override
    public void run() {
        try {
            this.L.use(this);
        } catch (InterruptedException e) {
            System.out.printf("Tesista [%d] è stato interrotto\n", this.getId());
        }
    }
}
