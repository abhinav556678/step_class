package week6.assignment;

import java.util.Arrays;

public class LateWithdrawalPenaltyOverride {

    public static class RaceEntry {
        private final String bibNumber;
        private final double entryFee;
        private double balanceDue;
        private final double[] lateFeeHistory;
        private int lateFeeCount;

        public RaceEntry(String bibNumber, double entryFee) {
            if (bibNumber == null || bibNumber.trim().isEmpty() || bibNumber.trim().length() < 4) {
                throw new IllegalArgumentException("Bib number must be non-null, non-blank, and at least 4 characters long.");
            }
            this.bibNumber = bibNumber.trim();
            this.entryFee = Math.max(0, entryFee);
            this.balanceDue = this.entryFee;
            this.lateFeeHistory = new double[10];
            this.lateFeeCount = 0;
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

        protected void applyLateFee(double amount) {
            if (amount > 0) {
                this.balanceDue += amount;
                if (lateFeeCount < lateFeeHistory.length) {
                    lateFeeHistory[lateFeeCount++] = amount;
                }
            }
        }

        public double[] getLateFeeHistory() {
            return Arrays.copyOf(lateFeeHistory, lateFeeCount);
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

        @Override
        public void applyLateFee(double amount) {
            super.applyLateFee(amount * 2);
        }
    }

    public static void main(String[] args) {
        RunnerEntry r = new RunnerEntry("BIB2001", 80, "Open 10K");
        r.pay(30);
        r.applyLateFee(20);
        System.out.println("Balance due after doubled late fee: " + r.getBalanceDue());

        double[] history = r.getLateFeeHistory();
        System.out.println("History before tampering: " + Arrays.toString(history));
        if (history.length > 0) {
            history[0] = 999;
        }
        System.out.println("History after modifying caller's array: " + Arrays.toString(r.getLateFeeHistory()));
    }
}
