package subsystem;

import model.Customer;

public class NotificationService {

    public void sendConfirmation(Customer customer, String orderId) {
        send(customer, "Order " + orderId + " is confirmed");
    }

    public void sendFailure(Customer customer, String reason) {
        send(customer, "Order failed: " + reason);
    }

    public void sendCancellation(Customer customer, String orderId) {
        send(customer, "Order " + orderId + " is cancelled and refunded");
    }

    private void send(Customer customer, String text) {
        System.out.println("  [email to " + customer.getEmail() + "] " + text);
    }
}
