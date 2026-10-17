import exception.OrderException;
import facade.OrderFacade;
import model.Cart;
import model.Customer;

public class Main {
    public static void main(String[] args) {
        OrderFacade shop = new OrderFacade();
        shop.addProductToStore("Laptop", 5);
        shop.addProductToStore("Mouse", 10);

        System.out.println("=== 1. Successful order ===");
        Customer aigerim = new Customer("Aigerim", "aigerim@mail.com", "Almaty, Abay 10", 2000);
        Cart cart1 = new Cart();
        cart1.add("Laptop", 900, 1);
        cart1.add("Mouse", 20, 2);
        String orderId = shop.placeOrder(cart1, aigerim);
        System.out.println("Order id: " + orderId);
        System.out.println("Laptops in stock: " + shop.getProductStock("Laptop"));
        System.out.println("Balance: " + aigerim.getBalance());

        System.out.println();
        System.out.println("=== 2. Payment declined (stock must be released) ===");
        Customer daniyar = new Customer("Daniyar", "daniyar@mail.com", "Astana, Mangilik El 5", 100);
        Cart cart2 = new Cart();
        cart2.add("Laptop", 900, 2);
        try {
            shop.placeOrder(cart2, daniyar);
        } catch (OrderException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("Laptops in stock: " + shop.getProductStock("Laptop"));
        System.out.println("Balance: " + daniyar.getBalance());

        System.out.println();
        System.out.println("=== 3. Shipping failed (refund and stock release) ===");
        Customer sergey = new Customer("Sergey", "sergey@mail.com", "", 5000);
        Cart cart3 = new Cart();
        cart3.add("Mouse", 20, 3);
        try {
            shop.placeOrder(cart3, sergey);
        } catch (OrderException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("Mice in stock: " + shop.getProductStock("Mouse"));
        System.out.println("Balance: " + sergey.getBalance());

        System.out.println();
        System.out.println("=== 4. Out of stock ===");
        Cart cart4 = new Cart();
        cart4.add("Laptop", 900, 10);
        try {
            shop.placeOrder(cart4, aigerim);
        } catch (OrderException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println();
        System.out.println("=== 5. Cancel order ===");
        shop.cancelOrder(orderId);
        System.out.println("Laptops in stock: " + shop.getProductStock("Laptop"));
        System.out.println("Balance: " + aigerim.getBalance());
        try {
            shop.cancelOrder(orderId);
        } catch (OrderException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
