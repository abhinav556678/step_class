import java.util.*;

interface PricingPlan {
    double applyDiscount(double price);
}

class DayScholarPlan implements PricingPlan {
    public double applyDiscount(double price) { return price; }
}
class HostellerPlan implements PricingPlan {
    public double applyDiscount(double price) { return price * 0.9; }
}

class SmartCard {
    String id;
    PricingPlan plan;
    double balance = 0;
    List<Double> transactions = new ArrayList<>();
    Set<String> refunded = new HashSet<>();

    public SmartCard(String id, PricingPlan plan) {
        this.id = id;
        this.plan = plan;
    }

    public void topUp(double amount) {
        if (amount >= 100 && balance + amount <= 5000) {
            balance += amount;
            transactions.add(amount);
            System.out.printf("%s topped up with ₹%.2f. Balance: ₹%.2f.\n", id, amount, balance);
        }
    }

    public void buy(String item, double price) {
        double finalPrice = plan.applyDiscount(price);
        if (balance >= finalPrice) {
            balance -= finalPrice;
            transactions.add(-finalPrice);
            System.out.printf("%s purchased for ₹%.2f. Balance: ₹%.2f.\n", item, finalPrice, balance);
        } else {
            System.out.printf("Purchase failed: Insufficient balance (required ₹%.2f, available ₹%.2f).\n", finalPrice, balance);
        }
    }

    public void refund(String item, double price) {
        if (refunded.contains(item)) {
            System.out.println("Refund rejected: " + item + " has already been refunded.");
            return;
        }
        double finalPrice = plan.applyDiscount(price);
        balance += finalPrice;
        transactions.add(finalPrice);
        refunded.add(item);
        System.out.printf("Refund of ₹%.2f for %s processed. Balance: ₹%.2f.\n", finalPrice, item, balance);
    }

    public void miniStatement() {
        System.out.print("Mini-statement for " + id + ": ");
        double sum = 0;
        for (int i=0; i<transactions.size(); i++) {
            double t = transactions.get(i);
            sum += t;
            System.out.printf("%s%.2f", t > 0 ? "+" : "", t);
            if (i < transactions.size() - 1) System.out.print(", ");
        }
        System.out.printf(" = ₹%.2f.\n", sum);
    }
}

public class CampusCanteenSmartCard {
    public static void main(String[] args) {
        SmartCard card = new SmartCard("C-2045", new HostellerPlan());
        card.topUp(500);
        card.buy("Veg Thali", 120);
        card.buy("Cold Coffee", 60);
        card.buy("Items", 400); // Should fail
        card.refund("Veg Thali", 120);
        card.refund("Veg Thali", 120); // Already refunded
        card.miniStatement();
    }
}
