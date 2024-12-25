// Sauce customization handler
class SauceHandler extends CustomizationHandler {
    @Override
    public void handleRequest(Pizza pizza, String request) {
        if (request.equalsIgnoreCase("sauce")) {
            pizza.setSauce(request);
            System.out.println("Sauce set to: " + request);
        } else if (nextHandler != null) {
            nextHandler.handleRequest(pizza, request);
        }
    }
}
