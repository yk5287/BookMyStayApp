/**
 * Use Case 7: Add-On Service Selection
 *
 * Demonstrates how optional services can be attached to
 * an existing reservation using Map and List.
 *
 * Core booking and inventory remain unchanged.
 *
 * @author YourName
 * @version 7.0
 */

import java.util.*;

// Add-On Service (Domain Model)
class AddOnService {
    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() { return serviceName; }
    public double getCost() { return cost; }
}

// Add-On Service Manager
class AddOnServiceManager {

    // Map<ReservationID, List of Services>
    private Map<String, List<AddOnService>> serviceMap = new HashMap<>();

    // Add service to a reservation
    public void addService(String reservationId, AddOnService service) {

        serviceMap
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);

        System.out.println("Added service: " + service.getServiceName()
                + " to Reservation ID: " + reservationId);
    }

    // Display services for a reservation
    public void displayServices(String reservationId) {

        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services added for Reservation ID: " + reservationId);
            return;
        }

        System.out.println("\nServices for Reservation ID: " + reservationId);

        for (AddOnService s : services) {
            System.out.println("Service: " + s.getServiceName() +
                    " | Cost: ₹" + s.getCost());
        }
    }

    // Calculate total additional cost
    public double calculateTotalCost(String reservationId) {

        List<AddOnService> services = serviceMap.get(reservationId);

        double total = 0;

        if (services != null) {
            for (AddOnService s : services) {
                total += s.getCost();
            }
        }

        return total;
    }
}

// Main Class
public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: 7.0\n");

        // Step 1: Assume reservation already exists (from UC6)
        String reservationId = "SI101";

        // Step 2: Create Add-On Services
        AddOnService wifi = new AddOnService("WiFi", 500);
        AddOnService breakfast = new AddOnService("Breakfast", 300);
        AddOnService spa = new AddOnService("Spa", 1000);

        // Step 3: Service Manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Step 4: Add services to reservation
        manager.addService(reservationId, wifi);
        manager.addService(reservationId, breakfast);
        manager.addService(reservationId, spa);

        // Step 5: Display services
        manager.displayServices(reservationId);

        // Step 6: Calculate total cost
        double totalCost = manager.calculateTotalCost(reservationId);

        System.out.println("\nTotal Add-On Cost: ₹" + totalCost);
    }
}