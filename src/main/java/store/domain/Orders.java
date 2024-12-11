package store.domain;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
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

    public Product findByName(String name) {
        return products.getProducts()
                .stream()
                .filter(product -> product.getName().equals(name))
                .findFirst().get();
    }

    public int getAddedProductCount(Order order) {
        Product product = products.getPromotionalProductByName(order.getName());
        if (product == null) return 0;

        if (product.getQuantity() > order.getQuantity()) return order.getQuantity() / (product.getPromotion().getBuy() + product.getPromotion().getGet());
        return product.getQuantity() / (product.getPromotion().getBuy() + product.getPromotion().getGet());
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

    public void getPromotionProduct(Order order) {
        order.setQuantity(order.getQuantity() + 1);
    }

    public void removeOrder(Order order, int quantity) {
        order.setQuantity(order.getQuantity() - quantity);
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

    public int getNotPromotionQuantity(Order order, Product product) {
        int totalPromotionQuantity = products.getPromotionalProductByName(product.getName()).getQuantity();
        return order.getQuantity() - (totalPromotionQuantity - totalPromotionQuantity % (product.getPromotion().getGet() + product.getPromotion().getBuy()));
    }

    public int getTotalCount() {
        return orders.stream()
                .mapToInt(Order::getQuantity)
                .sum();
    }

    public Money getTotalPrice() {
        int total = orders.stream()
                .mapToInt(order -> {
                    Product product = products.getProductByName(order.getName());
                    return product.getPrice().getPrice() * order.getQuantity();
                }).sum();

        return new Money(total);
    }

    public Money getPromotionDiscount() {
        int total = orders.stream()
                .filter(order -> getAddedProductCount(order) > 0)
                .filter(order -> isValidDate(products.getPromotionalProductByName(order.getName())))
                .mapToInt(order -> {
                    Product product = products.getPromotionalProductByName(order.getName());
                    return product.getPrice().getPrice() * getAddedProductCount(order);
                }).sum();

        return new Money(total);
    }

    public Money getMembership(String membership) {
        if (membership.equals("N")) return new Money(0);
        Money totalPrice = getTotalPrice();
        if ((int) (totalPrice.getPrice() * 0.3) > 8000) return new Money(8000);
        return new Money((int) (totalPrice.getPrice() * 0.3));
    }

    public Money getPayment(String membership) {
        return new Money(getTotalPrice().getPrice() - getPromotionDiscount().getPrice() - getMembership(membership).getPrice());
    }

    private boolean checkPromotionCondition(Product product, int quantity) {
        return (quantity + 1) % (product.getPromotion().getBuy() + product.getPromotion().getGet()) == 0;
    }

    private boolean checkHasNotPromotionCondition(Product product, int quantity) {
        int totalPromotionQuantity = products.getPromotionalProductByName(product.getName()).getQuantity();
        return quantity >= totalPromotionQuantity - (totalPromotionQuantity % (product.getPromotion().getGet() + product.getPromotion().getBuy()));
    }

    private boolean isValidDate(Product product) {
        LocalDate now = DateTimes.now().toLocalDate();
        return now.isAfter(product.getPromotion().getStartDate()) && now.isBefore(product.getPromotion().getEndDate());
    }

}
