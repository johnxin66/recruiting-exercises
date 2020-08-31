import java.util.HashMap;

/**
 * This class represents a warehouse
 */
public class Warehouse {
    private String name;
    private HashMap<String, Integer> inventory;

    /**
     * Constructor
     * @param name warehouse name
     * @param inventory warehouse inventory
     */
    public Warehouse(String name, HashMap<String, Integer> inventory) {
        this.name = name;
        this.inventory = inventory;
    }

    /**
     * Getter for warehouse name
     * @return warehouse name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for warehouse inventory
     * @return warehouse inventory
     */
    public HashMap<String, Integer> getInventory() {
        return inventory;
    }

    /**
     * Setter for warehouse name
     * @param name new warehouse name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter for warehouse inventory
     * @param inventory new warehouse inventory
     */
    public void setInventory(HashMap<String, Integer> inventory) {
        this.inventory = inventory;
    }
}
