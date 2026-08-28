package week3.homework;

public class EmployeeHierarchySalary {

    public static class Employee {
        private String empId;
        private String empName;
        private double salary;

        public Employee(String empId, String empName, double salary) {
            this.empId = empId;
            this.empName = empName;
            this.salary = salary;
        }

        public double getSalary() {
            return salary;
        }

        public String getEmpId() {
            return empId;
        }

        public String getEmpName() {
            return empName;
        }
    }

    public static class ManagerEmployee extends Employee {
        private double teamBonus;

        public ManagerEmployee(String empId, String empName, double salary, double teamBonus) {
            super(empId, empName, salary);
            this.teamBonus = teamBonus;
        }

        public double effectiveSalary() {
            return getSalary() + teamBonus;
        }

        public double getTeamBonus() {
            return teamBonus;
        }
    }

    public static class InternEmployee extends Employee {
        private double stipendCap;

        public InternEmployee(String empId, String empName, double salary, double stipendCap) {
            super(empId, empName, salary);
            this.stipendCap = stipendCap;
        }

        public double effectiveSalary() {
            return Math.min(getSalary(), stipendCap);
        }

        public double getStipendCap() {
            return stipendCap;
        }
    }

    public static void main(String[] args) {
        Employee plain = new Employee("EMP101", "Alice", 40000);
        ManagerEmployee manager = new ManagerEmployee("EMP102", "Bob", 70000, 8000);
        InternEmployee intern = new InternEmployee("EMP103", "Charlie", 12000, 10000);

        Employee[] employees = {plain, manager, intern};

        for (Employee emp : employees) {
            if (emp instanceof ManagerEmployee) {
                ManagerEmployee m = (ManagerEmployee) emp;
                System.out.printf("Manager effective pay: Rs %.1f%n", m.effectiveSalary());
            } else if (emp instanceof InternEmployee) {
                InternEmployee in = (InternEmployee) emp;
                System.out.printf("Intern effective pay: Rs %.1f%n", in.effectiveSalary());
            } else {
                System.out.printf("Plain employee pay: Rs %.1f%n", emp.getSalary());
            }
        }
    }
}
