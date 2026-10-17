package model;

public class Order {
    private final String id;
    private final Cart cart;
    private final Customer customer;
    private final String trackingNumber;
    private boolean cancelled;

    public Order(String id, Cart cart, Customer customer, String trackingNumber) {
        this.id = id;
        this.cart = cart;
        this.customer = customer;
        this.trackingNumber = trackingNumber;
    }

    public String getId() {
        return id;
    }

    public Cart getCart() {
        return cart;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void cancel() {
        cancelled = true;
    }
}
