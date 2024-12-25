public class CompletedState implements OrderState {

    @Override
    public void advanceToNextStage(Order order) {
    }

    @Override
    public String getStatus() {
        return "Order completed.";
    }
}
