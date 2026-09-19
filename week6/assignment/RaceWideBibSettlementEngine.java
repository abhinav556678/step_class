package week6.assignment;

public class RaceWideBibSettlementEngine {

    public static class RaceEntry {
        private static int bibCounter = 0;

        public final String entryCode;
        private final String bibNumber;
        private final double entryFee;
        private double balanceDue;

        public RaceEntry(String bibNumber, double entryFee) {
            if (bibNumber == null || bibNumber.trim().isEmpty() || bibNumber.trim().length() < 4) {
                throw new IllegalArgumentException("Bib number must be non-null, non-blank, and at least 4 characters long.");
            }
            if (entryFee < 0) {
                throw new IllegalArgumentException("Entry fee cannot be negative.");
            }
            bibCounter++;
            this.entryCode = "ENTRY-" + (1000 + bibCounter);
            this.bibNumber = bibNumber.trim();
            this.entryFee = entryFee;
            this.balanceDue = entryFee;
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

        public String getBibNumber() {
            return bibNumber;
        }

        public double getEntryFee() {
            return entryFee;
        }

        public static int getBibCounter() {
            return bibCounter;
        }

        public static boolean isValidDiscountCode(String code) {
            if (code == null || code.length() != 5) {
                return false;
            }
            if (code.charAt(0) != 'M') {
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

    public static class RunnerEntry extends RaceEntry {
        private final String category;

        public RunnerEntry(String bibNumber, double entryFee, String category) {
            super(bibNumber, entryFee);
            this.category = category;
        }

        public String getCategory() {
            return category;
        }
    }

    public static class EliteRunnerEntry extends RunnerEntry {
        private final double sponsorBonus;

        public EliteRunnerEntry(String bibNumber, double entryFee, String category, double sponsorBonus) {
            super(bibNumber, entryFee, category);
            this.sponsorBonus = sponsorBonus;
        }

        public double getSponsorBonus() {
            return sponsorBonus;
        }
    }

    public static class RelayTeamEntry extends RaceEntry {
        private final int teamSize;

        public RelayTeamEntry(String bibNumber, double entryFee, int teamSize) {
            super(bibNumber, entryFee);
            if (teamSize <= 0) {
                throw new IllegalArgumentException("Team size must be a positive integer.");
            }
            this.teamSize = teamSize;
        }

        public int getTeamSize() {
            return teamSize;
        }
    }

    public static String settleNight(RaceEntry[] entries) {
        int processed = 0;
        int nullSkipped = 0;
        int relay = 0;
        int individual = 0;

        if (entries != null) {
            for (RaceEntry entry : entries) {
                if (entry == null) {
                    nullSkipped++;
                    continue;
                }
                processed++;
                if (entry instanceof RelayTeamEntry) {
                    relay++;
                } else {
                    individual++;
                }
            }
        }

        return processed + " processed | " + nullSkipped + " null skipped | " + relay + " relay | " + individual + " individual";
    }

    public static void main(String[] args) {
        // Test 1: Discount code validation
        System.out.println("Discount code checks:");
        System.out.println("M123A: " + RaceEntry.isValidDiscountCode("M123A"));
        System.out.println("M12A: " + RaceEntry.isValidDiscountCode("M12A"));
        System.out.println("X123A: " + RaceEntry.isValidDiscountCode("X123A"));

        // Test 2: Overloaded payment
        RunnerEntry r = new RunnerEntry("BIB2001", 80, "Open 10K");
        r.pay(10, "UPI");
        System.out.println("Balance due: " + r.getBalanceDue());

        // Test 3: Nightly settlement
        EliteRunnerEntry eliteEntry = new EliteRunnerEntry("BIB3001", 150, "Elite Full Marathon", 500);
        RelayTeamEntry relayEntry = new RelayTeamEntry("BIB4001", 300, 4);

        RaceEntry[] batch = { eliteEntry, null, relayEntry };
        System.out.println("\nNightly settlement result:");
        System.out.println(settleNight(batch));

        // Test 4: Bib counter total
        System.out.println("\nTotal bib counter: " + RaceEntry.getBibCounter());
    }
}
