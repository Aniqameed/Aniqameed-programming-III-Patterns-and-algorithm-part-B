// Olives topping
public class OlivesToppingDecorator extends ExtraToppingsDecorator {
    public OlivesToppingDecorator(Pizza pizza) {
        super(pizza);
    }

    @Override
    protected double getToppingPrice() {
        return 1.5; // Price for olives
    }

    @Override
    protected String getToppingDescription() {
        return "Olives";
    }
}