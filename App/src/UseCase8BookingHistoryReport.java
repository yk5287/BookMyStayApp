/**
 * Use Case 8: Booking History & Reporting
 *
 * Stores confirmed reservations and generates reports.
 *
 * @author YourName
 * @version 8.0
 */

import java.util.*;

// Reservation (simplified from UC6)
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() { return reservationId; }
    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }

    public void showDetails() {
        System.out.println("Reservation ID: " + reservationId +
                " | Guest: " + guestName +
                " | Room Type: " + roomType);
    }
}

// Booking History (stores data)
class BookingHistory {

    // List preserves insertion order
    private List<Reservation> history = new ArrayList<>();

    // Add confirmed reservation
    public void addReservation(Reservation reservation) {
        history.add(reservation);
        System.out.println("Reservation stored in history: " + reservation.getReservationId());
    }

    // Retrieve all reservations
    public List<Reservation> getAllReservations() {
        return history;
    }

    // Display history
    public void displayHistory() {
        System.out.println("\n===== Booking History =====");

        if (history.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Reservation r : history) {
            r.showDetails();
        }
    }
}

// Reporting Service (read-only)
class BookingReportService {

    public void generateSummary(List<Reservation> reservations) {

        System.out.println("\n===== Booking Report =====");

        if (reservations.isEmpty()) {
            System.out.println("No data available for report.");
            return;
        }

        // Count bookings per room type
        Map<String, Integer> report = new HashMap<>();

        for (Reservation r : reservations) {
            report.put(r.getRoomType(),
                    report.getOrDefault(r.getRoomType(), 0) + 1);
        }

        // Display report
        for (String type : report.keySet()) {
            System.out.println("Room Type: " + type +
                    " | Total Bookings: " + report.get(type));
        }
    }
}

// Main Class
public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: 8.0\n");

        // Step 1: Create Booking History
        BookingHistory history = new BookingHistory();

        // Step 2: Simulate confirmed bookings
        Reservation r1 = new Reservation("R101", "Alice", "Single");
        Reservation r2 = new Reservation("R102", "Bob", "Double");
        Reservation r3 = new Reservation("R103", "Charlie", "Single");

        // Step 3: Store bookings
        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        // Step 4: Display history
        history.displayHistory();

        // Step 5: Generate report
        BookingReportService reportService = new BookingReportService();
        reportService.generateSummary(history.getAllReservations());
    }
}