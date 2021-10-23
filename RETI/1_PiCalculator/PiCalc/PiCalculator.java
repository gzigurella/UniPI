//define package
package PiCalc;

public class PiCalculator implements Runnable{
    private double accuracy;
    private double pi;
        
    public PiCalculator (double accuracy, double pi){
        this.accuracy = accuracy;
        this.pi = pi;
    }
    
    public double getPi(){
        return pi;
    }

    public void run() {
        System.out.println("Thread is running\n");
        long i = 0;
        while(!Thread.interrupted()) {
            try {
                //Gregory-Leibniz succession
                pi += (Math.pow(-1, i) / (2 * i + 1)) * 4;
                i++;
                if(Math.abs(pi - Math.PI) < accuracy) break;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                break;
            }
        }
        return;
    }
}
