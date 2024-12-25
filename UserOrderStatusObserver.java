public class UserOrderStatusObserver implements OrderStatusObserver {
    private String userId;

    public UserOrderStatusObserver(String userId) {
        this.userId = userId;
    }

    @Override
    public void update(Order order) {
        System.out.println("User " + userId + ", your order status has been updated:");
        System.out.println("Current Status: " + order.getCurrentStage());
        if (order.hasNextStage()) {
            System.out.println("Next Update: " + order.getNextStage());
        } else {
            System.out.println("Next Update: Order is completed.");
        }
    }
}
