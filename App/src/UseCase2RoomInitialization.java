/**
 * Use Case 2: Basic Room Types & Static Availability
 *
 * This class demonstrates object-oriented modeling using
 * abstraction, inheritance, and polymorphism.
 *
 * @author YourName
 * @version 2.0
 */

// Abstract Class
abstract class Room {
    private String type;
    private int beds;
    private double price;

    public Room(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    // Encapsulation (Getters)
    public String getType() { return type; }
    public int getBeds() { return beds; }
    public double getPrice() { return price; }

    // Abstract method
    public abstract void showDetails();
}

// Single Room
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 2000);
    }

    @Override
    public void showDetails() {
        System.out.println("Type: " + getType());
        System.out.println("Beds: " + getBeds());
        System.out.println("Price: ₹" + getPrice());
    }
}

// Double Room
class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 3500);
    }

    @Override
    public void showDetails() {
        System.out.println("Type: " + getType());
        System.out.println("Beds: " + getBeds());
        System.out.println("Price: ₹" + getPrice());
    }
}

// Suite Room
class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 5000);
    }

    @Override
    public void showDetails() {
        System.out.println("Type: " + getType());
        System.out.println("Beds: " + getBeds());
        System.out.println("Price: ₹" + getPrice());
    }
}

// Main Class
public class UseCase2RoomInitialization {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: 2.0\n");

        // Polymorphism
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static Availability (simple variables)
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        // Display details
        System.out.println("Single Room Details:");
        single.showDetails();
        System.out.println("Available: " + singleAvailable);
        System.out.println("--------------------------");

        System.out.println("Double Room Details:");
        doubleRoom.showDetails();
        System.out.println("Available: " + doubleAvailable);
        System.out.println("--------------------------");

        System.out.println("Suite Room Details:");
        suite.showDetails();
        System.out.println("Available: " + suiteAvailable);
        System.out.println("--------------------------");
    }
}