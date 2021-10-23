public class Studente implements Runnable{
    private final Laboratorio L;
    public int id;

    public Studente(int i, Laboratorio l){
        this.id = i;
        this.L = l;
    }

    public String getType() {
        return "Studente";
    }

    public int getId(){
        return this.id;
    }

    @Override
    public void run() {
        try {
            this.L.use(this);
        } catch (InterruptedException e) {
            System.out.printf("Studente [%d] è stato interrotto\n", this.getId());
        }
    }
}
