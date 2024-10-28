import java.util.concurrent.atomic.AtomicInteger;

public class Processor implements Runnable {
    private final DSM dsm; //DSM instance
    private final int id; //Unique ID
    private final int n; //Total number of processors in the system
    private final AtomicInteger turn = new AtomicInteger(); //track whose turn it is

    public Processor(DSM dsm, int id, int n) {  //Constructor that initializies DSM, processor ID, and the total number of processors
        this.dsm = dsm;
        this.id = id;
        this.n = n;
    }

    @Override
    public void run() {   //Method to repeatedly attempt entering the critical section
        for (int i = 0; i < 10; i++) {
            try {
                enterCriticalSection(); //Attempt to enter critical section
                System.out.println("Processor " + id + " is in the critical section.");
                Thread.sleep(500); //Simulate some work in the critical section
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); //Handle interrupt if it occurs
            } finally {
                exitCriticalSection();
            }
        }
    }

    public void enterCriticalSection() {
        for (int j = 0; j < n; j++) {
            if (j == id) continue; //Skip if it's the current processor's ID
            dsm.store("flag" + id, 1); //Set the flag indicating this processor's intent to enter the critical section
            turn.set(j);  //Atomically set turn

            while (dsm.load("flag" + j) == 1 && turn.get() == j) {
                //Busy-wait
            }
        }
    }

    public void exitCriticalSection() {
        dsm.store("flag" + id, 0); // Reset flag[id] to false
        System.out.println("Processor " + id + " exited the critical section.");
    }
}
