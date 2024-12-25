// DigitalWalletPaymentStrategy.java
public class DigitalWalletPaymentStrategy implements PaymentStrategy {
    private String walletId;

    public DigitalWalletPaymentStrategy(String walletId) {
        this.walletId = walletId;
    }

    @Override
    public boolean processPayment(double amount) {
        System.out.println("Processing payment of $" + amount + " via Digital Wallet...");
        return true;  // Assume payment is successful
    }
}
