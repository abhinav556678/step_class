package week4.practice;

public class NightlyFleetReconciliationEngine {

    public static class BusTicketAccount {
        private static double defaultBaseFloorPercent;

        // Static block for one-time class-level initialization
        static {
            defaultBaseFloorPercent = 1.0;
        }

        private final String bookingId;
        private final double ticketFare;

        // Full constructor
        public BusTicketAccount(String bookingId, double ticketFare) {
            if (bookingId == null || bookingId.trim().isEmpty()) {
                throw new IllegalArgumentException("Booking ID cannot be null or empty.");
            }
            if (ticketFare < 0) {
                throw new IllegalArgumentException("Ticket fare cannot be negative: " + ticketFare);
            }
            this.bookingId = bookingId.trim();
            this.ticketFare = ticketFare;
        }

        // Provisional constructor chaining to full constructor
        public BusTicketAccount(String bookingId) {
            this(bookingId, 0.0);
        }

        // Final penalty calculation adhering to Problem 4 tiered rule
        public final double calculatePenalty(int minutesLate) {
            if (minutesLate < 0) {
                throw new IllegalArgumentException("Minutes late cannot be negative: " + minutesLate);
            }
            if (minutesLate == 0) {
                return 0.0;
            }

            int b1 = Math.min(minutesLate, 5);
            int b2 = Math.max(0, Math.min(minutesLate - 5, 10));
            int b3 = Math.max(0, minutesLate - 15);

            double tieredRate = (b1 * 0.005) + (b2 * 0.010) + (b3 * 0.020);
            double tieredPenalty = tieredRate * ticketFare;
            double floorPenalty = (defaultBaseFloorPercent / 100.0) * ticketFare;

            return Math.max(tieredPenalty, floorPenalty);
        }

        public String getBookingId() {
            return bookingId;
        }

        public double getTicketFare() {
            return ticketFare;
        }
    }

    // Sleeper coach account subclass
    public static class SleeperAccount extends BusTicketAccount {
        private static final double SLEEPER_SURCHARGE_RATE = 0.10; // 10% premium sleeper amenity surcharge

        public SleeperAccount(String bookingId, double ticketFare) {
            super(bookingId, ticketFare);
        }

        public SleeperAccount(String bookingId) {
            super(bookingId);
        }

        public double getSleeperSurcharge() {
            return getTicketFare() * SLEEPER_SURCHARGE_RATE;
        }
    }

    // Alias Sleeper to SleeperAccount for convenience
    public static class Sleeper extends SleeperAccount {
        public Sleeper(String bookingId, double ticketFare) {
            super(bookingId, ticketFare);
        }

        public Sleeper(String bookingId) {
            super(bookingId);
        }
    }

    public static void processAccount(BusTicketAccount account, double amount, int minutesLate) {
        if (account == null) {
            System.out.println("Cannot process null account entry.");
            return;
        }

        double penalty = account.calculatePenalty(minutesLate);

        if (account instanceof SleeperAccount) {
            SleeperAccount sleeper = (SleeperAccount) account;
            double surcharge = sleeper.getSleeperSurcharge();
            System.out.printf("Processed Sleeper Account [%s]: Base Fare Rs %.2f, Amount Paid Rs %.2f, Late %d min (Penalty Rs %.2f, Sleeper Surcharge Rs %.2f)%n",
                    account.getBookingId(), account.getTicketFare(), amount, minutesLate, penalty, surcharge);
        } else {
            System.out.printf("Processed Regular Account [%s]: Base Fare Rs %.2f, Amount Paid Rs %.2f, Late %d min (Penalty Rs %.2f)%n",
                    account.getBookingId(), account.getTicketFare(), amount, minutesLate, penalty);
        }
    }

    public static void processBatch(BusTicketAccount[] accounts, double[] amounts, int[] minutesLateArray) {
        // Defensive validation: length mismatch corrupts the reconciliation by pairing amounts to the wrong passengers
        if (accounts == null || amounts == null || minutesLateArray == null) {
            throw new IllegalArgumentException("Batch inputs cannot be null.");
        }
        if (accounts.length != amounts.length || accounts.length != minutesLateArray.length) {
            throw new IllegalArgumentException("Batch rejected: Input array lengths do not match (accounts: "
                    + accounts.length + ", amounts: " + amounts.length + ", minutesLate: " + minutesLateArray.length + ").");
        }

        int processedCount = 0;
        int nullSkippedCount = 0;
        int sleeperCount = 0;
        int regularCount = 0;
        double grandTotalPenalties = 0.0;

        for (int i = 0; i < accounts.length; i++) {
            BusTicketAccount account = accounts[i];
            double amount = amounts[i];
            int minutesLate = minutesLateArray[i];

            if (account == null) {
                nullSkippedCount++;
                continue;
            }

            processedCount++;
            double penalty = account.calculatePenalty(minutesLate);
            grandTotalPenalties += penalty;

            // Dispatch using instanceof
            if (account instanceof SleeperAccount) {
                sleeperCount++;
            } else {
                regularCount++;
            }

            processAccount(account, amount, minutesLate);
        }

        System.out.printf("%n%d processed | %d null skipped | %d sleeper | %d regular | grand total penalties = Rs %.2f%n",
                processedCount, nullSkippedCount, sleeperCount, regularCount, grandTotalPenalties);
    }

    public static void main(String[] args) {
        BusTicketAccount[] accounts = {
            new Sleeper("BK001", 2000),
            null,
            new BusTicketAccount("BK002", 1200)
        };
        double[] amounts = {1200, 900, 700};
        int[] minutesLateArray = {10, 5, 0};

        System.out.println("Starting Nightly Fleet Reconciliation:");
        processBatch(accounts, amounts, minutesLateArray);
    }
}
