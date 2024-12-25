public class OutForDeliveryState implements OrderState {

    @Override
    public void advanceToNextStage(Order order) {
        // After the order is out for delivery, move to delivered state
        order.setCurrentState(new DeliveredState());
    }

    @Override
    public String getStatus() {
        return "Pizza is out for delivery. Estimated delivery time: 30 minutes.";
    }
}
