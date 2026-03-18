/**
 * Use Case 10: Booking Cancellation & Inventory Rollback
 *
 * Demonstrates safe cancellation using Stack (LIFO),
 * inventory restoration, and validation.
 *
 * @author YourName
 * @version 10.0
 */

import java.util.*;

// Reservation (from UC6/UC8 simplified)
class Reservation {
    private String reservationId;
    private String roomType;
    private String roomId;

    public Reservation(String reservationId, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getReservationId() { return reservationId; }
    public String getRoomType() { return roomType; }
    public String getRoomId() { return roomId; }
}

// Inventory Service
class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single", 1);
        inventory.put("Double", 1);
    }

    public void increaseAvailability(String roomType) {
        inventory.put(roomType, inventory.get(roomType) + 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " -> " + inventory.get(type));
        }
    }
}

// Booking History (for validation)
class BookingHistory {

    private Map<String, Reservation> confirmedBookings = new HashMap<>();

    public void addReservation(Reservation r) {
        confirmedBookings.put(r.getReservationId(), r);
    }

    public Reservation getReservation(String id) {
        return confirmedBookings.get(id);
    }

    public void removeReservation(String id) {
        confirmedBookings.remove(id);
    }
}

// Cancellation Service
class CancellationService {

    private Stack<String> rollbackStack = new Stack<>();

    public void cancelBooking(String reservationId,
                              BookingHistory history,
                              RoomInventory inventory) {

        // Step 1: Validate
        Reservation r = history.getReservation(reservationId);

        if (r == null) {
            System.out.println("Cancellation Failed: Invalid Reservation ID");
            return;
        }

        // Step 2: Push room ID to stack (LIFO rollback)
        rollbackStack.push(r.getRoomId());

        // Step 3: Restore inventory
        inventory.increaseAvailability(r.getRoomType());

        // Step 4: Remove booking from history
        history.removeReservation(reservationId);

        System.out.println("Booking Cancelled: " + reservationId +
                " | Room Released: " + r.getRoomId());
    }

    public void showRollbackStack() {
        System.out.println("\nRollback Stack (Recent Releases): " + rollbackStack);
    }
}

// Main Class
public class UseCase10BookingCancellation {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: 10.0\n");

        // Setup
        RoomInventory inventory = new RoomInventory();
        BookingHistory history = new BookingHistory();
        CancellationService cancelService = new CancellationService();

        // Simulate confirmed bookings
        Reservation r1 = new Reservation("R101", "Single", "S1");
        Reservation r2 = new Reservation("R102", "Double", "D1");

        history.addReservation(r1);
        history.addReservation(r2);

        // Cancel booking
        cancelService.cancelBooking("R101", history, inventory);

        // Invalid cancellation
        cancelService.cancelBooking("R999", history, inventory);

        // Show results
        inventory.displayInventory();
        cancelService.showRollbackStack();
    }
}