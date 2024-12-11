package store.domain;

public class Money {
    private int price;

    public Money(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return String.format("%,d원", price);
    }
}
