import java.util.*;

public class Main {
    private static Map<String, Order> orderMap = new HashMap<>();
    private static Map<String, Delivery> deliveryMap = new HashMap<>();
    private static Map<String, Feedback> feedbackMap = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create user
        User user = new User("John Doe", "123 Main St");

        // Add built-in pizzas to the system
        addBuiltInPizzas(user);

        SeasonalPromotion promotion = new SeasonalPromotion();

        System.out.println("Welcome to the Pizza Ordering System!");

        // Main menu loop
        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Customize Pizza");
            System.out.println("2. Order Pizza and payment");
            System.out.println("3. User Profile and favourites");
            System.out.println("4. Track Order");
            System.out.println("5. View Loyalty Points");
            System.out.println("6. Seasonal Promotions and Discounts");
            System.out.println("7. Provide Feedback and rating");
            System.out.println("8. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    customizePizza(scanner, user, promotion);
                    break;
                case 2:
                    orderPizza(scanner, user, promotion);
                    break;
                case 3:
                    manageUserProfile(scanner, user);
                    break;
                case 4:
                    trackOrder(scanner);
                    break;
                case 5:
                    viewLoyaltyPoints(user);
                    break;
                case 6:
                    displaySeasonalPromotions(promotion); // Handle new option
                    break;
                case 7:
                    manageFeedback(scanner, user);
                    break;
                case 8:
                    System.out.println("Thank you for using the Pizza Ordering System!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }

        }
    }

    // Add prebuilt pizzas to the user's allPizzas list
    private static void addBuiltInPizzas(User user) {
        Pizza pizza1 = new Pizza.Builder()
                .setName("Margherita")
                .setCrust("Thin")
                .setSauce("Tomato")
                .setToppings("Basil")
                .setCheese("Mozzarella")
                .build();

        Pizza pizza2 = new Pizza.Builder()
                .setName("Pepperoni Classic")
                .setCrust("Thick")
                .setSauce("Tomato")
                .setToppings("Pepperoni")
                .setCheese("Mozzarella")
                .build();

        Pizza pizza3 = new Pizza.Builder()
                .setName("Veggie Lovers")
                .setCrust("Thin")
                .setSauce("BBQ")
                .setToppings("Onions, Capsicum")
                .setCheese("Cheddar")
                .build();

        Pizza pizza4 = new Pizza.Builder()
                .setName("BBQ Chicken Special")
                .setCrust("Stuffed")
                .setSauce("BBQ")
                .setToppings("Chicken, Sweetcorn")
                .setCheese("Mozzarella")
                .build();

        Pizza pizza5 = new Pizza.Builder()
                .setName("Hawaiian Delight")
                .setCrust("Thin")
                .setSauce("Tomato")
                .setToppings("Ham, Pineapple")
                .setCheese("Mozzarella")
                .build();

        Pizza pizza6 = new Pizza.Builder()
                .setName("Cheese Overload")
                .setCrust("Thick")
                .setSauce("Alfredo")
                .setToppings("None")
                .setCheese("Mozzarella, Parmesan, Cheddar")
                .build();

        user.addPizza(pizza1, false);
        user.addPizza(pizza2, false);
        user.addPizza(pizza3, false);
        user.addPizza(pizza4, false);
        user.addPizza(pizza5, false);
        user.addPizza(pizza6, false);

    }

