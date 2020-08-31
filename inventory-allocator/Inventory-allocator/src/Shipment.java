import java.util.HashMap;

/**
 * This class represents a shipment itinerary
 * which contains its warehouse origin and items being shipped.
 * The destination isn't specified by the challenge so there's no destination field.
 */
public class Shipment {
    private String warehouseName;
    private HashMap<String, Integer> shipment;

    /**
     * Constructor
     * @param warehouseName name of the origin warehouse
     * @param items items being shipped
     */
    public Shipment(String warehouseName, HashMap<String, Integer> items) {
        this.warehouseName = warehouseName;
        shipment = items;
    }

    /**
     * Chained constructor that initializes the shipment field as a new HashMap
     * @param warehouseName name of the origin warehouse
     */
    public Shipment(String warehouseName) {
        this(warehouseName, new HashMap<String, Integer>());
    }

    /**
     * Getter for the HashMap of items being shipped
     * @return items being shipped
     */
    public HashMap<String, Integer> getShipment() {
        return shipment;
    }

    /**
     * Setter for the shipped items
     * @param shipment shipped items
     */
    public void setShipment(HashMap<String, Integer> shipment) {
        this.shipment = shipment;
    }

    /**
     * Getter for the name of the origin warehouse
     * @return name of the origin warehouse
     */
    public String getWarehouseName() {
        return warehouseName;
    }

    /**
     * Setter for the name of the origin warehouse
     * @param warehouseName name of the origin warehouse
     */
    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }
}
