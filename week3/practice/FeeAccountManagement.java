package week3.practice;

public class FeeAccountManagement {

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
                System.out.println("Payment rejected: Amount must be positive.");
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

        public void payInTwoInstallments(double amount) {
            if (amount > 0) {
                pay(amount / 2.0);
                pay(amount / 2.0);
            }
        }
    }

    public static class ScholarshipFeeAccount extends FeeAccount {
        private double scholarshipPercent; // 0–100

        public ScholarshipFeeAccount(String regNo, double totalFee, double amountPaid, double scholarshipPercent) {
            super(regNo, totalFee, amountPaid);
            this.scholarshipPercent = scholarshipPercent;
        }

        public double effectiveDue() {
            double rawDue = getDue();
            return rawDue - (rawDue * (scholarshipPercent / 100.0));
        }

        public double getScholarshipPercent() {
            return scholarshipPercent;
        }
    }

    public static void main(String[] args) {
        FeeAccount plain = new FeeAccount("REG101", 150000, 150000);
        HostelFeeAccount hostel = new HostelFeeAccount("REG102", 200000, 60000);
        ScholarshipFeeAccount scholarship = new ScholarshipFeeAccount("REG103", 180000, 0, 20.0);

        FeeAccount[] accounts = {plain, hostel, scholarship};

        for (FeeAccount acc : accounts) {
            if (acc instanceof ScholarshipFeeAccount) {
                ScholarshipFeeAccount s = (ScholarshipFeeAccount) acc;
                System.out.printf("Scholarship account effective due: Rs %.1f%n", s.effectiveDue());
            } else if (acc instanceof HostelFeeAccount) {
                System.out.printf("Hostel account due: Rs %.1f%n", acc.getDue());
            } else {
                System.out.printf("Plain account due: Rs %.1f%n", acc.getDue());
            }
        }
    }
}
