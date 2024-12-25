public class PreparingState implements OrderState {

    @Override
    public void advanceToNextStage(Order order) {
        // Once preparing is done, move to either pickup ready or out for delivery
        if (order.isDelivery()) {
            order.setCurrentState(new OutForDeliveryState());
        } else {
            order.setCurrentState(new PickupReadyState()); // Transition to pickup ready state
        }
    }

    @Override
    public String getStatus() {
        return "Your pizza is currently being prepared and will be ready soon.";
    }
}
