import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BroadcastAgent implements Runnable {
    private final BroadcastSystem broadcastSystem; //Reference to the shared broadcast system
    private final DSM dsm; //Reference to the associated DSM instance
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1); //to handle delayed message broadcasts

    public BroadcastAgent(BroadcastSystem system) {
        this.broadcastSystem = system;
        this.dsm = null;
    }

    public void broadcast(String message) {   //Broadcasts a message to other DSM instances with a delay
        int delay = (int) (Math.random() * 1000); //Generate a random delay in milliseconds
        scheduler.schedule(() -> broadcastSystem.broadcast(message), delay, TimeUnit.MILLISECONDS); //Schedule the broadcast to occur after the random delay
    }

    public void receive(String message) {
        dsm.handleBroadcast(message); //Update the DSM based on broadcast
    }

    @Override
    public void run() {
        System.out.println("BroadcastAgent running.");
    }
}
