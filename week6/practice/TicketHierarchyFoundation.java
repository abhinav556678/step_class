package week6.practice;

public class TicketHierarchyFoundation {

    public static class EventTicket {
        private final String attendeeId;
        private final double basePrice;
        private double balanceDue;

        public EventTicket(String attendeeId, double basePrice) {
            if (attendeeId == null || attendeeId.trim().isEmpty() || attendeeId.trim().length() < 4) {
                throw new IllegalArgumentException("Attendee ID must be non-null, non-blank, and at least 4 characters long.");
            }
            if (basePrice < 0) {
                throw new IllegalArgumentException("Base price cannot be negative.");
            }
            this.attendeeId = attendeeId.trim();
            this.basePrice = basePrice;
            this.balanceDue = basePrice;
        }

        public void pay(double amount) {
            if (amount > 0) {
                this.balanceDue -= amount;
            }
        }

        public double getBalanceDue() {
            return balanceDue;
        }

        public String getAttendeeId() {
            return attendeeId;
        }

        public double getBasePrice() {
            return basePrice;
        }
    }

    public static class WorkshopTicket extends EventTicket {
        private final String track;

        public WorkshopTicket(String attendeeId, double basePrice, String track) {
            super(attendeeId, basePrice);
            this.track = track;
        }

        public String getTrack() {
            return track;
        }
    }

    public static String registerBatch(String[] attendeeIds, double basePrice) {
        int registered = 0;
        int rejected = 0;

        if (attendeeIds != null) {
            for (String id : attendeeIds) {
                try {
                    new EventTicket(id, basePrice);
                    registered++;
                } catch (IllegalArgumentException e) {
                    rejected++;
                }
            }
        }

        return "Registered: " + registered + " | Rejected: " + rejected;
    }

    public static void main(String[] args) {
        // Test 1: Constructor rejection
        try {
            new EventTicket("ST1", 500);
            System.out.println("Construction unexpectedly succeeded");
        } catch (IllegalArgumentException e) {
            System.out.println("new EventTicket(\"ST1\", 500): construction rejected");
        }

        // Test 2: WorkshopTicket balance after payment
        WorkshopTicket w = new WorkshopTicket("STU2", 1200, "AI/ML");
        w.pay(500);
        System.out.println("WorkshopTicket balance due: " + w.getBalanceDue());

        // Test 3: Batch registration
        String[] batch = {"STU1", "ST1", "STU2", " ", "STU3"};
        System.out.println("registerBatch: " + registerBatch(batch, 500));
    }
}
