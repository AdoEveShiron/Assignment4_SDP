package subsystem;

import exception.PaymentDeclinedException;
import model.Customer;

public class PaymentProcessor {

    public void charge(Customer customer, double amount) {
        if (customer.getBalance() < amount) {
            throw new PaymentDeclinedException("Payment declined: insufficient funds for " + customer.getName());
        }
        customer.setBalance(customer.getBalance() - amount);
    }

    public void refund(Customer customer, double amount) {
        customer.setBalance(customer.getBalance() + amount);
    }
}
