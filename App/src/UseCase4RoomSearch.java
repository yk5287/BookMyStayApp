/**
 * Use Case 4: Room Search & Availability Check
 *
 * Demonstrates read-only access to centralized inventory
 * and separation of concerns using a Search Service.
 *
 * @author YourName
 * @version 4.0
 */

import java.util.*;

// Domain Model
class Room {
    private String type;
    private double price;
    private List<String> amenities;

    public Room(String type, double price, List<String> amenities) {
        this.type = type;
        this.price = price;
        this.amenities = amenities;
    }

    public String getType() { return type; }
    public double getPrice() { return price; }
    public List<String> getAmenities() { return amenities; }
}

// Inventory (State Holder - Read Only in this UC)
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoom(String type, int count) {
        inventory.put(type, count);
    }

    // Read-only method
    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public Set<String> getAllRoomTypes() {
        return inventory.keySet();
    }
}

// Search Service (NO state modification)
class SearchService {
    private RoomInventory inventory;
    private Map<String, Room> roomData;

    public SearchService(RoomInventory inventory, Map<String, Room> roomData) {
        this.inventory = inventory;
        this.roomData = roomData;
    }

    public void searchRooms() {
        System.out.println("\nAvailable Rooms:\n");

        for (String type : inventory.getAllRoomTypes()) {

            int available = inventory.getAvailability(type);

            // Validation Logic: Only available rooms
            if (available > 0) {

                Room room = roomData.get(type);

                // Defensive Programming
                if (room != null) {
                    System.out.println("Room Type: " + room.getType());
                    System.out.println("Price: ₹" + room.getPrice());
                    System.out.println("Amenities: " + room.getAmenities());
                    System.out.println("Available: " + available);
                    System.out.println("--------------------------");
                }
            }
        }
    }
}

// Main Class
public class UseCase4RoomSearch {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: 4.0");

        // Step 1: Initialize Inventory (from UC3)
        RoomInventory inventory = new RoomInventory();
        inventory.addRoom("Single Room", 5);
        inventory.addRoom("Double Room", 0); // Will be filtered
        inventory.addRoom("Suite Room", 2);

        // Step 2: Room Domain Data
        Map<String, Room> roomData = new HashMap<>();

        roomData.put("Single Room",
                new Room("Single Room", 2000,
                        Arrays.asList("WiFi", "TV")));

        roomData.put("Double Room",
                new Room("Double Room", 3500,
                        Arrays.asList("WiFi", "AC")));

        roomData.put("Suite Room",
                new Room("Suite Room", 5000,
                        Arrays.asList("WiFi", "AC", "Mini Bar")));

        // Step 3: Search Service
        SearchService searchService =
                new SearchService(inventory, roomData);

        // Step 4: Perform Read-Only Search
        searchService.searchRooms();
    }
}