/**
 * Use Case 6: Reservation Confirmation & Room Allocation
 *
 * Demonstrates safe room allocation using Queue (FIFO),
 * HashMap, and Set to prevent double booking.
 *
 * @author YourName
 * @version 6.0
 */

import java.util.*;

// Reservation (from UC5)
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
}

// Booking Queue (FIFO)
class BookingQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNextRequest() {
        return queue.poll(); // dequeue
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

// Inventory Service (from UC3)
class InventoryService {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void reduceAvailability(String type) {
        int current = getAvailability(type);
        if (current > 0) {
            inventory.put(type, current - 1);
        }
    }
}

// Booking Service (Core Logic)
class BookingService {

    private InventoryService inventory;

    // Track allocated room IDs per type
    private Map<String, Set<String>> allocatedRooms = new HashMap<>();

    // Global set for uniqueness
    private Set<String> allAllocatedRoomIds = new HashSet<>();

    private int idCounter = 1;

    public BookingService(InventoryService inventory) {
        this.inventory = inventory;
    }

    public void processBookings(BookingQueue queue) {

        while (!queue.isEmpty()) {

            Reservation r = queue.getNextRequest();
            String type = r.getRoomType();

            System.out.println("\nProcessing request for " + r.getGuestName());

            // Check availability
            if (inventory.getAvailability(type) > 0) {

                // Generate unique Room ID
                String roomId = type.substring(0, 2).toUpperCase() + idCounter++;

                // Ensure uniqueness
                while (allAllocatedRoomIds.contains(roomId)) {
                    roomId = type.substring(0, 2).toUpperCase() + idCounter++;
                }

                // Add to global set
                allAllocatedRoomIds.add(roomId);

                // Add to type-based map
                allocatedRooms
                        .computeIfAbsent(type, k -> new HashSet<>())
                        .add(roomId);

                // Reduce inventory (atomic step)
                inventory.reduceAvailability(type);

                System.out.println("Booking Confirmed!");
                System.out.println("Guest: " + r.getGuestName());
                System.out.println("Room Type: " + type);
                System.out.println("Room ID: " + roomId);

            } else {
                System.out.println("Booking Failed! No rooms available for " + type);
            }
        }
    }
}

// Main Class
public class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: 6.0\n");

        // Step 1: Inventory Setup
        InventoryService inventory = new InventoryService();
        inventory.addRoomType("Single Room", 2);
        inventory.addRoomType("Double Room", 1);

        // Step 2: Booking Queue
        BookingQueue queue = new BookingQueue();
        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Single Room"));
        queue.addRequest(new Reservation("Charlie", "Single Room")); // should fail
        queue.addRequest(new Reservation("David", "Double Room"));

        // Step 3: Booking Service
        BookingService bookingService = new BookingService(inventory);

        // Step 4: Process Bookings
        bookingService.processBookings(queue);
    }
}