package store.domain;

public class Order {
    private String name;
    private int quantity;
    private boolean isAdded;

    public Order(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
        this.isAdded = false;
    }

    public Order(String name, int quantity, boolean isAdded) {
        this.name = name;
        this.quantity = quantity;
        this.isAdded = isAdded;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }
}
