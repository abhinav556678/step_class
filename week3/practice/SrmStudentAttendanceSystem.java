package week3.practice;

public class SrmStudentAttendanceSystem {

    public static class SrmStudent {
        private String name;
        private String regNo;
        private int attendance;

        public SrmStudent(String name, String regNo, int attendance) {
            this.name = name;
            this.regNo = regNo;
            this.attendance = attendance;
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

        public void addAttendanceUpdate(int newAttendance) {
            this.attendance = newAttendance;
        }

        public boolean isEligible() {
            return this.attendance >= 75;
        }

        /*
         * JUSTIFICATION:
         * - 'isEligible()' is an instance method because eligibility is a property of a specific
         *   individual student based on their own private attendance score.
         * - 'classAverage(SrmStudent[] students)' is static because the concept of a class average
         *   does not belong to any single student object; it is a batch/utility operation that computes
         *   an aggregate metric across a whole collection of student objects.
         */
        public static double classAverage(SrmStudent[] students) {
            if (students == null || students.length == 0) {
                return 0.0;
            }
            int sum = 0;
            for (SrmStudent s : students) {
                sum += s.getAttendance();
            }
            return (double) sum / students.length;
        }
    }

    public static void main(String[] args) {
        SrmStudent[] students = {
            new SrmStudent("Ravi", "RA01", 82),
            new SrmStudent("Anitha", "RA02", 68),
            new SrmStudent("Karthik", "RA03", 91),
            new SrmStudent("Meera", "RA04", 74),
            new SrmStudent("Suresh", "RA05", 60)
        };

        for (SrmStudent s : students) {
            String status = s.isEligible() ? "Eligible" : "Detained";
            System.out.printf("%s - %d%% - %s%n", s.getName(), s.getAttendance(), status);
        }

        double avg = SrmStudent.classAverage(students);
        System.out.printf("Class average: %.1f%%%n", avg);
    }
}
