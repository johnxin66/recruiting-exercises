import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test cases
 * You may use Intellij to open the project and run the test cases with junit
 * Make sure to add junit to the path before trying to run
 */
public class DeliverrNow {
    private static final int TIMEOUT = 500;
    private List<Warehouse> warehouses;
    private HashMap<String, Integer> order;
    private InventoryAllocator allocator;

    @Before
    public void setup() {
        warehouses = new ArrayList<Warehouse>();
        order = new HashMap<String, Integer>();
        allocator = new InventoryAllocator();
    }

    @Test(timeout = TIMEOUT)
    public void NotFoundTest() {
        HashMap<String, Integer> inventory = new HashMap<String, Integer>();
        inventory.put("apple", 1);

        warehouses.add(new Warehouse("owd", inventory));

        order.put("apple", 5);

        List<Shipment> output = allocator.ship(order, warehouses);

        assertEquals(0, output.size());
        System.out.println("NotFound test successful.");
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void BadOrderTest() {
        HashMap<String, Integer> inventory = new HashMap<String, Integer>();
        inventory.put("apple", 1);
        warehouses.add(new Warehouse("owd", inventory));
        allocator.ship(order, warehouses);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void BadInventoryTest() {
        HashMap<String, Integer> inventory = new HashMap<String, Integer>();
        order.put("apple", 1);
        allocator.ship(order, warehouses);
    }

    @Test(timeout = TIMEOUT)
    public void EmptyInventoryTest() {
        HashMap<String, Integer> inventory = new HashMap<String, Integer>();
        order.put("apple", 1);
        warehouses.add(new Warehouse("owd", inventory));
        List<Shipment> output = allocator.ship(order, warehouses);
        assertEquals(0, output.size());
        System.out.println("Empty inventory test successful.");
    }

    @Test(timeout = TIMEOUT)
    public void SimpleTest1() {
        HashMap<String, Integer> inventory = new HashMap<String, Integer>();
        inventory.put("apple", 1);

        warehouses.add(new Warehouse("owd", inventory));

        order.put("apple", 1);

        List<Shipment> output = allocator.ship(order, warehouses);

        assertEquals(1, output.size());
        assertEquals("owd", output.get(0).getWarehouseName());
        assertTrue(output.get(0).getShipment().containsKey("apple"));
        assertEquals(java.util.Optional.of(1), java.util.Optional.ofNullable(output.get(0).getShipment().get("apple")));
        System.out.println("SimpleTest 1 successful.");
    }

    @Test(timeout = TIMEOUT)
    public void SimpleTest2() {
        HashMap<String, Integer> inv1 = new HashMap<>();
        HashMap<String, Integer> inv2 = new HashMap<>();
        inv1.put("apple", 5);
        inv2.put("banana", 5);
        warehouses.add(new Warehouse("owd", inv1));
        warehouses.add(new Warehouse("dm", inv2));
        order.put("apple", 5);
        order.put("banana", 5);
        List<Shipment> output = allocator.ship(order, warehouses);
        assertEquals(2, output.size());
        assertEquals("owd", output.get(0).getWarehouseName());
        assertTrue(output.get(0).getShipment().containsKey("apple"));
        assertEquals(java.util.Optional.of(5), java.util.Optional.ofNullable(output.get(0).getShipment().get("apple")));
        assertEquals("dm", output.get(1).getWarehouseName());
        assertTrue(output.get(1).getShipment().containsKey("banana"));
        assertEquals(java.util.Optional.of(5), java.util.Optional.ofNullable(output.get(0).getShipment().get("apple")));
        System.out.println("SimpleTest 2 successful.");
    }

    @Test(timeout = TIMEOUT)
    public void MidTest() {
        HashMap<String, Integer> inv1 = new HashMap<>();
        HashMap<String, Integer> inv2 = new HashMap<>();
        inv1.put("apple", 5);
        inv1.put("banana", 5);
        inv1.put("cherry", 5);
        inv1.put("dragon fruit", 5);
        inv1.put("eggplant", 5);
        inv1.put("fig", 5);
        inv1.put("grapefruit", 5);
        inv1.put("hazelnut", 5);
        inv1.put("italian parsley", 5);
        inv1.put("jackfruit", 5);

        inv2.put("apple", 5);
        inv2.put("banana", 5);
        inv2.put("cherry", 5);
        inv2.put("dragon fruit", 5);
        inv2.put("eggplant", 5);
        inv2.put("fig", 5);
        inv2.put("grapefruit", 5);
        inv2.put("hazelnut", 5);
        inv2.put("italian parsley", 5);
        inv2.put("jackfruit", 5);

        order.put("apple", 10);
        order.put("banana", 9);
        order.put("cherry", 8);
        order.put("dragon fruit", 7);
        order.put("eggplant", 6);
        order.put("fig", 5);
        order.put("grapefruit", 4);
        order.put("hazelnut", 3);
        order.put("italian parsley", 2);
        order.put("jackfruit", 1);

        warehouses.add(new Warehouse("owd", inv1));
        warehouses.add(new Warehouse("dm", inv2));
        List<Shipment> output = allocator.ship(order, warehouses);

        assertEquals(2, output.size());
        assertEquals("owd", output.get(0).getWarehouseName());
        assertTrue(output.get(0).getShipment().containsKey("apple"));
        assertEquals(java.util.Optional.of(5), java.util.Optional.ofNullable(output.get(0).getShipment().get("apple")));
        assertEquals(java.util.Optional.of(5), java.util.Optional.ofNullable(output.get(0).getShipment().get("banana")));
        assertEquals(java.util.Optional.of(5), java.util.Optional.ofNullable(output.get(0).getShipment().get("cherry")));
        assertEquals(java.util.Optional.of(5), java.util.Optional.ofNullable(output.get(0).getShipment().get("dragon fruit")));
        assertEquals(java.util.Optional.of(5), java.util.Optional.ofNullable(output.get(0).getShipment().get("eggplant")));
        assertEquals(java.util.Optional.of(5), java.util.Optional.ofNullable(output.get(0).getShipment().get("fig")));
        assertEquals(java.util.Optional.of(4), java.util.Optional.ofNullable(output.get(0).getShipment().get("grapefruit")));
        assertEquals(java.util.Optional.of(3), java.util.Optional.ofNullable(output.get(0).getShipment().get("hazelnut")));
        assertEquals(java.util.Optional.of(2), java.util.Optional.ofNullable(output.get(0).getShipment().get("italian parsley")));
        assertFalse(output.get(1).getShipment().containsKey("jackfruit"));
        assertEquals("dm", output.get(1).getWarehouseName());
        assertTrue(output.get(1).getShipment().containsKey("apple"));
        assertEquals(java.util.Optional.of(5), java.util.Optional.ofNullable(output.get(1).getShipment().get("apple")));
        assertEquals(java.util.Optional.of(4), java.util.Optional.ofNullable(output.get(1).getShipment().get("banana")));
        assertEquals(java.util.Optional.of(3), java.util.Optional.ofNullable(output.get(1).getShipment().get("cherry")));
        assertEquals(java.util.Optional.of(2), java.util.Optional.ofNullable(output.get(1).getShipment().get("dragon fruit")));
        assertEquals(java.util.Optional.of(1), java.util.Optional.ofNullable(output.get(1).getShipment().get("eggplant")));
        assertFalse(output.get(1).getShipment().containsKey("fig"));
        System.out.println("MidTest successful.");
    }

    @Test(timeout = TIMEOUT)
    public void HardTest1() {
        HashMap<String, Integer> inv1 = new HashMap<>();
        HashMap<String, Integer> inv2 = new HashMap<>();
        HashMap<String, Integer> inv3 = new HashMap<>();
        HashMap<String, Integer> inv4 = new HashMap<>();
        inv1.put("apple", 5);
        inv1.put("banana", 5);
        inv1.put("cherry", 5);
        inv1.put("dragon fruit", 5);
        inv1.put("eggplant", 5);
        inv1.put("fig", 5);
        inv1.put("grapefruit", 5);
        inv1.put("hazelnut", 5);
        inv1.put("italian parsley", 5);
        inv1.put("jackfruit", 5);

        inv2.put("apple", 5);
        inv2.put("banana", 5);
        inv2.put("cherry", 5);
        inv2.put("dragon fruit", 5);
        inv2.put("eggplant", 5);
        inv2.put("fig", 5);
        inv2.put("grapefruit", 5);
        inv2.put("hazelnut", 5);
        inv2.put("italian parsley", 5);
        inv2.put("jackfruit", 5);

        inv3.put("apple", 5);
        inv3.put("banana", 5);
        inv3.put("cherry", 5);
        inv3.put("dragon fruit", 5);
        inv3.put("eggplant", 5);
        inv3.put("fig", 5);
        inv3.put("grapefruit", 5);
        inv3.put("hazelnut", 5);
        inv3.put("italian parsley", 5);
        inv3.put("jackfruit", 5);

        inv4.put("apple", 5);
        inv4.put("banana", 5);
        inv4.put("cherry", 5);
        inv4.put("dragon fruit", 5);
        inv4.put("eggplant", 5);
        inv4.put("fig", 5);
        inv4.put("grapefruit", 5);
        inv4.put("hazelnut", 5);
        inv4.put("italian parsley", 5);
        inv4.put("jackfruit", 5);

        order.put("apple", 10);
        order.put("banana", 16);
        order.put("cherry", 2);
        order.put("dragon fruit", 4);
        order.put("eggplant", 13);
        order.put("fig", 5);
        order.put("grapefruit", 18);
        order.put("hazelnut", 20);
        order.put("italian parsley", 11);
        order.put("jackfruit", 9);

        warehouses.add(new Warehouse("inv1", inv1));
        warehouses.add(new Warehouse("inv2", inv2));
        warehouses.add(new Warehouse("inv3", inv3));
        warehouses.add(new Warehouse("inv4", inv4));
        List<Shipment> output = allocator.ship(order, warehouses);

        assertEquals(4, output.size());
        assertEquals("inv1", output.get(0).getWarehouseName());
        assertTrue(output.get(0).getShipment().containsKey("apple"));
        assertEquals(java.util.Optional.of(5), java.util.Optional.ofNullable(output.get(0).getShipment().get("apple")));
        assertEquals(java.util.Optional.of(5), java.util.Optional.ofNullable(output.get(0).getShipment().get("banana")));
        assertEquals(java.util.Optional.of(2), java.util.Optional.ofNullable(output.get(0).getShipment().get("cherry")));
        assertEquals(java.util.Optional.of(4), java.util.Optional.ofNullable(output.get(0).getShipment().get("dragon fruit")));
        assertEquals(java.util.Optional.of(5), java.util.Optional.ofNullable(output.get(0).getShipment().get("eggplant")));
        assertEquals(java.util.Optional.of(5), java.util.Optional.ofNullable(output.get(0).getShipment().get("fig")));
        assertEquals(java.util.Optional.of(5), java.util.Optional.ofNullable(output.get(0).getShipment().get("grapefruit")));
        assertEquals(java.util.Optional.of(5), java.util.Optional.ofNullable(output.get(0).getShipment().get("hazelnut")));
        assertEquals(java.util.Optional.of(5), java.util.Optional.ofNullable(output.get(0).getShipment().get("italian parsley")));
        assertEquals(java.util.Optional.of(5), java.util.Optional.ofNullable(output.get(0).getShipment().get("jackfruit")));
        assertEquals("inv2", output.get(1).getWarehouseName());
        assertTrue(output.get(1).getShipment().containsKey("apple"));
        assertEquals(java.util.Optional.of(5), java.util.Optional.ofNullable(output.get(1).getShipment().get("apple")));
        assertEquals(java.util.Optional.of(5), java.util.Optional.ofNullable(output.get(1).getShipment().get("banana")));
        assertEquals(java.util.Optional.of(5), java.util.Optional.ofNullable(output.get(1).getShipment().get("eggplant")));
        assertEquals(java.util.Optional.of(5), java.util.Optional.ofNullable(output.get(1).getShipment().get("grapefruit")));
        assertEquals(java.util.Optional.of(5), java.util.Optional.ofNullable(output.get(1).getShipment().get("hazelnut")));
        assertEquals(java.util.Optional.of(5), java.util.Optional.ofNullable(output.get(1).getShipment().get("italian parsley")));
        assertEquals(java.util.Optional.of(4), java.util.Optional.ofNullable(output.get(1).getShipment().get("jackfruit")));
        assertFalse(output.get(1).getShipment().containsKey("cherry"));
        assertFalse(output.get(1).getShipment().containsKey("dragon fruit"));
        assertFalse(output.get(1).getShipment().containsKey("fig"));
        assertEquals("inv3", output.get(2).getWarehouseName());
        assertEquals(java.util.Optional.of(5), java.util.Optional.ofNullable(output.get(2).getShipment().get("banana")));
        assertEquals(java.util.Optional.of(3), java.util.Optional.ofNullable(output.get(2).getShipment().get("eggplant")));
        assertEquals(java.util.Optional.of(5), java.util.Optional.ofNullable(output.get(2).getShipment().get("grapefruit")));
        assertEquals(java.util.Optional.of(5), java.util.Optional.ofNullable(output.get(2).getShipment().get("hazelnut")));
        assertEquals(java.util.Optional.of(1), java.util.Optional.ofNullable(output.get(2).getShipment().get("italian parsley")));
        assertFalse(output.get(2).getShipment().containsKey("apple"));
        assertFalse(output.get(2).getShipment().containsKey("cherry"));
        assertFalse(output.get(2).getShipment().containsKey("dragon fruit"));
        assertFalse(output.get(2).getShipment().containsKey("fig"));
        assertFalse(output.get(2).getShipment().containsKey("jackfruit"));
        assertEquals("inv4", output.get(3).getWarehouseName());
        assertEquals(java.util.Optional.of(1), java.util.Optional.ofNullable(output.get(3).getShipment().get("banana")));
        assertEquals(java.util.Optional.of(3), java.util.Optional.ofNullable(output.get(3).getShipment().get("grapefruit")));
        assertEquals(java.util.Optional.of(5), java.util.Optional.ofNullable(output.get(3).getShipment().get("hazelnut")));
        assertFalse(output.get(3).getShipment().containsKey("apple"));
        assertFalse(output.get(3).getShipment().containsKey("cherry"));
        assertFalse(output.get(3).getShipment().containsKey("dragon fruit"));
        assertFalse(output.get(3).getShipment().containsKey("eggplant"));
        assertFalse(output.get(3).getShipment().containsKey("fig"));
        assertFalse(output.get(3).getShipment().containsKey("italian parsley"));
        assertFalse(output.get(3).getShipment().containsKey("jackfruit"));
        System.out.println("HardTest 1 successful.");
    }

    @Test(timeout = TIMEOUT)
    public void HardTest2() {
        HashMap<String, Integer> inv1 = new HashMap<>();
        HashMap<String, Integer> inv2 = new HashMap<>();
        HashMap<String, Integer> inv3 = new HashMap<>();
        HashMap<String, Integer> inv4 = new HashMap<>();
        inv1.put("banana", 5);
        inv1.put("cherry", 5);
        inv1.put("eggplant", 5);
        inv1.put("fig", 5);
        inv1.put("grapefruit", 5);
        inv1.put("hazelnut", 5);
        inv1.put("italian parsley", 5);
        inv1.put("jackfruit", 5);

        inv2.put("apple", 5);
        inv2.put("eggplant", 5);
        inv2.put("fig", 5);
        inv2.put("grapefruit", 5);
        inv2.put("hazelnut", 5);
        inv2.put("italian parsley", 5);

        inv3.put("apple", 5);
        inv3.put("fig", 5);
        inv3.put("grapefruit", 5);
        inv3.put("hazelnut", 5);
        inv3.put("jackfruit", 5);

        inv4.put("apple", 5);
        inv4.put("banana", 5);
        inv4.put("cherry", 5);
        inv4.put("dragon fruit", 5);
        inv4.put("eggplant", 5);
        inv4.put("fig", 5);
        inv4.put("grapefruit", 5);
        inv4.put("italian parsley", 5);
        inv4.put("jackfruit", 5);

        order.put("apple", 10);
        order.put("banana", 8);
        order.put("cherry", 2);
        order.put("dragon fruit", 4);
        order.put("eggplant", 13);
        order.put("fig", 5);
        order.put("grapefruit", 18);
        order.put("hazelnut", 15);
        order.put("italian parsley", 11);
        order.put("jackfruit", 9);

        warehouses.add(new Warehouse("inv1", inv1));
        warehouses.add(new Warehouse("inv2", inv2));
        warehouses.add(new Warehouse("inv3", inv3));
        warehouses.add(new Warehouse("inv4", inv4));
        List<Shipment> output = allocator.ship(order, warehouses);

        assertEquals(4, output.size());
        assertEquals("inv1", output.get(0).getWarehouseName());
        assertFalse(output.get(0).getShipment().containsKey("apple"));
        assertEquals(java.util.Optional.of(5), java.util.Optional.ofNullable(output.get(0).getShipment().get("banana")));
        assertEquals(java.util.Optional.of(2), java.util.Optional.ofNullable(output.get(0).getShipment().get("cherry")));
        assertEquals(java.util.Optional.of(5), java.util.Optional.ofNullable(output.get(0).getShipment().get("eggplant")));
        assertEquals(java.util.Optional.of(5), java.util.Optional.ofNullable(output.get(0).getShipment().get("fig")));
        assertEquals(java.util.Optional.of(5), java.util.Optional.ofNullable(output.get(0).getShipment().get("grapefruit")));
        assertEquals(java.util.Optional.of(5), java.util.Optional.ofNullable(output.get(0).getShipment().get("hazelnut")));
        assertEquals(java.util.Optional.of(5), java.util.Optional.ofNullable(output.get(0).getShipment().get("italian parsley")));
        assertEquals(java.util.Optional.of(5), java.util.Optional.ofNullable(output.get(0).getShipment().get("jackfruit")));
        assertFalse(output.get(0).getShipment().containsKey("dragon fruit"));
        assertEquals("inv2", output.get(1).getWarehouseName());
        assertEquals(java.util.Optional.of(5), java.util.Optional.ofNullable(output.get(1).getShipment().get("apple")));
        assertEquals(java.util.Optional.of(5), java.util.Optional.ofNullable(output.get(1).getShipment().get("eggplant")));
        assertEquals(java.util.Optional.of(5), java.util.Optional.ofNullable(output.get(1).getShipment().get("grapefruit")));
        assertEquals(java.util.Optional.of(5), java.util.Optional.ofNullable(output.get(1).getShipment().get("hazelnut")));
        assertEquals(java.util.Optional.of(5), java.util.Optional.ofNullable(output.get(1).getShipment().get("italian parsley")));
        assertFalse(output.get(1).getShipment().containsKey("banana"));
        assertFalse(output.get(1).getShipment().containsKey("cherry"));
        assertFalse(output.get(1).getShipment().containsKey("dragon fruit"));
        assertFalse(output.get(1).getShipment().containsKey("fig"));
        assertFalse(output.get(1).getShipment().containsKey("jackfruit"));
        assertEquals("inv3", output.get(2).getWarehouseName());
        assertEquals(java.util.Optional.of(5), java.util.Optional.ofNullable(output.get(2).getShipment().get("apple")));
        assertEquals(java.util.Optional.of(5), java.util.Optional.ofNullable(output.get(2).getShipment().get("grapefruit")));
        assertEquals(java.util.Optional.of(5), java.util.Optional.ofNullable(output.get(2).getShipment().get("hazelnut")));
        assertEquals(java.util.Optional.of(4), java.util.Optional.ofNullable(output.get(2).getShipment().get("jackfruit")));
        assertFalse(output.get(2).getShipment().containsKey("banana"));
        assertFalse(output.get(2).getShipment().containsKey("cherry"));
        assertFalse(output.get(2).getShipment().containsKey("dragon fruit"));
        assertFalse(output.get(2).getShipment().containsKey("eggplant"));
        assertFalse(output.get(2).getShipment().containsKey("fig"));
        assertFalse(output.get(2).getShipment().containsKey("italian parsley"));
        assertEquals("inv4", output.get(3).getWarehouseName());
        assertEquals(java.util.Optional.of(3), java.util.Optional.ofNullable(output.get(3).getShipment().get("banana")));
        assertEquals(java.util.Optional.of(4), java.util.Optional.ofNullable(output.get(3).getShipment().get("dragon fruit")));
        assertEquals(java.util.Optional.of(3), java.util.Optional.ofNullable(output.get(3).getShipment().get("eggplant")));
        assertEquals(java.util.Optional.of(3), java.util.Optional.ofNullable(output.get(3).getShipment().get("grapefruit")));
        assertEquals(java.util.Optional.of(1), java.util.Optional.ofNullable(output.get(3).getShipment().get("italian parsley")));
        assertFalse(output.get(3).getShipment().containsKey("apple"));
        assertFalse(output.get(3).getShipment().containsKey("cherry"));
        assertFalse(output.get(3).getShipment().containsKey("fig"));
        assertFalse(output.get(3).getShipment().containsKey("hazelnut"));
        assertFalse(output.get(3).getShipment().containsKey("jackfruit"));
        System.out.println("HardTest 2 successful.");
    }

    @Test(timeout = TIMEOUT)
    public void NotFoundHardTest() {
        HashMap<String, Integer> inv1 = new HashMap<>();
        HashMap<String, Integer> inv2 = new HashMap<>();
        HashMap<String, Integer> inv3 = new HashMap<>();
        HashMap<String, Integer> inv4 = new HashMap<>();
        inv1.put("banana", 5);
        inv1.put("cherry", 5);
        inv1.put("eggplant", 5);
        inv1.put("fig", 5);
        inv1.put("grapefruit", 5);
        inv1.put("hazelnut", 5);
        inv1.put("italian parsley", 5);
        inv1.put("jackfruit", 5);

        inv2.put("apple", 5);
        inv2.put("eggplant", 5);
        inv2.put("fig", 5);
        inv2.put("grapefruit", 5);
        inv2.put("hazelnut", 5);
        inv2.put("italian parsley", 5);

        inv3.put("apple", 5);
        inv3.put("fig", 5);
        inv3.put("hazelnut", 5);
        inv3.put("jackfruit", 5);

        inv4.put("apple", 5);
        inv4.put("banana", 5);
        inv4.put("cherry", 5);
        inv4.put("dragon fruit", 5);
        inv4.put("eggplant", 5);
        inv4.put("fig", 5);
        inv4.put("italian parsley", 5);
        inv4.put("jackfruit", 5);

        order.put("apple", 10);
        order.put("banana", 8);
        order.put("cherry", 2);
        order.put("dragon fruit", 4);
        order.put("eggplant", 13);
        order.put("fig", 5);
        order.put("grapefruit", 18);
        order.put("hazelnut", 15);
        order.put("italian parsley", 11);
        order.put("jackfruit", 9);

        warehouses.add(new Warehouse("inv1", inv1));
        warehouses.add(new Warehouse("inv2", inv2));
        warehouses.add(new Warehouse("inv3", inv3));
        warehouses.add(new Warehouse("inv4", inv4));
        List<Shipment> output = allocator.ship(order, warehouses);

        assertEquals(0, output.size());
        System.out.println("NotFoundHardTest successful.");
    }
}
