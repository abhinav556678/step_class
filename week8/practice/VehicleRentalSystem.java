abstract class Vehicle {
    String name;
    boolean isAvailable = true;
    public Vehicle(String name) { this.name = name; }
    abstract double calculateCharge(int days);
}

class StandardCar extends Vehicle {
    public StandardCar(String name) { super(name); }
    @Override
    double calculateCharge(int days) { return days * 50.0; }
}

class LuxuryCar extends Vehicle {
    public LuxuryCar(String name) { super(name); }
    @Override
    double calculateCharge(int days) { return days * 100.0; }
}

class Rental {
    Vehicle vehicle;
    int days;
    public Rental(Vehicle vehicle, int days) {
        this.vehicle = vehicle;
        this.days = days;
    }
}

class RentalService {
    public void rent(Vehicle vehicle, int days) {
        if (!vehicle.isAvailable) {
            System.out.println("Vehicle not available.");
            return;
        }
        vehicle.isAvailable = false;
        double charge = vehicle.calculateCharge(days);
        System.out.printf("%s rented for %d days. Total charge: $%.2f\n", vehicle.name, days, charge);
    }
    public void returnVehicle(Vehicle vehicle) {
        vehicle.isAvailable = true;
        System.out.println(vehicle.name + " returned. Now available.");
    }
}

public class VehicleRentalSystem {
    public static void main(String[] args) {
        RentalService service = new RentalService();
        LuxuryCar luxury = new LuxuryCar("Luxury Car A");
        StandardCar standard = new StandardCar("Standard Car B");
        
        service.rent(luxury, 3);
        service.rent(standard, 5);
        service.returnVehicle(luxury);
    }
}
