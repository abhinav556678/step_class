package week3.practice;

public class FeeAndHostelCapstone {

    // Reusing FeeAccount and HostelFeeAccount
    public static class FeeAccount {
        private String regNo;
        private double totalFee;
        private double amountPaid;

        public FeeAccount(String regNo, double totalFee, double amountPaid) {
            this.regNo = regNo;
            this.totalFee = totalFee;
            this.amountPaid = amountPaid;
        }

        public void pay(double amount) {
            if (amount > 0) {
                this.amountPaid += amount;
            } else {
                System.out.printf("Payment of Rs %.1f rejected (negative or zero amount)%n", amount);
            }
        }

        public double getDue() {
            return totalFee - amountPaid;
        }

        public String getRegNo() {
            return regNo;
        }
    }

    public static class HostelFeeAccount extends FeeAccount {
        public HostelFeeAccount(String regNo, double totalFee, double amountPaid) {
            super(regNo, totalFee, amountPaid);
        }
    }

    // Reusing HostelRoom
    public static class HostelRoom {
        private String roomNo;
        private int beds;
        private int occupied;

        public HostelRoom(String roomNo, int beds, int occupied) {
            this.roomNo = roomNo;
            this.beds = beds;
            this.occupied = occupied;
        }

        public String getRoomNo() {
            return roomNo;
        }

        public boolean allot() {
            if (occupied < beds) {
                occupied++;
                return true;
            }
            return false;
        }
    }

    // Composed Student class
    public static class SrmStudent {
        public static int totalStudents = 0;

        private String name;
        private String regNo;
        private HostelFeeAccount feeAccount;
        private HostelRoom room;

        public SrmStudent(String name, String regNo, HostelFeeAccount feeAccount, HostelRoom room) {
            this.name = name;
            this.regNo = regNo;
            this.feeAccount = feeAccount;
            this.room = room;
            totalStudents++;
        }

        public void setRoom(HostelRoom room) {
            this.room = room;
        }

        public String getName() {
            return name;
        }

        public String getRegNo() {
            return regNo;
        }

        public HostelRoom getRoom() {
            return room;
        }

        public HostelFeeAccount getFeeAccount() {
            return feeAccount;
        }

        public String fullStatus() {
            String roomDisplay = (room != null) ? room.getRoomNo() : "unallotted";
            return String.format("%s | Due: Rs %.1f | Room: %s", name, feeAccount.getDue(), roomDisplay);
        }
    }

    public static void main(String[] args) {
        HostelRoom room1 = new HostelRoom("C-214", 3, 2);
        HostelRoom room2 = new HostelRoom("C-507", 2, 1);

        HostelFeeAccount fee1 = new HostelFeeAccount("RA01", 200000, 60000);
        HostelFeeAccount fee2 = new HostelFeeAccount("RA02", 200000, 20000);
        HostelFeeAccount fee3 = new HostelFeeAccount("RA03", 200000, 0);

        // Process a mix of payments including a negative rejected payment
        fee1.pay(-5000); // rejected
        fee2.pay(0);     // rejected

        // Allot rooms to 2 students and leave the 3rd unallotted
        room1.allot();
        room2.allot();

        SrmStudent s1 = new SrmStudent("Ravi", "RA01", fee1, room1);
        SrmStudent s2 = new SrmStudent("Anitha", "RA02", fee2, room2);
        SrmStudent s3 = new SrmStudent("Karthik", "RA03", fee3, null); // unallotted

        System.out.println(s1.fullStatus());
        System.out.println(s2.fullStatus());
        System.out.println(s3.fullStatus());
        System.out.printf("Total students: %d%n", SrmStudent.totalStudents);
    }
}
