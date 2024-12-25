public abstract class ExtraToppingsDecorator extends Pizza {
    protected final Pizza basePizza;

    public ExtraToppingsDecorator(Pizza pizza) {
        super(new Builder()
                .setName(pizza.getName())
                .setCrust(pizza.getCrust())
                .setSauce(pizza.getSauce())
                .setToppings(String.join(", ", pizza.getToppings()))
                .setCheese(pizza.getCheese()));
        this.basePizza = pizza;
    }

    @Override
    public double getPrice() {
        return basePizza.getPrice() + getToppingPrice();
    }

    // Abstract method for the price of the specific topping
    protected abstract double getToppingPrice();

    @Override
    public String toString() {
        return basePizza.toString() + " + " + getToppingDescription();
    }

    // Abstract method for topping description
    protected abstract String getToppingDescription();
}
