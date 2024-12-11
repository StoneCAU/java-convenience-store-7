package store.controller;

import java.util.List;
import java.util.stream.Collectors;
import store.domain.Order;
import store.domain.Orders;
import store.domain.Product;
import store.domain.Products;
import store.exception.StoreException;
import store.util.FileLoader;
import store.util.InputValidator;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {
    public void run() {
        Products products = FileLoader.getProducts();
        OutputView.printWelcomeMessage(products);
        Orders orders = makeOrders(products);
        getResults(orders);
    }

    private Orders makeOrders(Products products) {
        while (true) {
            try {
                String input = InputView.inputOrders();
                return InputValidator.getOrders(products, input);
            } catch (StoreException e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void getResults(Orders orders) {
        List<Order> addedOrders = orders.getOrders()
                .stream()
                .filter(orders::isPromotionAvailable)
                .map(order -> checkPromotionAvailability(orders))
                .collect(Collectors.toList());

        orders.addOrders(addedOrders);
    }

    private Order checkPromotionAvailability(Orders orders) {
        while (true) {
            try {
                Product product = orders.findPromotionAvailableProduct();
                String input = InputView.inputPromotionApplicable(product);
                String reply = InputValidator.validateReply(input);
                if (reply.equals("Y")) return orders.getPromotionProduct(product);
            } catch (StoreException e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }
}
