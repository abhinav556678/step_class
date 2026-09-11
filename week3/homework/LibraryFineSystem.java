package week3.homework;

public class LibraryFineSystem {

    public static class BookIssue {
        private String title;
        private String borrowerName;
        private int daysOverdue;

        public BookIssue(String title, String borrowerName, int daysOverdue) {
            this.title = title;
            this.borrowerName = borrowerName;
            this.daysOverdue = daysOverdue;
        }

        public String getTitle() {
            return title;
        }

        public String getBorrowerName() {
            return borrowerName;
        }

        public int getDaysOverdue() {
            return daysOverdue;
        }

        public double fineAmount() {
            return (daysOverdue > 0) ? daysOverdue * 5.0 : 0.0;
        }

        public boolean isSeverelyOverdue() {
            return daysOverdue > 14;
        }

        /*
         * JUSTIFICATION:
         * - 'fineAmount()' is an instance method because fine calculation is specific to an
         *   individual book issue (depends on its unique daysOverdue state).
         * - 'totalFineCollected(BookIssue[] issues)' is static because summing the total fine
         *   is a aggregate calculation over a collection of BookIssue objects and does not belong
         *   to any single book issue.
         */
        public static double totalFineCollected(BookIssue[] issues) {
            if (issues == null) {
                return 0.0;
            }
            double total = 0.0;
            for (BookIssue issue : issues) {
                if (issue != null) {
                    total += issue.fineAmount();
                }
            }
            return total;
        }
    }

    public static void main(String[] args) {
        BookIssue[] issues = {
            new BookIssue("Clean Code", "Alice", 18),
            new BookIssue("Effective Java", "Bob", 5),
            new BookIssue("Refactoring", "Charlie", 0),
            new BookIssue("DSA Handbook", "David", 21),
            new BookIssue("Design Patterns", "Eve", 9)
        };

        for (BookIssue issue : issues) {
            String status = issue.isSeverelyOverdue() ? "Severely overdue" : "OK";
            System.out.printf("%s - %d days - %s%n", issue.getTitle(), issue.getDaysOverdue(), status);
        }

        double totalFine = BookIssue.totalFineCollected(issues);
        System.out.printf("Total fine collected: Rs %.1f%n", totalFine);
    }
}
