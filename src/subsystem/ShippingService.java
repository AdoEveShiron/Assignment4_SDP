package subsystem;

import exception.ShippingException;
import model.Customer;

import java.util.HashSet;
import java.util.Set;

public class ShippingService {
    private final Set<String> activeShipments = new HashSet<>();
    private int counter = 1000;

    public String createShipment(Customer customer) {
        if (customer.getAddress() == null || customer.getAddress().isBlank()) {
            throw new ShippingException("Shipping failed: no delivery address");
        }
        counter++;
        String trackingNumber = "TRK" + counter;
        activeShipments.add(trackingNumber);
        return trackingNumber;
    }

    public void cancelShipment(String trackingNumber) {
        activeShipments.remove(trackingNumber);
    }

    public boolean isActive(String trackingNumber) {
        return activeShipments.contains(trackingNumber);
    }
}
