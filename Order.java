import java.util.*;

public class Order {
    private String orderId;
    private String userId;
    private boolean isDelivery;
    private String deliveryAddress;
    private List<OrderStatusObserver> observers;
    private List<OrderItem> orderItems;
    private OrderState currentState; // Current state of the order
    private double totalPrice;
    private double totalOrderPrice;
    private double totalDiscount;

    public Order(String userId, boolean isDelivery, String deliveryAddress) {
        this.orderId = UUID.randomUUID().toString();
        this.userId = userId;
        this.isDelivery = isDelivery;
        this.deliveryAddress = deliveryAddress;
        this.orderItems = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.totalOrderPrice = 0.0;
        this.totalDiscount = 0.0;

        // Initialize state based on delivery or pickup
        this.currentState = new PlacedState(); // Default state for both delivery and pickup
    }

    // Add observer
    public void addObserver(OrderStatusObserver observer) {
        observers.add(observer);
    }

    // Remove observer
    public void removeObserver(OrderStatusObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (OrderStatusObserver observer : observers) {
            observer.update(this);
        }
    }

    // Advance to the next stage and notify observers
    public void advanceToNextStage() {
        if (currentState instanceof CompletedState) return;
        currentState.advanceToNextStage(this);
        notifyObservers();
    }

    public String getOrderId() {
        return orderId;
    }

    public void addPizza(Pizza pizza, int quantity, double discountedPrice, double discountAmount) {
        if (pizza == null || quantity <= 0) {
            throw new IllegalArgumentException("Invalid pizza or quantity.");
        }
        this.orderItems.add(new OrderItem(pizza, quantity));
        this.totalPrice += pizza.getPrice() * quantity;
        this.totalOrderPrice += discountedPrice * quantity;
        this.totalDiscount += discountAmount * quantity;
    }

    public String getUserId() {
        return userId;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public boolean isDelivery() {
        return isDelivery;
    }

    public String getCurrentStage() {
        return currentState.getStatus();
    }

    public boolean hasNextStage() {
        return !(currentState instanceof DeliveredState || currentState instanceof CompletedState);
    }

    public String getNextStage() {
        if (currentState instanceof CompletedState) {
            return "No further updates. Order is completed.";
        }
        return currentState.getStatus();
    }

    // Set the current state
    public void setCurrentState(OrderState state) {
        this.currentState = state;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order ID: ").append(orderId)
                .append("\nUser ID: ").append(userId)
                .append("\nDelivery: ").append(isDelivery ? "Yes" : "No")
                .append("\nAddress: ").append(deliveryAddress != null ? deliveryAddress : "N/A")
                .append("\nStatus: ").append(getCurrentStage())
                .append("\nOrder Items:");
        for (OrderItem item : orderItems) {
            sb.append("\n - ").append(item.getPizza().getName())
                    .append(" x ").append(item.getQuantity());
        }
        sb.append("\nTotal Price: $").append(String.format("%.2f", totalPrice));
        sb.append("\nTotal Discounts: $").append(String.format("%.2f", totalDiscount))
                .append("\nTotal Order Price: $").append(String.format("%.2f", totalOrderPrice));
        return sb.toString();
    }
}
