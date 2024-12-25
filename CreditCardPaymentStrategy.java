// CreditCardPaymentStrategy.java
public class CreditCardPaymentStrategy implements PaymentStrategy {
    private String cardNumber;
    private String cardHolderName;
    private String expiryDate;

    public CreditCardPaymentStrategy(String cardNumber, String cardHolderName, String expiryDate) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expiryDate = expiryDate;
    }

    @Override
    public boolean processPayment(double amount) {
        System.out.println("Processing payment of $" + amount + " via Credit Card...");
        return true;  // Assume payment is successful
    }
}
