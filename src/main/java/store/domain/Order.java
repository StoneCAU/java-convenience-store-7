package store.domain;

public class Order {
    private int quantity;
    private String name;
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

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }
}
