import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public interface Congress extends Remote {

    /**
     * @brief Adds a session to the schedule.
     *
     * @return null if schedule is empty, Arraylist of hashmaps containing session and speakers o/w.
     * @throws RemoteException if errors occurred during remote call.
     */
    ArrayList<HashMap<Integer, ArrayList<String>>> getSchedule() throws RemoteException;

    /**
     * @brief Adds a session to the schedule.
     *
     * @param name speaker's username
     * @param day  day in which speaker wants to enroll
     * @param slot session slot in which speaker wants to enroll
     * @return true if success, false if params wrong.
     * @throws RemoteException if any errors occurred during remote call.
     */
    boolean enrollSpeaker(String name, int day, int slot) throws RemoteException;


    /**
     * @brief prints the schedule.
     * @throws RemoteException if any errors occurred during remote call.
     */
    void printSchedule() throws RemoteException;
}