package week6.practice;

public class ThreeShapesOfOneFamilyTree {

    public static class EventTicket {
        private final String attendeeId;
        private final double basePrice;
        private double balanceDue;

        public EventTicket(String attendeeId, double basePrice) {
            if (attendeeId == null || attendeeId.trim().isEmpty() || attendeeId.trim().length() < 4) {
                throw new IllegalArgumentException("Attendee ID must be non-null, non-blank, and at least 4 characters long.");
            }
            this.attendeeId = attendeeId.trim();
            this.basePrice = basePrice;
            this.balanceDue = basePrice;
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

        public String printTicket() {
            return "Standard Event Ticket | Balance Due: " + balanceDue;
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

        @Override
        public String printTicket() {
            return "Workshop Ticket | Track: " + track + " | Balance Due: " + getBalanceDue();
        }
    }

    public static class PremiumWorkshopTicket extends WorkshopTicket {
        private final double kitFee;

        public PremiumWorkshopTicket(String attendeeId, double basePrice, String track, double kitFee) {
            super(attendeeId, basePrice, track);
            this.kitFee = kitFee;
        }

        public double getKitFee() {
            return kitFee;
        }

        @Override
        public String printTicket() {
            return "Premium Workshop Ticket | Track: " + getTrack() + " | Kit Fee: " + kitFee + " | Balance Due: " + getBalanceDue();
        }
    }

    public static class HackathonTicket extends EventTicket {
        private final String teamName;

        public HackathonTicket(String attendeeId, double basePrice, String teamName) {
            super(attendeeId, basePrice);
            this.teamName = teamName;
        }

        public String getTeamName() {
            return teamName;
        }

        @Override
        public String printTicket() {
            return "Hackathon Ticket | Team: " + teamName + " | Balance Due: " + getBalanceDue();
        }
    }

    public static String classifyGeneration(EventTicket ticket) {
        if (ticket instanceof PremiumWorkshopTicket) {
            return "Multilevel descendant (3 generations deep)";
        } else if (ticket instanceof HackathonTicket) {
            return "Hierarchical sibling (independent branch)";
        }
        return "Standard branch";
    }

    public static double getTotalBalanceDue(EventTicket[] tickets) {
        double total = 0.0;
        if (tickets != null) {
            for (EventTicket ticket : tickets) {
                if (ticket != null) {
                    total += ticket.getBalanceDue();
                }
            }
        }
        return total;
    }

    public static void main(String[] args) {
        EventTicket standardTicket = new EventTicket("STU1", 500);
        WorkshopTicket workshopTicket = new WorkshopTicket("STU2", 1200, "AI/ML");
        PremiumWorkshopTicket premiumTicket = new PremiumWorkshopTicket("STU3", 2000, "Cloud Native", 300);
        HackathonTicket hackathonTicket = new HackathonTicket("STU4", 800, "Byte Force");

        System.out.println(standardTicket.printTicket());
        System.out.println(workshopTicket.printTicket());
        System.out.println(premiumTicket.printTicket());
        System.out.println(hackathonTicket.printTicket());

        System.out.println("\nClassifications:");
        System.out.println("premiumTicket: " + classifyGeneration(premiumTicket));
        System.out.println("hackathonTicket: " + classifyGeneration(hackathonTicket));

        EventTicket[] mixedField = { standardTicket, workshopTicket, premiumTicket, hackathonTicket };
        System.out.println("\nTotal balance due: " + getTotalBalanceDue(mixedField));
    }
}
