import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * @author Yu Xin
 * This class generates the shipment itinerary sequentially.
 * I also know a concurrent, pipelined solution
 * that should work significantly better for shipping large order from a wide selection of warehouses.
 * It works as follows:
 * Suppose you have 5 warehouses and 5 items in an order;
 * Since by assumption, the warehouse closer to the beginning of the warehouse array is cheaper to ship,
 * we need to check the first warehouse before moving on to the second.
 *
 * 1. First, use processor 1 to check if warehouse 1 can fulfill item 1
 * 2. If not, then use processor 2 to check if warehouse 2 can fulfill item 1;
 *    at the same time, bring in item 2 to the pipeline--use processor 1 to check if warehouse 1 can fulfill item 2
 * 3. Items at the back of the pipeline can only move forward when the next processor becomes available;
 *    so when item 1 is on processor 3, item 2 has to stay in processor 2.
 * 4. The pipeline continues until all items find their stock in the warehouse,
 *    or throws an error if an item can't be fulfilled.
 * 5. If an item reaches the last processor but the number of warehouses is bigger than the number of processors,
 *    then an new item won't enter the pipeline;
 *    instead, the item on the last processor will move on to the first processor.
 *
 * This approach should be significantly faster for shipping large order from a wide selection of warehouses;
 * but since I don't have the time to hand-type such orders unless I'm hired,
 * and since having an untested concurrent system is same as having nothing,
 * I only implemented a draft of the concurrent code (and part of it hasn't been synchronized yet)
 *
 * The sequential, single-thread solution is complete and tested on some benchmark test cases.
 * Hire me and I'll finish & test the concurrent code ;)
 *
 * Jokes aside, I know Deliverr have much better systems and I do like to work with y'all
 */
public class InventoryAllocator {

//    private class AssignmentRunnable implements Runnable {
//        private HashMap<String, Integer> order;
//        private Warehouse warehouse;
//        private List<Shipment> shipments;
//
//        public AssignmentRunnable(HashMap<String, Integer> order, Warehouse warehouse, List<Shipment> shipments) {
//            this.order = order;
//            this.warehouse = warehouse;
//            this.shipments = shipments;
//        }
//
//        public void run() {
//            assignWarehouse(order, warehouse);
//        }
//
//        private void assignWarehouse(HashMap<String, Integer> order, Warehouse warehouse) {
//            HashMap<String, Integer> items = new HashMap<>();
//            for (String name:order.keySet()) {
//                if (order.get(name) != null && order.get(name) <= 0)
//                    throw new IllegalArgumentException(
//                            "Error, order amount has to be a positive integer"
//                    );
//                if (warehouse.getInventory().containsKey(name)) {
//                    if (warehouse.getInventory().get(name) > 0) {
//                        if (warehouse.getInventory().get(name) >= order.get(name)) {
//                            items.put(name, order.get(name));
//                            order.remove(name);
//                        } else {
//                            items.put(name, order.get(name) - warehouse.getInventory().get(name));
//                            order.put(name, order.get(name) - warehouse.getInventory().get(name));
//                        }
//                    } else throw new IllegalArgumentException(
//                            "Error, warehouse inventory amount has to be a positive integer"
//                    );
//                }
//            }
//            Shipment toShip = new Shipment(warehouse.getName(), items);
//            synchronized(this) {
//                shipments.add(toShip);
//            }
//        }
//    }

    public InventoryAllocator() {}

    /**
     * Helper function that assigns items in an order to a specific warehouse
     * @param order the order to be processed
     * @param warehouse the warehouse to ship from
     * @param shipments the total shipments
     */
    private void assignWarehouse(HashMap<String, Integer> order, Warehouse warehouse, List<Shipment> shipments) {
        HashMap<String, Integer> items = new HashMap<>();
        Iterator<String> orderIte = order.keySet().iterator();
        while (orderIte.hasNext()) {
            String name = orderIte.next();
            if (order.get(name) != null && order.get(name) <= 0)
                throw new IllegalArgumentException(
                        "Error, order amount has to be a positive integer."
                );
            if (warehouse.getInventory().containsKey(name)) {
                if (warehouse.getInventory().get(name) > 0) {
                    if (warehouse.getInventory().get(name) >= order.get(name)) {
                        items.put(name, order.get(name));
                        orderIte.remove();
                    } else {
                        items.put(name, warehouse.getInventory().get(name));
                        order.put(name, order.get(name) - warehouse.getInventory().get(name));
                    }
                } else throw new IllegalArgumentException(
                        "Error, warehouse inventory amount has to be a positive integer."
                );
            }
        }
        Shipment toShip = new Shipment(warehouse.getName(), items);
        shipments.add(toShip);
    }

    /**
     * Ship the order
     * @param order the order to be shipoed
     * @param warehouseList the list of warehouses to ship from, sorted by shipping cost in ascending order
     * @return the list of shipment itineraries
     */
    public List<Shipment> ship(HashMap<String, Integer> order, List<Warehouse> warehouseList) {
        if (order == null || order.size() == 0) throw new IllegalArgumentException("Error, order cannot be empty.");
        if (warehouseList == null || warehouseList.size() == 0)
            throw new IllegalArgumentException("Error, warehouse list cannot be empty.");

        List<Shipment> shipments = new ArrayList<>();
        for (Warehouse warehouse:warehouseList) {
            assignWarehouse(order, warehouse, shipments);
        }
        if (order.size() != 0) return new ArrayList<Shipment>();
        return shipments;

//        int processors = Runtime.getRuntime().availableProcessors();
//        Thread[] threads = new Thread[processors];
//        int i = 0;
//        for (Warehouse warehouse:warehouseList) {
//            Runnable r = new AssignmentRunnable(order, warehouse, shipments);
//            Thread newThread = new Thread(r);
//            while (threads[i] != null && threads[i].isAlive()) i = (i + 1) % processors;
//            threads[i] = newThread;
//            threads[i].start();
//            System.out.println("Thread created for " + warehouse.getName() + " at position " + i);
//        }
//        for (Thread thread : threads) {
//            try {
//                if (thread != null) {
//                    thread.join();
//                    System.out.println("Thread joined");
//                }
//            } catch (InterruptedException e) {
//                System.out.println("Error, thread interrupted.");
//            }
//        }
//        return shipments;
    }
}
