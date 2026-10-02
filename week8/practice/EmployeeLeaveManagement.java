abstract class Employee {
    String name;
    String type;
    public Employee(String name, String type) {
        this.name = name;
        this.type = type;
    }
}

class FullTimeEmployee extends Employee {
    public FullTimeEmployee(String name) { super(name, "Full-time"); }
}

class PartTimeEmployee extends Employee {
    public PartTimeEmployee(String name) { super(name, "Part-time"); }
}

class LeaveRequest {
    Employee employee;
    String startDate, endDate;
    String status = "Pending";

    public LeaveRequest(Employee employee, String startDate, String endDate) {
        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
        System.out.println("Leave request submitted by " + employee.name + " for " + startDate + " to " + endDate + ". Status: " + status + ".");
    }

    public void approve() {
        if (status.equals("Pending")) {
            status = "Approved";
            System.out.println("Leave request for " + employee.name + " approved. Status: " + status + ".");
        } else {
            System.out.println("Cannot change status: " + status + " request cannot revert to Pending.");
        }
    }

    public void setPending() {
        if (!status.equals("Pending")) {
            System.out.println("Cannot change status: " + status + " request cannot revert to Pending.");
        }
    }
}

public class EmployeeLeaveManagement {
    public static void main(String[] args) {
        FullTimeEmployee john = new FullTimeEmployee("John Doe");
        LeaveRequest req1 = new LeaveRequest(john, "2024-10-10", "2024-10-12");
        req1.approve();

        PartTimeEmployee jane = new PartTimeEmployee("Jane Smith");
        LeaveRequest req2 = new LeaveRequest(jane, "2024-11-01", "2024-11-05");
        
        req1.setPending();
    }
}
