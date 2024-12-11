package store.domain;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
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

    public Product getProductByName(String name) {
        return products.stream()
                .filter(product -> product.getName().equals(name))
                .findAny().orElse(null);
    }

    public int getTotalQuantityByName(String name) {
        List<Product> productList = getProductsByName(name);
        return productList.stream()
                .mapToInt(Product::getQuantity)
                .sum();
    }

    private List<Product> getAllKindsOfProducts(String name) {
        return products.stream()
                .filter(product -> product.getName().equals(name))
                .toList();
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
                .filter(this::isValidDate)
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

    private boolean isValidDate(Product product) {
        LocalDate now = DateTimes.now().toLocalDate();
        return now.isAfter(product.getPromotion().getStartDate()) && now.isBefore(product.getPromotion().getEndDate());
    }

    public void update(Orders orders) {
        orders.getOrders().forEach(this::updateQuantity);
    }

    private void updateQuantity(Order order) {
        List<Product> productList = getAllKindsOfProducts(order.getName());
        if (productList.size() == 2) {
            updateHasPromotion(order, productList);
            return;
        }
        updateOnlyNormal(order, productList.getFirst());
    }

    private void updateHasPromotion(Order order, List<Product> productList) {
        Product promotionProduct = productList.get(0);
        Product normalProduct = productList.get(1);
        if (promotionProduct.getQuantity() > order.getQuantity()) {
            promotionProduct.setQuantity(promotionProduct.getQuantity() - order.getQuantity());
            return;
        }
        normalProduct.setQuantity(normalProduct.getQuantity() - (order.getQuantity() - promotionProduct.getQuantity()));
        promotionProduct.setQuantity(0);
    }

    private void updateOnlyNormal(Order order, Product product) {
        product.setQuantity(product.getQuantity() - order.getQuantity());
    }
}
