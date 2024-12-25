public class PlacedState implements OrderState {

    @Override
    public void advanceToNextStage(Order order) {
        // If the order is placed, move to the preparing state
        order.setCurrentState(new PreparingState());
    }

    @Override
    public String getStatus() {
        return "Your order has been placed, and pizza preparation is in progress.";
    }
}
