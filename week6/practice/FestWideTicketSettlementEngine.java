package week6.practice;

public class FestWideTicketSettlementEngine {

    public static class EventTicket {
        private static int ticketsIssued = 0;

        public final String ticketId;
        private final double basePrice;
        private double balanceDue;

        public EventTicket(double basePrice) {
            if (basePrice < 0) {
                throw new IllegalArgumentException("Base price cannot be negative.");
            }
            ticketsIssued++;
            this.ticketId = "TCK-" + (1000 + ticketsIssued);
            this.basePrice = basePrice;
            this.balanceDue = basePrice;
        }

        public void pay(double amount) {
            if (amount > 0) {
                this.balanceDue -= amount;
            }
        }

        public void pay(double amount, String mode) {
            System.out.println("Paying via " + mode);
            pay(amount);
        }

        public double getBalanceDue() {
            return balanceDue;
        }

        public double getBasePrice() {
            return basePrice;
        }

        public static int getTicketsIssued() {
            return ticketsIssued;
        }

        public static boolean isValidPromoCode(String code) {
            if (code == null || code.length() != 5) {
                return false;
            }
            if (code.charAt(0) != 'F') {
                return false;
            }
            for (int i = 1; i <= 3; i++) {
                if (!Character.isDigit(code.charAt(i))) {
                    return false;
                }
            }
            return Character.isUpperCase(code.charAt(4));
        }
    }

    public static class GroupTicket extends EventTicket {
        private final int groupSize;

        public GroupTicket(double basePrice, int groupSize) {
            super(basePrice);
            if (groupSize <= 0) {
                throw new IllegalArgumentException("Group size must be a positive integer.");
            }
            this.groupSize = groupSize;
        }

        public int getGroupSize() {
            return groupSize;
        }
    }

    public static String processNightlySettlement(EventTicket[] tickets) {
        int processed = 0;
        int nullSkipped = 0;
        int group = 0;
        int individual = 0;

        if (tickets != null) {
            for (EventTicket ticket : tickets) {
                if (ticket == null) {
                    nullSkipped++;
                    continue;
                }
                processed++;
                if (ticket instanceof GroupTicket) {
                    group++;
                } else {
                    individual++;
                }
            }
        }

        return processed + " processed | " + nullSkipped + " null skipped | " + group + " group | " + individual + " individual";
    }

    public static void main(String[] args) {
        // Test 1: Ticket ID generation & issuance counter
        EventTicket t1 = new EventTicket(500);
        System.out.println("t1 ticketId: " + t1.ticketId);
        System.out.println("Tickets issued: " + EventTicket.getTicketsIssued());

        // Test 2: Promo code validation
        System.out.println("\nPromo code checks:");
        System.out.println("F123A: " + EventTicket.isValidPromoCode("F123A"));
        System.out.println("F12A: " + EventTicket.isValidPromoCode("F12A"));
        System.out.println("X123A: " + EventTicket.isValidPromoCode("X123A"));

        // Test 3: Overloaded pay methods
        System.out.println("\nPayment overload test:");
        t1.pay(200);
        t1.pay(200, "UPI");
        System.out.println("Remaining balance due: " + t1.getBalanceDue());

        // Test 4: Nightly settlement
        EventTicket[] settlementBatch = {
            new GroupTicket(2000, 5),
            null,
            new EventTicket(500)
        };
        System.out.println("\nNightly settlement result:");
        System.out.println(processNightlySettlement(settlementBatch));
    }
}
