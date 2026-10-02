import java.util.*;

class Room {
    String name;
    double pricePerNight;
    public Room(String name, double pricePerNight) {
        this.name = name;
        this.pricePerNight = pricePerNight;
    }
}

class Reservation {
    Room room;
    String startDate;
    String endDate;
    int days;
    public Reservation(Room room, String startDate, String endDate, int days) {
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        this.days = days;
    }
}

class BookingManager {
    List<Reservation> reservations = new ArrayList<>();

    public void book(Room room, String startDate, String endDate, int days) {
        for (Reservation r : reservations) {
            if (r.room.equals(room) && overlap(r.startDate, r.endDate, startDate, endDate)) {
                System.out.println("Booking failed: " + room.name + " is not available for " + startDate + " to " + endDate + ".");
                return;
            }
        }
        Reservation res = new Reservation(room, startDate, endDate, days);
        reservations.add(res);
        System.out.printf("%s booked from %s to %s. Total price: $%.2f\n", room.name, startDate, endDate, room.pricePerNight * days);
    }

    public void cancel(Room room) {
        reservations.removeIf(r -> r.room.equals(room));
        System.out.println("Reservation for " + room.name + " cancelled successfully.");
    }

    private boolean overlap(String s1, String e1, String s2, String e2) {
        return !(e1.compareTo(s2) <= 0 || s1.compareTo(e2) >= 0);
    }
}

public class HotelBookingSystem {
    public static void main(String[] args) {
        BookingManager manager = new BookingManager();
        Room deluxe = new Room("Deluxe Room 101", 200.0);
        Room standard = new Room("Standard Room 205", 150.0);

        manager.book(deluxe, "2024-12-01", "2024-12-05", 4);
        manager.book(standard, "2024-12-03", "2024-12-07", 4);
        manager.book(deluxe, "2024-12-03", "2024-12-07", 4);
        manager.cancel(deluxe);
    }
}
