package store.domain;

import java.util.List;

public class Orders {
    private List<Order> orders;

    public Orders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Order> getOrders() {
        return orders;
    }
}