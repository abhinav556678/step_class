package week6.assignment;

public class ThreeShapesOfOneRaceFamily {

    public static class RaceEntry {
        private final String bibNumber;
        private final double entryFee;
        private double balanceDue;

        public RaceEntry(String bibNumber, double entryFee) {
            if (bibNumber == null || bibNumber.trim().isEmpty() || bibNumber.trim().length() < 4) {
                throw new IllegalArgumentException("Bib number must be non-null, non-blank, and at least 4 characters long.");
            }
            this.bibNumber = bibNumber.trim();
            this.entryFee = entryFee;
            this.balanceDue = entryFee;
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

        public String announce() {
            return "Race Entry | Bib: " + bibNumber + " | Balance: " + balanceDue;
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
        public String announce() {
            return "Runner Entry | Bib: " + getBibNumber() + " | Category: " + category + " | Balance: " + getBalanceDue();
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

        @Override
        public String announce() {
            return "Elite Runner | Bib: " + getBibNumber() + " | Category: " + getCategory() + " | Sponsor Bonus: " + sponsorBonus + " | Balance: " + getBalanceDue();
        }
    }

    public static class RelayTeamEntry extends RaceEntry {
        private final int teamSize;

        public RelayTeamEntry(String bibNumber, double entryFee, int teamSize) {
            super(bibNumber, entryFee);
            this.teamSize = teamSize;
        }

        public int getTeamSize() {
            return teamSize;
        }

        @Override
        public String announce() {
            return "Relay Team | Bib: " + getBibNumber() + " | Team Size: " + teamSize + " | Balance: " + getBalanceDue();
        }
    }

    public static String classifyGeneration(RaceEntry entry) {
        if (entry instanceof EliteRunnerEntry) {
            return "Multilevel descendant (3 generations deep)";
        } else if (entry instanceof RelayTeamEntry) {
            return "Hierarchical sibling (independent branch)";
        }
        return "Standard branch";
    }

    public static double getTotalBalanceDue(RaceEntry[] entries) {
        double total = 0.0;
        if (entries != null) {
            for (RaceEntry entry : entries) {
                if (entry != null) {
                    total += entry.getBalanceDue();
                }
            }
        }
        return total;
    }

    public static void main(String[] args) {
        RunnerEntry runnerEntry = new RunnerEntry("BIB2001", 80, "Open 10K");
        EliteRunnerEntry eliteEntry = new EliteRunnerEntry("BIB3001", 150, "Elite Full Marathon", 500);
        RelayTeamEntry relayEntry = new RelayTeamEntry("BIB4001", 300, 4);

        System.out.println(runnerEntry.announce());
        System.out.println(eliteEntry.announce());
        System.out.println(relayEntry.announce());

        System.out.println("\nClassifications:");
        System.out.println("eliteEntry: " + classifyGeneration(eliteEntry));
        System.out.println("relayEntry: " + classifyGeneration(relayEntry));

        RaceEntry[] entries = { runnerEntry, eliteEntry, relayEntry };
        System.out.println("\nTotal balance due: " + getTotalBalanceDue(entries));
    }
}
