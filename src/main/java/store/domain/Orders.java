package store.domain;

import java.util.List;

public class Orders {
    private List<Order> orders;
    private Products products;

    public Orders(List<Order> orders, Products products) {
        this.orders = orders;
        this.products = products;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public Product findPromotionAvailableProduct() {
         String name = orders.stream()
                .filter(order -> products.getPromotionalProductByName(order.getName()) != null)
                .filter(order -> products.getPromotionalProductByName(order.getName()).getQuantity() >= order.getQuantity())
                .filter(order -> {
                    Product product = products.getPromotionalProductByName(order.getName());
                    return checkPromotionCondition(product, order.getQuantity());
                }).findFirst().get().getName();

         return products.getPromotionalProductByName(name);
    }

    public Product findNotPromotionAvailableProduct() {
        String name = orders.stream()
                .filter(order -> products.getPromotionalProductByName(order.getName()) != null)
                .filter(order -> checkHasNotPromotionCondition(products.getPromotionalProductByName(order.getName()), order.getQuantity()))
                .filter(order -> order.getQuantity() >= products.getPromotionalProductByName(order.getName()).getQuantity())
                .findFirst().get().getName();

        return products.getPromotionalProductByName(name);
    }

    public Order getPromotionProduct(Product product) {
        return new Order(product.getName(), 1, true);
    }

    public void removeOrder(Order order, int quantity) {
        order.setQuantity(order.getQuantity() - quantity);
    }

    public void addOrders(List<Order> orders) {
        this.orders.addAll(orders);
    }

    public boolean isPromotionAvailable(Order order) {
        if (products.getPromotionalProductByName(order.getName()) != null
                && products.getPromotionalProductByName(order.getName()).getQuantity() >= order.getQuantity()) {
            Product product = products.getPromotionalProductByName(order.getName());
            return checkPromotionCondition(product, order.getQuantity());
        }
        return false;
    }

    public boolean hasNotPromotion(Order order) {
        if (products.getPromotionalProductByName(order.getName()) != null
                && checkHasNotPromotionCondition(products.getPromotionalProductByName(order.getName()), order.getQuantity())) {
            return order.getQuantity() >= products.getPromotionalProductByName(order.getName()).getQuantity();
        }
        return false;
    }

    public int getNotPromotionQuantity(Product product) {
        int totalPromotionQuantity = products.getPromotionalProductByName(product.getName()).getQuantity();
        return totalPromotionQuantity - (totalPromotionQuantity % (product.getPromotion().getGet() + product.getPromotion().getBuy()));
    }

    private boolean checkPromotionCondition(Product product, int quantity) {
        return (quantity + 1) % (product.getPromotion().getBuy() + product.getPromotion().getGet()) == 0;
    }

    private boolean checkHasNotPromotionCondition(Product product, int quantity) {
        int totalPromotionQuantity = products.getPromotionalProductByName(product.getName()).getQuantity();
        return quantity >= totalPromotionQuantity - (totalPromotionQuantity % (product.getPromotion().getGet() + product.getPromotion().getBuy()));
    }
}
