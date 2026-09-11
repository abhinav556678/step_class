package week3.practice;

public class SrmStudentStaticBoundary {

    /*
     * ------------------------------------------------------------
     * BROKEN VERSION (STATIC MISUSE)
     * ------------------------------------------------------------
     * In this broken class, marking every field static means there is only ONE copy
     * of name, regNo, and attendance shared across ALL instances.
     *
     * WHY MARKING EACH FIELD STATIC IS WRONG:
     * 1. 'name': Every student has an individual unique identity. Marking it static means
     *    creating a new student overwrites the name of all previously created students.
     * 2. 'regNo': Registration number must uniquely identify one specific student. A static
     *    regNo means all students share identical registration numbers.
     * 3. 'attendance': Attendance percentage reflects an individual student's record. A static
     *    attendance means one student's attendance update alters everyone else's attendance.
     */
    public static class BrokenSrmStudent {
        public static String name;
        public static String regNo;
        public static int attendance;

        public BrokenSrmStudent(String n, String r, int a) {
            name = n;
            regNo = r;
            attendance = a;
        }

        public void printName() {
            System.out.println(name);
        }
    }

    /*
     * ------------------------------------------------------------
     * FIXED VERSION (PROPER INSTANCE VS STATIC BOUNDARY)
     * ------------------------------------------------------------
     * - name, regNo, attendance -> Instance fields (unique per student object).
     * - university, admissionCount -> Static fields (shared at the class level).
     */
    public static class SrmStudent {
        // Static fields shared across all students
        private static String university = "SRM University";
        private static int admissionCount = 0;

        // Instance fields unique to each student
        private String name;
        private String regNo;
        private int attendance;

        public SrmStudent(String name, int attendance) {
            this.name = name;
            this.attendance = attendance;
            admissionCount++;
            // Deriving regNo automatically from admissionCount: e.g. RA231100301010 + admissionCount
            this.regNo = String.format("RA2311003010%02d", admissionCount + 10);
        }

        public static String getUniversity() {
            return university;
        }

        public String getName() {
            return name;
        }

        public String getRegNo() {
            return regNo;
        }

        public int getAttendance() {
            return attendance;
        }

        public void printIdCard() {
            System.out.printf("%s | %s%n", name, regNo);
        }

        public static void printTotalAdmissions() {
            System.out.printf("Students admitted so far: %d%n", admissionCount);
        }

        public static void resetCount() {
            admissionCount = 0;
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Broken Version (Demonstrating Data Overwrite) ---");
        BrokenSrmStudent s1 = new BrokenSrmStudent("Ravi", "RA101", 82);
        BrokenSrmStudent s2 = new BrokenSrmStudent("Meera", "RA102", 74);
        s1.printName();
        s2.printName();

        System.out.println("\n--- Fixed Version (Independent Instance Data) ---");
        SrmStudent.resetCount();
        SrmStudent fixed1 = new SrmStudent("Ravi", 82);
        SrmStudent fixed2 = new SrmStudent("Meera", 74);
        fixed1.printIdCard();
        fixed2.printIdCard();
        SrmStudent.printTotalAdmissions();
    }
}
