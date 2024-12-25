import java.util.List;
import java.util.ArrayList;

public class Delivery {
    private static int nextDeliveryId = 1;
    private final String deliveryId;   
    private final String userId;       
    private final String orderId;      
    private List<String> pizzaIds;     
    private final String address;      
    private String status;             

    // Constructor to initialize Delivery with Order and Pizza info
    public Delivery(String userId, Order order, String address) {
        this.deliveryId = String.format("%04d", nextDeliveryId++);
        this.userId = userId;
        this.orderId = order.getOrderId();
        this.address = address;
        this.status = "Ordered";

        this.pizzaIds = new ArrayList<>();
        if (order != null && !order.getOrderItems().isEmpty()) {
            for (OrderItem item : order.getOrderItems()) {
                this.pizzaIds.add(item.getPizza().getPizzaId());
            }
        }
    }

    // Getters
    public String getDeliveryId() {
        return deliveryId;
    }

    public String getUserId() {
        return userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public List<String> getPizzaIds() {
        return pizzaIds;
    }

    public String getAddress() {
        return address;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Delivery [Delivery ID=" + deliveryId + ", User ID=" + userId +
               ", Order ID=" + orderId + ", Pizza IDs=" + pizzaIds + ", Address=" + address +
               ", Status=" + status + "]";
    }
}
