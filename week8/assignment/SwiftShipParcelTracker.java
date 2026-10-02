import java.util.*;

interface ShippingType {
    double calcCharge(double kg);
    String getName();
}

class StandardShipping implements ShippingType {
    public double calcCharge(double kg) { return 40 + 10 * kg; }
    public String getName() { return "Standard"; }
}

class ExpressShipping implements ShippingType {
    public double calcCharge(double kg) { return 80 + 15 * kg; }
    public String getName() { return "Express"; }
}

interface NotificationChannel {
    void notify(String pId, String status);
    String getName();
}

class SmsChannel implements NotificationChannel {
    public void notify(String pId, String status) { System.out.println("[SMS] " + pId + " is now " + status + "."); }
    public String getName() { return "SMS"; }
}

class EmailChannel implements NotificationChannel {
    public void notify(String pId, String status) { System.out.println("[Email] " + pId + " is now " + status + "."); }
    public String getName() { return "Email"; }
}

class Parcel {
    String id;
    double kg;
    ShippingType type;
    String status = "BOOKED";
    List<NotificationChannel> channels = new ArrayList<>();
    
    private static final List<String> flow = Arrays.asList("BOOKED", "PICKED_UP", "IN_TRANSIT", "OUT_FOR_DELIVERY", "DELIVERED");

    public Parcel(String id, double kg, ShippingType type) {
        this.id = id;
        this.kg = kg;
        this.type = type;
        System.out.printf("Parcel %s booked (%s, %.0f kg). Charge: ₹%.2f.\n", id, type.getName(), kg, type.calcCharge(kg));
    }

    public void addChannel(NotificationChannel c) {
        channels.add(c);
        c.notify(id, status);
    }

    public void updateStatus(String newStatus) {
        int currentIndex = flow.indexOf(status);
        int newIndex = flow.indexOf(newStatus);
        
        if (newIndex == currentIndex + 1) {
            status = newStatus;
            for (NotificationChannel c : channels) c.notify(id, status);
        } else {
            System.out.println("Invalid transition: " + status + " -> " + newStatus + " is not allowed.");
        }
    }

    public void cancel() {
        if ("BOOKED".equals(status)) {
            System.out.println("Cancellation successful.");
        } else {
            System.out.println("Cancellation failed: " + id + " can be cancelled only while BOOKED.");
        }
    }
}

public class SwiftShipParcelTracker {
    public static void main(String[] args) {
        Parcel p = new Parcel("P101", 2, new ExpressShipping());
        p.addChannel(new SmsChannel());
        p.addChannel(new EmailChannel());
        
        p.updateStatus("PICKED_UP");
        p.cancel();
        p.updateStatus("IN_TRANSIT");
        p.updateStatus("DELIVERED"); // skipping OUT_FOR_DELIVERY
    }
}
