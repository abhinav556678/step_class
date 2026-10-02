import java.util.*;

class LineItem {
    String name;
    int qty;
    public LineItem(String name, int qty) {
        this.name = name;
        this.qty = qty;
    }
}

interface IPaymentMethod {
    boolean pay();
    String getName();
}

class CreditCardPayment implements IPaymentMethod {
    boolean success;
    public CreditCardPayment(boolean success) { this.success = success; }
    public boolean pay() { return success; }
    public String getName() { return "Credit Card"; }
}

class DigitalWalletPayment implements IPaymentMethod {
    boolean success;
    public DigitalWalletPayment(boolean success) { this.success = success; }
    public boolean pay() { return success; }
    public String getName() { return "Digital Wallet"; }
}

class Order {
    static int counter = 123;
    int orderId = counter++;
    List<LineItem> items = new ArrayList<>();
    
    public void addItem(String name, int qty) {
        items.add(new LineItem(name, qty));
    }

    public void place(IPaymentMethod payment) {
        if (items.isEmpty()) {
            System.out.println("Cannot place order: Order must contain at least one item.");
            return;
        }
        System.out.println("Order placed successfully.");
        if (payment.pay()) {
            System.out.println("Payment via " + payment.getName() + " successful. Order status: Paid. Notification: Order #" + orderId + " placed and paid.");
        } else {
            System.out.println("Payment via " + payment.getName() + " failed. Order status: Pending Payment. Notification: Order #" + orderId + " placed, awaiting payment.");
        }
    }
}

public class FoodOrderSystem {
    public static void main(String[] args) {
        System.out.println("Order created. Added Pizza (Qty 2), Soda (Qty 1).");
        Order order1 = new Order();
        Order order2 = new Order();
        order2.addItem("Pizza", 2);
        order2.addItem("Soda", 1);
        
        order1.place(new CreditCardPayment(true)); // empty cart
        order2.place(new CreditCardPayment(true));
        
        System.out.println("Order created. Added Burger (Qty 1).");
        Order order3 = new Order();
        order3.addItem("Burger", 1);
        order3.place(new DigitalWalletPayment(false));
    }
}
