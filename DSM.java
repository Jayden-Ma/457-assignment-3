import java.util.concurrent.locks.ReentrantLock;

public class DSM implements Runnable {
    private final LocalMemory localMemory;
    private final BroadcastAgent broadcastAgent;
    private final ReentrantLock lock = new ReentrantLock();

    public DSM(LocalMemory memory, BroadcastAgent agent) {  //Constructor that initializies the local memory and broadcast agent for the DSM
        this.localMemory = memory;
        this.broadcastAgent = agent;
    }

    public int load(String var) {  //Method to load a value from local memory in a thread
        lock.lock(); //Acquire a lock to ensure no other thread is reading/writing
        try {
            return localMemory.load(var); //Retrieve the value of var from the local memory
        } finally {
            lock.unlock(); //release the lock
        }
    }

    public void store(String var, int value) {
        lock.lock(); //Acquire a lock to ensure no other thread is reading/writing
        try {
            localMemory.store(var, value); //Update local replica
            broadcastAgent.broadcast(var + "=" + value); //Broadcast to the others
        } finally {
            lock.unlock(); //release the lock
        }
    }

    @Override
    public void run() {
        System.out.println("DSM started for processor.");
    }

    public void handleBroadcast(String message) {   //Method to handle incoming broadcast messages from other DSMs
        String[] parts = message.split("=");
        String var = parts[0];
        int value = Integer.parseInt(parts[1]);
        localMemory.store(var, value); //Store the received value in the local replica
        System.out.println("DSM received broadcast: " + message);
    }
}
