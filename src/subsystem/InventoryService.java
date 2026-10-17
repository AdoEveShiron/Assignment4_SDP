package subsystem;

import exception.OutOfStockException;
import model.Cart;
import model.CartItem;

import java.util.HashMap;
import java.util.Map;

public class InventoryService {
    private final Map<String, Integer> stock = new HashMap<>();

    public void addStock(String product, int quantity) {
        stock.merge(product, quantity, Integer::sum);
    }

    public int getStock(String product) {
        return stock.getOrDefault(product, 0);
    }

    public void reserve(Cart cart) {
        for (CartItem item : cart.getItems()) {
            if (getStock(item.getProduct()) < item.getQuantity()) {
                throw new OutOfStockException("Not enough stock for " + item.getProduct());
            }
        }
        for (CartItem item : cart.getItems()) {
            stock.put(item.getProduct(), getStock(item.getProduct()) - item.getQuantity());
        }
    }

    public void release(Cart cart) {
        for (CartItem item : cart.getItems()) {
            addStock(item.getProduct(), item.getQuantity());
        }
    }
}
