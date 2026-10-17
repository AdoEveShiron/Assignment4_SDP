package facade;

import exception.OrderException;
import exception.OutOfStockException;
import exception.PaymentDeclinedException;
import exception.ShippingException;
import model.Cart;
import model.Customer;
import model.Order;
import subsystem.InventoryService;
import subsystem.NotificationService;
import subsystem.PaymentProcessor;
import subsystem.ShippingService;

import java.util.HashMap;
import java.util.Map;

public class OrderFacade {
    private final InventoryService inventory = new InventoryService();
    private final PaymentProcessor payment = new PaymentProcessor();
    private final ShippingService shipping = new ShippingService();
    private final NotificationService notifications = new NotificationService();
    private final Map<String, Order> orders = new HashMap<>();
    private int orderCounter = 0;

    public void addProductToStore(String product, int quantity) {
        inventory.addStock(product, quantity);
    }

    public int getProductStock(String product) {
        return inventory.getStock(product);
    }

    public String placeOrder(Cart cart, Customer customer) {
        boolean reserved = false;
        boolean charged = false;
        try {
            inventory.reserve(cart);
            reserved = true;
            payment.charge(customer, cart.getTotal());
            charged = true;
            String trackingNumber = shipping.createShipment(customer);
            String orderId = createOrderId();
            orders.put(orderId, new Order(orderId, cart, customer, trackingNumber));
            notifications.sendConfirmation(customer, orderId);
            return orderId;
        } catch (OutOfStockException | PaymentDeclinedException | ShippingException e) {
            if (charged) {
                payment.refund(customer, cart.getTotal());
            }
            if (reserved) {
                inventory.release(cart);
            }
            notifications.sendFailure(customer, e.getMessage());
            throw new OrderException(e.getMessage());
        }
    }

    public void cancelOrder(String orderId) {
        Order order = orders.get(orderId);
        if (order == null) {
            throw new OrderException("Order not found: " + orderId);
        }
        if (order.isCancelled()) {
            throw new OrderException("Order already cancelled: " + orderId);
        }
        shipping.cancelShipment(order.getTrackingNumber());
        payment.refund(order.getCustomer(), order.getCart().getTotal());
        inventory.release(order.getCart());
        order.cancel();
        notifications.sendCancellation(order.getCustomer(), orderId);
    }

    private String createOrderId() {
        orderCounter++;
        return "ORD-" + orderCounter;
    }
}
