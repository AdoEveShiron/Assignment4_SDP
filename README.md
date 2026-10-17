# Order Processing Facade (Java)

Assignment #4 - Facade Pattern, ShP-2216 Software Design Patterns.

Option B: `OrderFacade` hides four subsystem services behind `placeOrder` and `cancelOrder`.

## Structure

```
src/
  Main.java                    client, uses only OrderFacade
  facade/OrderFacade.java      the Facade
  subsystem/                   InventoryService, PaymentProcessor, ShippingService, NotificationService
  model/                       Cart, CartItem, Customer, Order
  exception/                   OrderException, OutOfStockException, PaymentDeclinedException, ShippingException
docs/uml.png                   UML class diagram
```

## Facade API

- `placeOrder(Cart, Customer)` returns order id, or throws `OrderException` after rollback
- `cancelOrder(String orderId)`
- `addProductToStore(String, int)` and `getProductStock(String)`

## Order of steps in placeOrder

1. InventoryService reserves stock
2. PaymentProcessor charges the customer
3. ShippingService creates a shipment
4. NotificationService sends the confirmation

If a step fails, the completed steps are rolled back (refund, stock release) and the customer gets a failure e-mail.

## Run

```
javac -d out $(find src -name "*.java")
java -cp out Main
```

JDK 17 or newer. In IntelliJ IDEA: mark `src` as Sources Root and run `Main`.
