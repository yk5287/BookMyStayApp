/**
 * Use Case 9: Error Handling & Validation
 *
 * Demonstrates validation, custom exceptions, and fail-fast design.
 *
 * @author YourName
 * @version 9.0
 */

import java.util.*;

// Custom Exception
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Inventory (simplified)
class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single", 2);
        inventory.put("Double", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, -1);
    }

    public void reduceAvailability(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }
}

// Validator Class
class BookingValidator {

    public static void validate(String roomType, RoomInventory inventory)
            throws InvalidBookingException {

        // Validate room type
        if (!inventoryHasRoom(roomType, inventory)) {
            throw new InvalidBookingException("Invalid Room Type: " + roomType);
        }

        // Validate availability
        if (inventory.getAvailability(roomType) <= 0) {
            throw new InvalidBookingException("No rooms available for: " + roomType);
        }
    }

    private static boolean inventoryHasRoom(String roomType, RoomInventory inventory) {
        return inventory.getAvailability(roomType) != -1;
    }
}

// Booking Service
class BookingService {

    private RoomInventory inventory;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void bookRoom(String guestName, String roomType) {

        try {
            // Step 1: Validate (Fail-Fast)
            BookingValidator.validate(roomType, inventory);

            // Step 2: Process booking
            inventory.reduceAvailability(roomType);

            System.out.println("Booking confirmed for " + guestName +
                    " | Room Type: " + roomType);

        } catch (InvalidBookingException e) {
            // Graceful failure
            System.out.println("Booking Failed: " + e.getMessage());
        }
    }
}

// Main Class
public class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: 9.0\n");

        RoomInventory inventory = new RoomInventory();
        BookingService service = new BookingService(inventory);

        // Valid booking
        service.bookRoom("Alice", "Single");

        // Invalid room type
        service.bookRoom("Bob", "Suite");

        // Exhaust availability
        service.bookRoom("Charlie", "Double");
        service.bookRoom("David", "Double"); // Should fail
    }
}