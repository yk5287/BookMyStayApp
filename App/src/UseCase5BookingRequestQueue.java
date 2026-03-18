/**
 * Use Case 5: Booking Request Queue (First-Come-First-Served)
 *
 * Demonstrates how booking requests are handled fairly using a Queue.
 * Requests are stored and processed in arrival order (FIFO).
 *
 * @author YourName
 * @version 5.0
 */

import java.util.*;

// Reservation class (Represents a booking request)
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Booking Queue (FIFO)
class BookingQueue {
    private Queue<Reservation> queue;

    public BookingQueue() {
        queue = new LinkedList<>();
    }

    // Add booking request
    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Request added for " + reservation.getGuestName());
    }

    // View all requests (without removing)
    public void displayQueue() {
        System.out.println("\nCurrent Booking Requests (FIFO Order):\n");

        for (Reservation r : queue) {
            System.out.println("Guest: " + r.getGuestName());
            System.out.println("Room Type: " + r.getRoomType());
            System.out.println("--------------------------");
        }
    }
}

// Main Class
public class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: 5.0\n");

        // Step 1: Initialize Queue
        BookingQueue bookingQueue = new BookingQueue();

        // Step 2: Create Booking Requests
        Reservation r1 = new Reservation("Alice", "Single Room");
        Reservation r2 = new Reservation("Bob", "Double Room");
        Reservation r3 = new Reservation("Charlie", "Suite Room");

        // Step 3: Add to Queue (FIFO)
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        // Step 4: Display Queue (No processing yet)
        bookingQueue.displayQueue();
    }
}