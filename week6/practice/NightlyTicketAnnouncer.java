package week6.practice;

public class NightlyTicketAnnouncer {

    public static class EventTicket {
        private final double basePrice;
        private double balanceDue;

        public EventTicket(double basePrice) {
            this.basePrice = basePrice;
            this.balanceDue = basePrice;
        }

        public EventTicket(String attendeeId, double basePrice) {
            this.basePrice = basePrice;
            this.balanceDue = basePrice;
        }

        public double getBalanceDue() {
            return balanceDue;
        }

        public double getBasePrice() {
            return basePrice;
        }

        public String printTicket() {
            return "Standard | Balance: " + balanceDue;
        }
    }

    public static class WorkshopTicket extends EventTicket {
        private final String track;

        public WorkshopTicket(double basePrice, String track) {
            super(basePrice);
            this.track = track;
        }

        public WorkshopTicket(String attendeeId, double basePrice, String track) {
            super(attendeeId, basePrice);
            this.track = track;
        }

        public String getTrack() {
            return track;
        }

        @Override
        public String printTicket() {
            return "Workshop | Track: " + track + " | Balance: " + getBalanceDue();
        }
    }

    public static String batchPrint(EventTicket[] tickets) {
        StringBuilder report = new StringBuilder();
        if (tickets != null) {
            for (EventTicket ticket : tickets) {
                if (ticket != null) {
                    report.append(ticket.printTicket());
                    if (ticket instanceof WorkshopTicket) {
                        WorkshopTicket wt = (WorkshopTicket) ticket;
                        report.append(" [Track via downcast: ").append(wt.getTrack()).append("]");
                    }
                    report.append(" | ");
                }
            }
        }
        return report.toString();
    }

    public static void main(String[] args) {
        EventTicket[] batch = {
            new EventTicket(500),
            new WorkshopTicket(1200, "AI/ML")
        };

        String announcement = batchPrint(batch);
        System.out.println("Announcement Output:\n" + announcement);

        // Demonstrating safe downcasting vs unsafe cast
        EventTicket plain = new EventTicket(500);
        if (plain instanceof WorkshopTicket) {
            WorkshopTicket wt = (WorkshopTicket) plain;
            System.out.println("Downcast succeeded: " + wt.getTrack());
        } else {
            System.out.println("Downcast avoided safely: plain is not a WorkshopTicket");
        }

        try {
            // Unsafe direct downcast simulation
            WorkshopTicket bad = (WorkshopTicket) plain;
            System.out.println("Unexpected cast success: " + bad);
        } catch (ClassCastException e) {
            System.out.println("Caught expected ClassCastException on direct downcast without instanceof check.");
        }
    }
}
