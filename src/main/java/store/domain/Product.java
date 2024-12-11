package store.domain;

import java.util.ArrayList;
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

    public Product(List<String> productInfo) {
        this.name = productInfo.get(0);
        this.price = new Money(Integer.parseInt(productInfo.get(1)));
        this.quantity = 0;
        this.promotion = null;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public List<String> getProductInfo() {
        List<String> productInfo = new ArrayList<>();
        productInfo.add(name);
        productInfo.add(Integer.toString(price.getPrice()));
        productInfo.add(Integer.toString(quantity));

        return productInfo;
    }

    @Override
    public String toString() {
        if (promotion != null)
            return "- " + name + " " + price + "원 " + getQuantityString() + " " + promotion.getName();
        return "- " + name + " " + price + "원 " + getQuantityString();
    }

    private String getQuantityString() {
        if (quantity == 0) return "재고 없음";
        return quantity + "개";
    }
}