    // Pizza customization
    private static void customizePizza(Scanner scanner, User user, SeasonalPromotion promotion) {
        System.out.print("Enter Pizza Name: ");
        String name = scanner.nextLine();
        System.out.print("Choose Crust (Thin, Thick, Stuffed): ");
        String crust = scanner.nextLine();
        System.out.print("Choose Sauce (Tomato, Alfredo, BBQ): ");
        String sauce = scanner.nextLine();
        System.out.print("Choose Toppings: ");
        String toppings = scanner.nextLine();
        System.out.print("Choose Cheese: ");
        String cheese = scanner.nextLine();

        List<String> toppingsList = Arrays.asList(toppings.split(",\\s*"));
        List<String> crustList = Arrays.asList(crust);

        Pizza pizza = new Pizza.Builder()
                .setName(name)
                .setCrust(crust)
                .setSauce(sauce)
                .setToppings(toppings)
                .setCheese(cheese)
                .build();

        while (true) {
            System.out.print("Add Extra Toppings? (cheese/olives/pepperoni/none): ");
            String extraTopping = scanner.nextLine();
            if (extraTopping.equalsIgnoreCase("none"))
                break;

            switch (extraTopping.toLowerCase()) {
                case "cheese":
                    pizza = new CheeseToppingDecorator(pizza);
                    break;
                case "olives":
                    pizza = new OlivesToppingDecorator(pizza);
                    break;
                case "pepperoni":
                    pizza = new PepperoniToppingDecorator(pizza);
                    break;
                default:
                    System.out.println("Invalid topping choice! Try again.");
            }
        }

        double originalPrice = pizza.getPrice();
        double discountedPrice = promotion.applyDiscount(toppingsList, originalPrice);
        discountedPrice = promotion.applyDiscount(crustList, discountedPrice);

        System.out.println("Pizza Customized: " + pizza);
        System.out.println("Original Price: $" + String.format("%.2f", originalPrice));
        System.out.println("Price after discounts: $" + String.format("%.2f", discountedPrice));

        System.out.print("Save to Favorites? (yes/no): ");
        boolean save = scanner.nextLine().equalsIgnoreCase("yes");
        user.addPizza(pizza, save);
        if (save) {
            System.out.println("Pizza added to your favorites.");
        } else {
            System.out.println("Pizza added to the new list.");
        }               

    }

