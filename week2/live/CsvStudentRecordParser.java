package week2.live;

public class CsvStudentRecordParser {

    public static void parseStudentRecord(String csvLine) {
        if (csvLine == null) {
            System.out.println("Invalid Record");
            return;
        }

        // Split by comma
        String[] parts = csvLine.split(",", -1);
        if (parts.length != 3) {
            System.out.println("Invalid Record");
            return;
        }

        String name = parts[0].trim();
        String rollNo = parts[1].trim();
        String dept = parts[2].trim();

        if (name.isEmpty() || rollNo.isEmpty() || dept.isEmpty()) {
            System.out.println("Invalid Record");
            return;
        }

        System.out.printf("Name: %s | Roll No: %s | Dept: %s%n", name, rollNo, dept);
    }

    public static void main(String[] args) {
        System.out.println("--- Test 1 ---");
        parseStudentRecord("Ananya Verma,RA2211003010123,CSE");

        System.out.println("\n--- Test 2 ---");
        parseStudentRecord("Ananya Verma,CSE");
    }
}
