public interface OrderState {
    void advanceToNextStage(Order order);
    String getStatus();
}
