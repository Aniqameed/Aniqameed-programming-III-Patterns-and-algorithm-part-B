// Crust customization handler
class CrustHandler extends CustomizationHandler {
    @Override
    public void handleRequest(Pizza pizza, String request) {
        if (request.equalsIgnoreCase("crust")) {
            pizza.setCrust(request);
            System.out.println("Crust set to: " + request);
        } else if (nextHandler != null) {
            nextHandler.handleRequest(pizza, request);
        }
    }
}