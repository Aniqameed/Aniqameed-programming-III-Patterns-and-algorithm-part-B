import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeasonalPromotion {
    private Map<String, Double> discounts;

    public SeasonalPromotion() {
        this.discounts = new HashMap<>();
        discounts.put("Mushrooms", 0.1); // 10% discount on Mushrooms
        discounts.put("Thick", 0.15);    // 15% discount on Thick Crust
    }

    public double applyDiscount(List<String> items, double originalPrice) {
        double totalDiscount = 0.0;

        for (String item : items) {
            if (discounts.containsKey(item)) {
                double itemDiscount = originalPrice * discounts.get(item);
                totalDiscount += itemDiscount;
                System.out.println("Seasonal Discount: $" + String.format("%.2f", itemDiscount) + " applied for " + item);
            }
        }

        double finalPrice = originalPrice - totalDiscount;
        return Math.max(finalPrice, 0.0); // Ensure price does not go below zero
    }

    // New Method to get discount details
    public Map<String, Double> getDiscounts() {
        return discounts;
    }
}
