package store.view;

import store.domain.Money;
import store.domain.Orders;
import store.domain.Products;

public class OutputView {
    private static final String NEW_LINE = System.lineSeparator();

    public static void printWelcomeMessage(Products products) {
        System.out.println("안녕하세요. W편의점입니다.");
        printProducts(products);
    }

    public static void printProducts(Products products) {
        printNewLine();
        System.out.println("현재 보유하고 있는 상품입니다.");
        products.getProducts().forEach(System.out::println);
    }

    public static void printResults(Orders orders) {
        printNewLine();
        System.out.println("===========W 편의점=============");
        printOrders(orders);
        printAdded(orders);
    }

    public static void printErrorMessage(String message) {
        printNewLine();
        System.out.println(message);
    }

    private static void printOrders(Orders orders) {
        System.out.println("상품명\t\t수량\t금액");
        orders.getOrders()
                .forEach(order ->
                        System.out.println(order.getName() + "\t\t" + order.getQuantity() + "\t" + new Money(orders.findByName(order.getName()).getPrice().getPrice() * order.getQuantity())
                        ));
    }

    private static void printAdded(Orders orders) {
        System.out.println("===========증\t정=============");
        orders.getOrders()
                .stream().filter(order -> orders.getAddedProductCount(order) > 0)
                .forEach(order -> System.out.println(order.getName() + "\t\t" + orders.getAddedProductCount(order)));
    }

    private static void printNewLine() {
        System.out.printf(NEW_LINE);
    }
}
