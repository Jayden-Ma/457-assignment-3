import java.util.HashMap;
import java.util.Map;

public class LocalMemory {
    private final Map<String, Integer> memory = new HashMap<>();

    public int load(String var) {  //Method to load a value from memory
        return memory.getOrDefault(var, 0); //Returns 0 if var is not found
    }

    public void store(String var, int value) {  //Method to store a value in memory
        memory.put(var, value); //Put the value into memory
        System.out.println("Stored " + value + " in " + var);
    }
}
