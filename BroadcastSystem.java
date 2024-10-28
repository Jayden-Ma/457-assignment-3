import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BroadcastSystem {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1); //Scheduler to handle delayed message broadcasts

    public void broadcast(String message) {   //Method to broadcast a message to all agents with a randomized delay
        int delay = (int) (Math.random() * 1000);
        scheduler.schedule(() -> {    //Schedule the broadcast to occur after the random delay
            System.out.println("Broadcasting message: " + message);
            //Simulate a message reaching other agents
        }, delay, TimeUnit.MILLISECONDS);
    }
}
