public class PickupReadyState implements OrderState {

    @Override
    public void advanceToNextStage(Order order) {
        // Transition to completed state after pickup
        order.setCurrentState(new CompletedState());
    }

    @Override
    public String getStatus() {
        return "Pizza is ready for pickup. Please collect within 15 minutes.";
    }
}
