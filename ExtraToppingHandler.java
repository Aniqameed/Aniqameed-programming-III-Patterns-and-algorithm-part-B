// Extra Topping customization handler
class ExtraToppingHandler extends CustomizationHandler {
    @Override
    public void handleRequest(Pizza pizza, String request) {
        if (request.equalsIgnoreCase("cheese") || request.equalsIgnoreCase("olives") || request.equalsIgnoreCase("pepperoni")) {
            pizza.addExtraTopping(request);
            System.out.println("Extra topping added: " + request);
        } else if (nextHandler != null) {
            nextHandler.handleRequest(pizza, request);
        }
    }
}