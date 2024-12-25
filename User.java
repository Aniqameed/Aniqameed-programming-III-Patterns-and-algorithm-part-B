import java.util.*;

public class User {
    private static int nextUserId = 1;
    private final String userId;
    private String name;
    private String address;
    private int loyaltyPoints;
    private List<Pizza> favorites;
    private List<Pizza> allPizzas;

    public User(String name, String address) {
        this.userId = String.format("%04d", nextUserId++);
        this.name = name;
        this.address = address;
        this.loyaltyPoints = 0;
        this.favorites = new ArrayList<>();
        this.allPizzas = new ArrayList<>();
    }

    // Getters
    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public List<Pizza> getFavorites() {
        return favorites;
    }

    public List<Pizza> getAllPizzas() {
        return allPizzas;
    }

    // Setters
    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        }
    }

    public void setAddress(String address) {
        if (address != null && !address.trim().isEmpty()) {
            this.address = address;
        }
    }

    // Add loyalty points
    public void addLoyaltyPoints(int points) {
        this.loyaltyPoints += points;
    }

    // Add pizza to the user's lists
    public void addPizza(Pizza pizza, boolean saveToFavorites) {
        if (pizza == null) return; // Null check
        allPizzas.add(pizza);
        if (saveToFavorites && !favorites.contains(pizza)) {
            favorites.add(pizza);
        }
    }

    // Redeem loyalty points
    public boolean redeemLoyaltyPoints(int points) {
        if (loyaltyPoints >= points) {
            loyaltyPoints -= points;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "User [User ID=" + userId + ", Name=" + name + ", Address=" + address +
               ", Loyalty Points=" + loyaltyPoints + ", Favorite Pizzas=" + favorites + "]";
    }
}
