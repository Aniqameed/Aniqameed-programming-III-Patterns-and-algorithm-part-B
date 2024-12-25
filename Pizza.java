import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Pizza {
    private static final AtomicInteger idGenerator = new AtomicInteger(1);
    private final String pizzaId;
    private String name;
    private String crust;
    private String sauce;
    private String toppings;
    private String cheese;
    private double price;
    public List<String> extraToppings;
    private List<Integer> ratings;

    // Private constructor
    public Pizza(Builder builder) {
        this.name = builder.name;
        this.crust = builder.crust;
        this.sauce = builder.sauce;
        this.toppings = builder.toppings;
        this.cheese = builder.cheese;
        this.extraToppings = new ArrayList<>();
        this.ratings = new ArrayList<>();
        this.price = calculateBasePrice();
        this.pizzaId = "PZ-" + idGenerator.getAndIncrement(); // Unique Pizza ID
    }

    // Builder pattern for creating Pizza objects
    public static class Builder {
        private String name;
        private String crust;
        private String sauce;
        private String toppings;
        private String cheese;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setSauce(String sauce) {
            this.sauce = sauce;
            return this;
        }

        public Builder setToppings(String toppings) {
            this.toppings = toppings;
            return this;
        }

        public Builder setCrust(String crust) {
            this.crust = crust;
            return this;
        }

        public Builder setCheese(String cheese) {
            this.cheese = cheese;
            return this;
        }

        public Pizza build() {
            return new Pizza(this);
        }
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getCrust() {
        return crust;
    }

    public String getSauce() {
        return sauce;
    }

    public List<String> getToppings() {
        return toppings == null ? Collections.emptyList() : Arrays.asList(toppings.split(",\\s*"));
    }

    public void setToppings(String toppings) {
        this.toppings = toppings;
    }

    public void setSauce(String sauce) {
        this.sauce = sauce;
    }

    public void setCrust(String crust) {
        this.crust = crust;
    }

    public String getCheese() {
        return cheese;
    }

    public double getPrice() {
        return price;
    }

    public String getPizzaId() {
        return pizzaId;
    }

    // Add extra topping
    public void addExtraTopping(String topping) {
        if (!extraToppings.contains(topping)) {
            extraToppings.add(topping);
            price += 1.0; // Each extra topping adds $1.00
        }
    }

    // Add a rating
    public void addRating(int rating) {
        if (rating >= 1 && rating <= 5) {
            ratings.add(rating);
        }
    }

    // Remove a specific rating
    public void removeRating(int rating) {
        if (ratings.contains(rating)) {
            ratings.remove((Integer) rating);
            System.out.println("Rating " + rating + " removed from this pizza.");
        } else {
            System.out.println("Rating " + rating + " not found for this pizza.");
        }
    }

    // Get average rating
    public double getAverageRating() {
        return ratings.isEmpty() ? 0.0 : ratings.stream().mapToInt(Integer::intValue).average().orElse(0.0);
    }

    // Calculate base price of the pizza
    private double calculateBasePrice() {
        double basePrice = 10.0; // Starting price
        if ("Thick".equalsIgnoreCase(crust)) {
            basePrice += 2.0;
        }
        if ("Stuffed".equalsIgnoreCase(crust)) {
            basePrice += 3.0;
        }
        if ("BBQ".equalsIgnoreCase(sauce)) {
            basePrice += 1.5;
        }
        return basePrice;
    }

    public String getDescription() {
        return String.format(
                "Pizza{name='%s', crust='%s', sauce='%s', toppings='%s', cheese='%s', price=$%.2f, pizzaId='%s'}",
                name, crust, sauce, toppings, cheese, price, pizzaId);
    }

    @Override
    public String toString() {
        return String.format(
                "Pizza{name='%s', crust='%s', sauce='%s', toppings='%s', cheese='%s', price=$%.2f, pizzaId='%s'}",
                name, crust, sauce, toppings, cheese, price, pizzaId);
    }
}
