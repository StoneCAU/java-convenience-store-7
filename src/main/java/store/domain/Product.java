package store.domain;

import java.util.List;

public class Product {
    private String name;
    private Money price;
    private int quantity;
    private Promotion promotion;

    public Product(List<String> productsInfo, Promotions promotions) {
        this.name = productsInfo.get(0);
        this.price = new Money(Integer.parseInt(productsInfo.get(1)));
        this.quantity = Integer.parseInt(productsInfo.get(2));
        this.promotion = promotions.getPromotionByName(productsInfo.get(3));
    }

    public String getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public Promotion getPromotion() {
        return promotion;
    }
}
