package week6.assignment;

public class RaceEntryFoundation {

    public static class RaceEntry {
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
            this.bibNumber = bibNumber.trim();
            this.entryFee = entryFee;
            this.balanceDue = entryFee;
        }

        public void pay(double amount) {
            if (amount > 0) {
                this.balanceDue -= amount;
            }
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

    public static String registerBatch(String[] bibNumbers, double entryFee) {
        int registered = 0;
        int rejected = 0;

        if (bibNumbers != null) {
            for (String bib : bibNumbers) {
                try {
                    new RaceEntry(bib, entryFee);
                    registered++;
                } catch (IllegalArgumentException e) {
                    rejected++;
                }
            }
        }

        return "Registered: " + registered + " | Rejected: " + rejected;
    }

    public static void main(String[] args) {
        // Test 1: Constructor rejection for invalid bib
        try {
            new RaceEntry("B1", 50);
            System.out.println("Construction unexpectedly succeeded");
        } catch (IllegalArgumentException e) {
            System.out.println("new RaceEntry(\"B1\", 50): construction rejected");
        }

        // Test 2: RunnerEntry balance due
        RunnerEntry r = new RunnerEntry("BIB2001", 80, "Open 10K");
        r.pay(30);
        System.out.println("Balance due after payment: " + r.getBalanceDue());

        // Test 3: Batch bib registration
        String[] batch = {"BIB1", "B1", "BIB2"};
        System.out.println("registerBatch: " + registerBatch(batch, 80));
    }
}
