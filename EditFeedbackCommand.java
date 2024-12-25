public class EditFeedbackCommand implements Command {
    private Feedback feedback;
    private String newMessage;

    public EditFeedbackCommand(Feedback feedback, String newMessage) {
        this.feedback = feedback;
        this.newMessage = newMessage;
    }

    @Override
    public void execute() {
        feedback.setMessage(newMessage);
        System.out.println("Feedback updated: " + feedback);
    }
}
