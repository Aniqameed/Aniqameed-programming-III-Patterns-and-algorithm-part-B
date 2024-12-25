// Topping customization handler
class ToppingHandler extends CustomizationHandler {
    @Override
    public void handleRequest(Pizza pizza, String request) {
        if (request.equalsIgnoreCase("topping")) {
            pizza.setToppings(request);
            System.out.println("Topping set to: " + request);
        } else if (nextHandler != null) {
            nextHandler.handleRequest(pizza, request);
        }
    }
}
