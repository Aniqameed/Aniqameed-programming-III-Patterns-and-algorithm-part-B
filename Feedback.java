import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Feedback {
    private static int nextFeedbackId = 1; // Auto-incrementing feedback ID
    private final String feedbackId;      // Unique ID for the feedback
    private final String orderId;         // Associated Order ID
    private final String userId;          // Associated User ID
    private String message;               // Feedback message
    private int rating;                   // Feedback rating

    // Static repository to store feedback instances
    private static final Map<String, Feedback> feedbackRepository = new HashMap<>();

    // File path to store the feedback ID
    private static final String ID_FILE = "feedbackId.txt";

    public Feedback(String orderId, String userId, String message, int rating) {
        loadFeedbackId(); // Ensure feedback ID is loaded from the file
        this.feedbackId = String.format("%04d", nextFeedbackId++);
        this.orderId = orderId;
        this.userId = userId;
        this.message = message;
        setRating(rating);
        feedbackRepository.put(this.feedbackId, this); // Add feedback to repository
        saveFeedbackId(); // Save the new feedback ID back to the file
    }

    // Add Feedback to Repository
    public static void addFeedback(Feedback feedback) {
        feedbackRepository.put(feedback.getFeedbackId(), feedback);
    }

    // Retrieve Feedback by ID
    public static Feedback getFeedbackById(String feedbackId) {
        return feedbackRepository.get(feedbackId);
    }

    // Getters and Setters
    public String getFeedbackId() {
        return feedbackId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        if (rating >= 1 && rating <= 5) {
            this.rating = rating;
        } else {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }
    }

    @Override
    public String toString() {
        return "Feedback [Feedback ID=" + feedbackId + ", Order ID=" + orderId +
                ", User ID=" + userId + ", Message=" + message + ", Rating=" + rating + "]";
    }

    // Load the last used feedback ID from the file
    private static void loadFeedbackId() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ID_FILE))) {
            String line = reader.readLine();
            if (line != null) {
                nextFeedbackId = Integer.parseInt(line); // Set the nextFeedbackId to the saved value
            }
        } catch (IOException e) {
            System.out.println("Error loading feedback ID. Starting with ID 1.");
        }
    }

    // Save the current feedback ID to the file
    private static void saveFeedbackId() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ID_FILE))) {
            writer.write(String.valueOf(nextFeedbackId)); // Save the current nextFeedbackId
        } catch (IOException e) {
            System.out.println("Error saving feedback ID.");
        }
    }
}
