public class AddFeedbackCommand implements Command {
    private Feedback feedback;

    public AddFeedbackCommand(Feedback feedback) {
        this.feedback = feedback;
    }

    @Override
    public void execute() {
        Feedback.addFeedback(feedback);
        System.out.println("Feedback added: " + feedback);
    }
}
