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
        do {
            OutputView.printWelcomeMessage(products);
            Orders orders = makeOrders(products);
            getResults(orders);
            products.update(orders);
        } while (!getRetry().equals("N"));
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
        addOrders(orders);
        removedOrders(orders);
        String membership = checkMembership();
        OutputView.printResults(orders, membership);
    }

    private void addOrders(Orders orders) {
        orders.getOrders()
                .stream()
                .filter(orders::isPromotionAvailable)
                .forEach(order -> checkPromotionAvailability(orders, order));
    }

    private void removedOrders(Orders orders) {
        orders.getOrders()
                .stream()
                .filter(orders::hasNotPromotion)
                .forEach(order -> checkNotPromotionApplicability(orders, order));
    }

    private void checkPromotionAvailability(Orders orders, Order order) {
        while (true) {
            try {
                Product product = orders.findPromotionAvailableProduct();
                String reply = InputValidator.validateReply(InputView.inputPromotionApplicable(product));
                if (reply.equals("Y")) orders.getPromotionProduct(order);
                return;
            } catch (StoreException e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void checkNotPromotionApplicability(Orders orders, Order order) {
        while (true) {
            try {
                Product product = orders.findNotPromotionAvailableProduct();
                String reply = InputValidator.validateReply(InputView.inputNotPromotionApplicable(product, orders.getNotPromotionQuantity(order, product)));
                if (reply.equals("N")) orders.removeOrder(order, orders.getNotPromotionQuantity(order, product));
                return;
            } catch (StoreException e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private String checkMembership() {
        while (true) {
            try {
                String input = InputView.inputMembership();
                return InputValidator.validateReply(input);
            } catch (StoreException e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private String getRetry() {
        while (true) {
            try {
                String input = InputView.inputRetry();
                return InputValidator.validateReply(input);
            } catch (StoreException e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }
}
