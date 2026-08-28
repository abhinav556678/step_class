package week3.homework;

public class LibraryMembershipStaticBoundary {

    /*
     * ------------------------------------------------------------
     * BROKEN VERSION (STATIC MISUSE)
     * ------------------------------------------------------------
     * WHY MARKING EACH FIELD STATIC IS WRONG:
     * 1. 'name': Every library member is a distinct individual. Static 'name' causes
     *    the latest member's name to overwrite all existing members' names.
     * 2. 'memberId': Member ID must be unique per account. A static memberId forces
     *    all members to share one ID.
     * 3. 'booksIssued': Tracking how many books an individual has borrowed must be per-member.
     *    A static count overwrites everyone's borrow count.
     */
    public static class BrokenLibraryMember {
        public static String name;
        public static String memberId;
        public static int booksIssued;

        public BrokenLibraryMember(String n, String m, int b) {
            name = n;
            memberId = m;
            booksIssued = b;
        }

        public void printName() {
            System.out.println(name);
        }
    }

    /*
     * ------------------------------------------------------------
     * FIXED VERSION (CORRECT INSTANCE VS STATIC BOUNDARY)
     * ------------------------------------------------------------
     * - name, memberId, booksIssued -> Instance fields (per member)
     * - libraryName, memberCount -> Static fields (shared class-level)
     */
    public static class LibraryMember {
        private static String libraryName = "City Central Library";
        private static int memberCount = 0;

        private String name;
        private String memberId;
        private int booksIssued;

        public LibraryMember(String name, int booksIssued) {
            this.name = name;
            this.booksIssued = booksIssued;
            memberCount++;
            this.memberId = String.format("LM-%04d", 1000 + memberCount);
        }

        public static String getLibraryName() {
            return libraryName;
        }

        public String getName() {
            return name;
        }

        public String getMemberId() {
            return memberId;
        }

        public int getBooksIssued() {
            return booksIssued;
        }

        public void printMemberCard() {
            System.out.printf("%s | %s%n", name, memberId);
        }

        public static void printTotalMembers() {
            System.out.printf("Total members: %d%n", memberCount);
        }

        public static void resetCount() {
            memberCount = 0;
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Broken Version (Demonstrating Bug) ---");
        BrokenLibraryMember m1 = new BrokenLibraryMember("Aditi", "LM101", 2);
        BrokenLibraryMember m2 = new BrokenLibraryMember("Rohan", "LM102", 1);
        m1.printName();
        m2.printName();

        System.out.println("\n--- Fixed Version (Independent State) ---");
        LibraryMember.resetCount();
        LibraryMember fixed1 = new LibraryMember("Aditi", 2);
        LibraryMember fixed2 = new LibraryMember("Rohan", 1);
        fixed1.printMemberCard();
        fixed2.printMemberCard();
        LibraryMember.printTotalMembers();
    }
}
