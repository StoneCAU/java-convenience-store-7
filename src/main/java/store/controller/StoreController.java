package store.controller;

import store.domain.Orders;
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
}
