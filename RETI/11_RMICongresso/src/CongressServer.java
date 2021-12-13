import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Si progetti un’applicazione Client/Server per la gestione delle registrazioni a un congresso.
 * L’organizzazione del congresso fornisce agli speaker delle varie sessioni un’interfaccia tramite
 * la quale iscriversi a una sessione, e la possibilità di visionare i programmi
 * delle varie giornate del congresso, con gli interventi delle varie sessioni.
 * Il server mantiene i programmi delle tre giornate del congresso, ciascuno dei quali è memorizzato in una struttura
 * dati come quella mostrata di seguito, in cui a ogni riga corrisponde una sessione (in tutto 12 per ogni giornata).
 * Per ciascuna sessione vengono memorizzati i nomi degli speaker che si sono registrati (al massimo cinque).
 *
 */

public class CongressServer extends RemoteServer implements Congress {

    private final ArrayList<HashMap<Integer, ArrayList<String>>> days;
    private final int maxSpeakersPerSession;
    private final int maxDays;

    public CongressServer(int maxDays, int sessionsPerDay, int maxSpeakersPerSession){
        this.maxSpeakersPerSession = maxSpeakersPerSession; // Sets the max number of speakers per session
        this.maxDays = maxDays; // Sets the max number of days
        this.days = new ArrayList<>(maxDays); // Creates the days array shared among the server

        for(int i = 0; i < maxDays; i++){
            days.add(i, new HashMap<>(sessionsPerDay)); // Creates the sessions per day
            for(int j = 0; j < sessionsPerDay; j++) // Creates the slots per session
                days.get(i).put(j, new ArrayList<>(this.maxSpeakersPerSession));
        }
    }


    /**
     *
     * @return the days 'matrix' of the schedule
     * @throws RemoteException if a communication error occurs
     */
    @Override
    public synchronized ArrayList<HashMap<Integer, ArrayList<String>>> getSchedule() throws RemoteException {
        return days;
    }

    /**
     * @param name the name of the speaker
     * @param day the day of the schedule
     * @param slot the session of the schedule
     * @return true if the speakers has been added to the day, false otherwise
     * @throws RemoteException if a communication error occurs
     */
    @Override
    public synchronized boolean enrollSpeaker(String name, int day, int slot) throws RemoteException {
        // check if the day is valid
        if(day < 0 || day > maxDays) return false;
        // check if the slot is valid
        if(slot < 0 || slot >= days.get(day).size()) return false;

        // check if the slot is full
        if(this.days.get(day).get(slot).size() < maxSpeakersPerSession)
            return this.days.get(day).get(slot).add(name);
        else return false;
    }

    public synchronized void printSchedule() throws RemoteException {
        Iterator<Map.Entry<Integer, ArrayList<String>>> iter; // Iterator for the days

        for(HashMap<Integer, ArrayList<String>> e : days){
            System.out.println(" ────────────────────────── " + "Day " + days.indexOf(e) + " ────────────────────────── ");
            iter = e.entrySet().iterator(); // Iterator for the sessions
            while(iter.hasNext()){
                Map.Entry<Integer, ArrayList<String>> entry = iter.next();
                System.out.println("Slot: "
                        + entry.getKey().toString()
                        + " Speakers: "
                        + entry.getValue().toString());
            }
        }
        System.out.println(" ────────────────────────── " + "End of schedule " + " ────────────────────────── ");
    }
}