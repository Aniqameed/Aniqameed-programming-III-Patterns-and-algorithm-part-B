public class DeliveredState implements OrderState {

    @Override
    public void advanceToNextStage(Order order) {
        order.setCurrentState(this); // No further state changes
    }

    @Override
    public String getStatus() {
        return "Order delivered. Enjoy your meal!";
    }
}
