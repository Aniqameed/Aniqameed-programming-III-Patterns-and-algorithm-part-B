// Pepperoni topping
public class PepperoniToppingDecorator extends ExtraToppingsDecorator {
    public PepperoniToppingDecorator(Pizza pizza) {
        super(pizza);
    }

    @Override
    protected double getToppingPrice() {
        return 2.0; // Price for pepperoni
    }

    @Override
    protected String getToppingDescription() {
        return "Pepperoni";
    }
}