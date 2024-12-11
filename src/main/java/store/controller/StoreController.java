package store.controller;

import store.domain.Products;
import store.util.FileLoader;

public class StoreController {
    public void run() {
        Products products = FileLoader.getProducts();
    }
}
