public class Docente implements Runnable{
    private final Laboratorio L;
    public int id;

    public Docente(int i, Laboratorio l){
        this.id = i;
        this.L = l;
    }

    public String getType() {
        return "Docente";
    }

    public int getId(){
        return this.id;
    }

    @Override
    public void run() {
        try {
            this.L.use(this);
        } catch (InterruptedException e) {
            System.out.printf("Docente [%d] è stato interrotto\n", this.getId());
        }
    }
}
