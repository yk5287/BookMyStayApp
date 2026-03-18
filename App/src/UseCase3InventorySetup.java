/**
 * Use Case 3: Centralized Room Inventory Management
 *
 * Demonstrates centralized state management using HashMap
 * to maintain room availability.
 *
 * @author YourName
 * @version 3.0
 */

import java.util.HashMap;
import java.util.Map;

// Inventory Class (Single Source of Truth)
class RoomInventory {

    private Map<String, Integer> inventory;

    // Constructor initializes inventory
    public RoomInventory() {
        inventory = new HashMap<>();
    }

    // Add or initialize room type
    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    // Get availability (O(1))
    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    // Update availability safely
    public void updateAvailability(String type, int newCount) {
        if (inventory.containsKey(type)) {
            inventory.put(type, newCount);
        } else {
            System.out.println("Room type does not exist: " + type);
        }
    }

    // Display full inventory
    public void displayInventory() {
        System.out.println("\nCurrent Room Inventory:\n");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println("Room Type: " + entry.getKey());
            System.out.println("Available: " + entry.getValue());
            System.out.println("--------------------------");
        }
    }
}

// Main Class
public class UseCase3InventorySetup {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: 3.0\n");

        // Step 1: Initialize Inventory
        RoomInventory inventory = new RoomInventory();

        // Step 2: Register room types
        inventory.addRoomType("Single Room", 5);
        inventory.addRoomType("Double Room", 3);
        inventory.addRoomType("Suite Room", 2);

        // Step 3: Display inventory
        inventory.displayInventory();

        // Step 4: Update availability
        System.out.println("\nUpdating availability...\n");
        inventory.updateAvailability("Single Room", 4);

        // Step 5: Display updated inventory
        inventory.displayInventory();
    }
}