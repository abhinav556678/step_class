package week4.assignment;

public class GhostOrderValidator {

    public static class FoodOrder {
        private final String studentName;
        private final String dishName;
        private boolean isDelivered;

        // No usable no-argument constructor - only parameterized constructor that validates both fields
        public FoodOrder(String studentName, String dishName) {
            if (studentName == null || studentName.trim().isEmpty()) {
                throw new IllegalArgumentException("Invalid order: Student name cannot be null, blank, or whitespace-only.");
            }
            if (dishName == null || dishName.trim().isEmpty()) {
                throw new IllegalArgumentException("Invalid order: Dish name cannot be null, blank, or whitespace-only.");
            }

            this.studentName = studentName.trim();
            this.dishName = dishName.trim();
            this.isDelivered = false;
        }

        public void markDelivered() {
            if (this.isDelivered) {
                System.out.println("Alert: Order for " + studentName + " (" + dishName + ") was already marked delivered! Double-serving detected.");
            } else {
                this.isDelivered = true;
                System.out.println("Order for " + studentName + " (" + dishName + ") marked delivered successfully.");
            }
        }

        public boolean isDelivered() {
            return isDelivered;
        }

        public String getStudentName() {
            return studentName;
        }

        public String getDishName() {
            return dishName;
        }
    }

    public static void processBatch(String[][] rawOrders) {
        int validCount = 0;
        int rejectedCount = 0;

        if (rawOrders == null) {
            System.out.println("Valid: 0 | Rejected: 0");
            return;
        }

        for (String[] rawOrder : rawOrders) {
            if (rawOrder == null || rawOrder.length < 2) {
                rejectedCount++;
                continue;
            }

            try {
                new FoodOrder(rawOrder[0], rawOrder[1]);
                validCount++;
            } catch (IllegalArgumentException e) {
                rejectedCount++;
            }
        }

        System.out.println("Valid: " + validCount + " | Rejected: " + rejectedCount);
    }

    public static void main(String[] args) {
        String[][] rawOrders = {
            {"Ravi", "Paneer Butter Masala"},
            {"", "Chole Bhature"},
            {"Meera", " "},
            {"Divya", "Veg Biryani"}
        };

        System.out.println("Batch Processing Food Orders:");
        processBatch(rawOrders);

        System.out.println("\nTesting Delivery Status:");
        FoodOrder order = new FoodOrder("Ravi", "Paneer Butter Masala");
        order.markDelivered();
        order.markDelivered();
    }
}
