// Cheese topping
public class CheeseToppingDecorator extends ExtraToppingsDecorator {
    public CheeseToppingDecorator(Pizza pizza) {
        super(pizza);
    }

    @Override
    protected double getToppingPrice() {
        return 1.0; // Price for extra cheese
    }

    @Override
    protected String getToppingDescription() {
        return "Extra Cheese";
    }
}