    // Order a pizza
    private static void orderPizza(Scanner scanner, User user, SeasonalPromotion promotion) {
        System.out.println("Choose Pizza: ");
        System.out.println("1. New List");
        System.out.println("2. Favorites");
        int pizzaChoice = scanner.nextInt();
        scanner.nextLine();

        List<OrderItem> orderItems = new ArrayList<>();
        double totalOrderPrice = 0.0;
        double totalDiscount = 0.0;

        while (true) {
            Pizza pizza;
            if (pizzaChoice == 1) {
                System.out.println("All Pizzas:");
                for (int i = 0; i < user.getAllPizzas().size(); i++) {
                    Pizza currentPizza = user.getAllPizzas().get(i);
                    System.out.println((i + 1) + ". " + currentPizza + " | Rating: "
                            + String.format("%.1f", currentPizza.getAverageRating()));
                }
                System.out.print("Select Pizza: ");
                int index = scanner.nextInt();
                scanner.nextLine();
                pizza = user.getAllPizzas().get(index - 1);
            } else {
                System.out.println("Favorites:");
                for (int i = 0; i < user.getFavorites().size(); i++) {
                    Pizza currentPizza = user.getFavorites().get(i);
                    System.out.println((i + 1) + ". " + currentPizza + " | Rating: "
                            + String.format("%.1f", currentPizza.getAverageRating()));
                }
                System.out.print("Select Pizza: ");
                int index = scanner.nextInt();
                scanner.nextLine();
                pizza = user.getFavorites().get(index - 1);
            }

            // Integrate Decorator to Add Extra Toppings
            while (true) {
                System.out.print("Add Extra Toppings? (cheese/olives/pepperoni/none): ");
                String extraTopping = scanner.nextLine();
                if (extraTopping.equalsIgnoreCase("none"))
                    break;

                switch (extraTopping.toLowerCase()) {
                    case "cheese":
                        pizza = new CheeseToppingDecorator(pizza);
                        break;
                    case "olives":
                        pizza = new OlivesToppingDecorator(pizza);
                        break;
                    case "pepperoni":
                        pizza = new PepperoniToppingDecorator(pizza);
                        break;
                    default:
                        System.out.println("Invalid topping choice! Try again.");
                }
            }

            System.out.print("Enter Quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();
            double basePrice = pizza.getPrice();

            // Use getToppings() based on its return type
            List<String> toppingsList = pizza.getToppings();
            List<String> crustList = Arrays.asList(pizza.getCrust());

            double discountedPrice = promotion.applyDiscount(toppingsList, basePrice);
            discountedPrice = promotion.applyDiscount(crustList, discountedPrice);

            double pizzaTotalPrice = discountedPrice * quantity;
            double pizzaDiscount = (basePrice - discountedPrice) * quantity;
            totalDiscount += pizzaDiscount;
            totalOrderPrice += pizzaTotalPrice;

            orderItems.add(new OrderItem(pizza, quantity));

            System.out.print("Do you want to add more pizzas to the order? (yes/no): ");
            String addMore = scanner.nextLine();
            if (addMore.equalsIgnoreCase("no")) {
                break;
            }
        }

        System.out.print("Pickup or Delivery? (pickup/delivery): ");
        boolean isDelivery = scanner.nextLine().equalsIgnoreCase("delivery");

        String address = null;
        if (isDelivery) {
            System.out.print("Enter Delivery Address: ");
            address = scanner.nextLine();
        }

        Order order = new Order(user.getUserId(), isDelivery, address);
        for (OrderItem item : orderItems) {
            Pizza pizza = item.getPizza();
            int quantity = item.getQuantity();

            double basePrice = pizza.getPrice();
            List<String> toppingsList = pizza.getToppings();
            List<String> crustList = Arrays.asList(pizza.getCrust());

            // Apply seasonal discounts
            double discountedPrice = promotion.applyDiscount(toppingsList, basePrice);
            discountedPrice = promotion.applyDiscount(crustList, discountedPrice);
            double discountAmount = basePrice - discountedPrice;

            // Add pizza to the order
            order.addPizza(pizza, quantity, discountedPrice, discountAmount);
        }
        orderMap.put(order.getOrderId(), order);

        if (isDelivery) {
            Delivery delivery = new Delivery(user.getUserId(), order, address);
            deliveryMap.put(delivery.getDeliveryId(), delivery);
            System.out.println("Delivery created: " + delivery);
        }

        double loyaltyDollarValue = user.getLoyaltyPoints() * 0.1;
        String paymentId = null;

        if (user.getLoyaltyPoints() > 0) {
            System.out.println("You have " + user.getLoyaltyPoints() + " loyalty points (equivalent to $"
                    + loyaltyDollarValue + ").");
            System.out.print("Do you want to use loyalty points? (yes/no): ");
            boolean usePoints = scanner.nextLine().equalsIgnoreCase("yes");

            if (usePoints) {
                if (loyaltyDollarValue >= totalOrderPrice) {
                    System.out.println("Your loyalty points covered the entire price. No payment required.");
                    user.addLoyaltyPoints(-(int) (totalOrderPrice / 0.1));
                    totalOrderPrice = 0;
                } else {
                    totalOrderPrice -= loyaltyDollarValue;
                    System.out.println("Loyalty points applied. Remaining price: $" + totalOrderPrice);
                    user.addLoyaltyPoints(-(int) (loyaltyDollarValue / 0.1));
                }
            }
        }

        if (totalOrderPrice > 0) {
            System.out.println("Choose Payment Method:");
            System.out.println("1. Credit Card");
            System.out.println("2. Digital Wallet");
            System.out.println("3. Bank Transfer");

            int paymentChoice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            PaymentStrategy paymentStrategy = null;

            switch (paymentChoice) {
                case 1:
                    System.out.print("Enter Card Number: ");
                    String cardNumber = scanner.nextLine();
                    System.out.print("Enter Card Holder Name: ");
                    String cardHolderName = scanner.nextLine();
                    System.out.print("Enter Expiry Date (MM/YY): ");
                    String expiryDate = scanner.nextLine();
                    paymentStrategy = new CreditCardPaymentStrategy(cardNumber, cardHolderName, expiryDate);
                    break;

                case 2:
                    System.out.print("Enter Wallet ID: ");
                    String walletId = scanner.nextLine();
                    paymentStrategy = new DigitalWalletPaymentStrategy(walletId);
                    break;

                case 3:
                    System.out.print("Enter Bank Account Number: ");
                    String bankAccount = scanner.nextLine();
                    paymentStrategy = new BankTransferPaymentStrategy(bankAccount);
                    break;

                default:
                    System.out.println("Invalid payment method choice!");
                    return; // Handle invalid case
            }

            // Process payment using the chosen strategy
            if (paymentStrategy != null && paymentStrategy.processPayment(totalOrderPrice)) {
                System.out.println("Payment processed successfully!");
            }
        }

        System.out.println("Order placed successfully!");
        System.out.println("\n--- Bill ---");
        System.out.println("\nYour Order ID is: " + order.toString());
        System.out.println("Loyalty Points Earned: 10");
        System.out.println("Thank you for your order!");

        user.addLoyaltyPoints(10); // Add points for purchase
    }

    private static void displaySeasonalPromotions(SeasonalPromotion promotion) {
        System.out.println("\n--- Seasonal Promotions and Discounts ---");
        Map<String, Double> discounts = promotion.getDiscounts();

        if (discounts.isEmpty()) {
            System.out.println("No seasonal promotions available at the moment.");
        } else {
            for (Map.Entry<String, Double> entry : discounts.entrySet()) {
                System.out.printf("%s: %.0f%% off\n", entry.getKey(), entry.getValue() * 100);
            }
        }
    }

    private static void trackOrder(Scanner scanner) {
        System.out.print("Enter Order ID to Track: ");
        String orderId = scanner.nextLine();

        // Retrieve the order using the orderId
        Order order = orderMap.get(orderId);

        if (order != null) {
            // Display the current status and next status
            System.out.println("Current Status: " + order.getCurrentStage());

            // Advance to the next stage (if applicable)
            if (order.hasNextStage()) {
                order.advanceToNextStage();
                System.out.println("Next Update: " + order.getCurrentStage()); // Show updated status
            } else {
                System.out.println("Next Update: No further updates. Order is completed.");
            }
        } else {
            System.out.println("Invalid Order ID.");
        }
    }

    private static void manageFeedback(Scanner scanner, User user) {
        System.out.println("1. Provide Feedback");
        System.out.println("2. Edit Feedback");
        System.out.println("3. View Feedback");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        switch (choice) {
            case 1:
                provideFeedback(scanner, user);
                break;
            case 2:
                editFeedback(scanner, user);
                break;
            case 3:
                viewFeedback(scanner, user);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void provideFeedback(Scanner scanner, User user) {
        System.out.print("Enter Order ID for Feedback: ");
        String orderId = scanner.nextLine();

        if (orderMap.containsKey(orderId)) {
            Order order = orderMap.get(orderId);
            List<OrderItem> orderItems = order.getOrderItems();

            System.out.print("Enter your feedback: ");
            String feedbackMessage = scanner.nextLine();
            System.out.print("Rate your order (1-5): ");
            int rating = scanner.nextInt();
            scanner.nextLine();

            for (OrderItem item : orderItems) {
                Pizza pizza = item.getPizza();
                pizza.addRating(rating);

                Feedback feedback = new Feedback(orderId, user.getUserId(), feedbackMessage, rating);
                feedbackMap.put(feedback.getFeedbackId(), feedback);
            }

            System.out.println("Thank you for your feedback!");
            System.out.println("Your Feedback ID is: " + feedbackMap.keySet().toArray()[feedbackMap.size() - 1]);

            for (OrderItem item : orderItems) {
                Pizza pizza = item.getPizza();
                System.out.println("Updated Pizza Rating for " + pizza.getName() + ": "
                        + String.format("%.1f", pizza.getAverageRating()));
            }
        } else {
            System.out.println("Invalid Order ID.");
        }
    }

    private static void editFeedback(Scanner scanner, User user) {
        System.out.print("Enter Feedback ID to edit: ");
        String feedbackId = scanner.nextLine();

        if (feedbackMap.containsKey(feedbackId)) {
            Feedback feedback = feedbackMap.get(feedbackId);

            // Verify the user is authorized to edit this feedback
            if (!feedback.getUserId().equals(user.getUserId())) {
                System.out.println("You are not authorized to edit this feedback.");
                return;
            }

            System.out.println("Current Feedback: " + feedback.getMessage());
            System.out.print("Enter new feedback message (or press Enter to skip): ");
            String newMessage = scanner.nextLine();
            if (!newMessage.isEmpty()) {
                feedback.setMessage(newMessage);
                System.out.println("Feedback message updated successfully.");
            }

            System.out.println("Current Rating: " + feedback.getRating());
            System.out.print("Enter new rating (1-5): ");
            int newRating = scanner.nextInt();
            scanner.nextLine();

            // Update pizza ratings
            String orderId = feedback.getOrderId();
            Order order = orderMap.get(orderId);
            if (order != null) {
                for (OrderItem item : order.getOrderItems()) {
                    Pizza pizza = item.getPizza();
                    // Remove the old rating and add the new rating
                    pizza.removeRating(feedback.getRating());
                    pizza.addRating(newRating);
                }
            }

            feedback.setRating(newRating); // Update feedback rating
            System.out.println("Feedback rating updated successfully.");
        } else {
            System.out.println("Invalid Feedback ID.");
        }
    }

    private static void viewFeedback(Scanner scanner, User user) {
        System.out.println("Feedbacks by User ID: " + user.getUserId());
        boolean hasFeedback = false;

        for (Feedback feedback : feedbackMap.values()) {
            if (feedback.getUserId().equals(user.getUserId())) {
                System.out.println("Feedback ID: " + feedback.getFeedbackId());
                System.out.println("Order ID: " + feedback.getOrderId());
                System.out.println("Message: " + feedback.getMessage());
                System.out.println("Rating: " + feedback.getRating());
                System.out.println("---------------------------------------");
                hasFeedback = true;
            }
        }

        if (!hasFeedback) {
            System.out.println("No feedback found for this user.");
        }
    }

    private static void viewLoyaltyPoints(User user) {
        System.out.println("Your Loyalty Points: " + user.getLoyaltyPoints());
    }

    private static void manageUserProfile(Scanner scanner, User user) {
        while (true) {
            System.out.println("\nUser Profile:");
            System.out.println("1. View Profile");
            System.out.println("2. Update Name");
            System.out.println("3. Update Address");
            System.out.println("4. View Favorite Pizzas");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear the buffer

            switch (choice) {
                case 1:
                    System.out.println("\nProfile Details:");
                    System.out.println(user); // Displays the current user profile
                    break;
                case 2:
                    System.out.print("Enter new name: ");
                    String newName = scanner.nextLine();
                    user.setName(newName); // Updates the user's name
                    System.out.println("Name updated successfully!");
                    break;
                case 3:
                    System.out.print("Enter new address: ");
                    String newAddress = scanner.nextLine();
                    user.setAddress(newAddress); // Updates the user's address
                    System.out.println("Address updated successfully!");
                    break;
                case 4:
                    System.out.println("\nYour Favorite Pizzas:");
                    if (user.getFavorites().isEmpty()) {
                        System.out.println("You have no favorite pizzas yet.");
                    } else {
                        for (Pizza favorite : user.getFavorites()) {
                            System.out.println(favorite); // Display each favorite pizza's details
                        }
                    }
                    break;
                case 5:
                    return; // Exit the User Profile menu
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

}
