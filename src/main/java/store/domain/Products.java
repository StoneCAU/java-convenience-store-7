package store.domain;

import java.util.ArrayList;
import java.util.List;

public class Products {
    private List<Product> products;

    public Products(List<Product> products) {
        this.products = products;
        addNormalOutOfStockProduct();
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    public int getTotalQuantityByName(String name) {
        List<Product> productList = getProductsByName(name);
        return productList.stream()
                .mapToInt(Product::getQuantity)
                .sum();
    }

    private void addNormalOutOfStockProduct() {
        while (isNeedToAdd()) {
            Product product = findNormalOutOfStockProduct();
            if (product == null) return;
            products.add(products.indexOf(product) + 1, new Product(product.getProductInfo()));
        }
    }

    private Product findNormalOutOfStockProduct() {
        return products.stream()
                .filter(product -> getProductsByName(product.getName()).size() == 1)
                .filter(product -> getPromotionalProductByName(product.getName()) != null)
                .filter(product -> getNormalProductByName(product.getName()) == null)
                .findFirst().orElse(null);
    }

    private List<Product> getProductsByName(String name) {
        return products.stream()
                .filter(product -> product.getName().equals(name))
                .toList();
    }

    private Product getNormalProductByName(String name) {
        return products.stream()
                .filter(product -> product.getName().equals(name))
                .filter(product -> product.getPromotion() == null)
                .findFirst().orElse(null);
    }

    public Product getPromotionalProductByName(String name) {
        return products.stream()
                .filter(product -> product.getName().equals(name))
                .filter(product -> product.getPromotion() != null)
                .findFirst().orElse(null);
    }

    private boolean isNeedToAdd() {
        return products.stream()
                .anyMatch(product -> getProductsByName(product.getName()).size() == 1
                            && getPromotionalProductByName(product.getName()) != null
                            && getNormalProductByName(product.getName()) == null
                );
    }
}
