// BankTransferPaymentStrategy.java
public class BankTransferPaymentStrategy implements PaymentStrategy {
    private String bankAccount;

    public BankTransferPaymentStrategy(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Override
    public boolean processPayment(double amount) {
        System.out.println("Processing payment of $" + amount + " via Bank Transfer...");
        return true;  // Assume payment is successful
    }
}
