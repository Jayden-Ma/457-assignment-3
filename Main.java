import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        int numProcessors = 10; //Number of processors in the simulation
        BroadcastSystem broadcastSystem = new BroadcastSystem();

        //Create a thread pool to manage processors and DSM instances
        ExecutorService executorService = Executors.newFixedThreadPool(numProcessors * 2);

        //Create the processors and DSMs
        for (int i = 0; i < numProcessors; i++) {
            LocalMemory localMemory = new LocalMemory();
            DSM dsm = new DSM(localMemory, new BroadcastAgent(broadcastSystem));
            Processor processor = new Processor(dsm, i, numProcessors);

            //Submit each DSM and Processor to the executor service
            executorService.submit(dsm);
            executorService.submit(processor);
        }

        //Shutdown all of the executors service after all tasks are completed
        executorService.shutdown();
    }
}
