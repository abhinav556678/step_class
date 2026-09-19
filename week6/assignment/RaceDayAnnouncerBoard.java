package week6.assignment;

public class RaceDayAnnouncerBoard {

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

        public void setBalanceDue(double balanceDue) {
            this.balanceDue = balanceDue;
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

    public static String announceAll(RaceEntry[] entries) {
        StringBuilder sb = new StringBuilder();
        if (entries != null) {
            for (RaceEntry entry : entries) {
                if (entry != null) {
                    sb.append(entry.announce());
                    if (entry instanceof RelayTeamEntry) {
                        RelayTeamEntry rte = (RelayTeamEntry) entry;
                        sb.append(" [Team size via downcast: ").append(rte.getTeamSize()).append("]");
                    }
                    sb.append(" | ");
                }
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        RunnerEntry runnerEntry = new RunnerEntry("BIB2001", 80, "Open 10K");
        runnerEntry.setBalanceDue(90.0);

        RelayTeamEntry relayEntry = new RelayTeamEntry("BIB4001", 300, 4);

        RaceEntry[] fleet = { runnerEntry, relayEntry };
        System.out.println("Fleet announcement:\n" + announceAll(fleet));

        // Testing runtime type safety with instanceof check vs illegal downcast
        RaceEntry plain = new RaceEntry("BIB5001", 50);
        if (plain instanceof RelayTeamEntry) {
            RelayTeamEntry rte = (RelayTeamEntry) plain;
            System.out.println("Downcast succeeded: " + rte.getTeamSize());
        } else {
            System.out.println("Safely prevented illegal downcast: plain is not RelayTeamEntry");
        }

        try {
            RelayTeamEntry bad = (RelayTeamEntry) plain;
            System.out.println("Unexpected cast success: " + bad);
        } catch (ClassCastException e) {
            System.out.println("Caught expected ClassCastException on direct downcast without guard.");
        }
    }
}
