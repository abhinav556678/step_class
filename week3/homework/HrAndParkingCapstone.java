package week3.homework;

public class HrAndParkingCapstone {

    // Reusing Employee hierarchy
    public static class Employee {
        private String empId;
        private String empName;
        private double salary;

        public Employee(String empId, String empName, double salary) {
            this.empId = empId;
            this.empName = empName;
            this.salary = salary;
        }

        public double getEffectivePay() {
            return salary;
        }

        public String getEmpName() {
            return empName;
        }

        public String getEmpId() {
            return empId;
        }
    }

    public static class ManagerEmployee extends Employee {
        private double teamBonus;

        public ManagerEmployee(String empId, String empName, double salary, double teamBonus) {
            super(empId, empName, salary);
            this.teamBonus = teamBonus;
        }

        @Override
        public double getEffectivePay() {
            return super.getEffectivePay() + teamBonus;
        }
    }

    public static class InternEmployee extends Employee {
        private double stipendCap;

        public InternEmployee(String empId, String empName, double salary, double stipendCap) {
            super(empId, empName, salary);
            this.stipendCap = stipendCap;
        }

        @Override
        public double getEffectivePay() {
            return Math.min(super.getEffectivePay(), stipendCap);
        }
    }

    // Reusing ParkingSlot
    public static class ParkingSlot {
        private String slotNo;
        private int capacity;
        private int occupiedCount;

        public ParkingSlot(String slotNo, int capacity, int occupiedCount) {
            this.slotNo = slotNo;
            this.capacity = capacity;
            this.occupiedCount = occupiedCount;
        }

        public String getSlotNo() {
            return slotNo;
        }

        public boolean allot() {
            if (occupiedCount < capacity) {
                occupiedCount++;
                return true;
            }
            return false;
        }
    }

    // Composed Company Employee Record
    public static class CompanyEmployeeRecord {
        public static int totalRecords = 0;

        private String name;
        private String empId;
        private Employee employee;
        private ParkingSlot slot;

        public CompanyEmployeeRecord(String name, String empId, Employee employee, ParkingSlot slot) {
            this.name = name;
            this.empId = empId;
            this.employee = employee;
            this.slot = slot;
            totalRecords++;
        }

        public String getEmpId() {
            return empId;
        }

        public String getName() {
            return name;
        }

        public Employee getEmployee() {
            return employee;
        }

        public ParkingSlot getSlot() {
            return slot;
        }

        public String fullProfile() {
            String slotDisplay = (slot != null) ? slot.getSlotNo() : "no parking assigned";
            return String.format("%s | Pay: Rs %.1f | Slot: %s", name, employee.getEffectivePay(), slotDisplay);
        }
    }

    public static void main(String[] args) {
        ManagerEmployee manager = new ManagerEmployee("EMP101", "Divya", 70000, 8000);
        Employee plain = new Employee("EMP102", "Karan", 40000);
        InternEmployee intern = new InternEmployee("EMP103", "Meera", 12000, 10000);

        ParkingSlot slot1 = new ParkingSlot("A1", 4, 3);
        ParkingSlot slot2 = new ParkingSlot("A2", 5, 4);

        slot1.allot();
        slot2.allot();

        CompanyEmployeeRecord rec1 = new CompanyEmployeeRecord("Divya", "EMP101", manager, slot1);
        CompanyEmployeeRecord rec2 = new CompanyEmployeeRecord("Karan", "EMP102", plain, slot2);
        CompanyEmployeeRecord rec3 = new CompanyEmployeeRecord("Meera", "EMP103", intern, null); // no parking

        System.out.println(rec1.fullProfile());
        System.out.println(rec2.fullProfile());
        System.out.println(rec3.fullProfile());
        System.out.printf("Total records: %d%n", CompanyEmployeeRecord.totalRecords);
    }
}
