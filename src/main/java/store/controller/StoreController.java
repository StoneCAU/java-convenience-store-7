package store.controller;

import store.domain.Products;
import store.util.FileLoader;
import store.view.OutputView;

public class StoreController {
    public void run() {
        Products products = FileLoader.getProducts();
        OutputView.printWelcomeMessage(products);
    }
}
