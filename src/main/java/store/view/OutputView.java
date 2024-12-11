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

    public static void printResults(Orders orders, String membership) {
        printNewLine();
        System.out.println("===========W 편의점=============");
        printOrders(orders);
        printAdded(orders);
        printPayment(orders, membership);
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

    private static void printPayment(Orders orders, String membership) {
        System.out.println("==============================");
        System.out.println("총구매액\t\t" + orders.getTotalCount() + "\t" + orders.getTotalPrice());
        System.out.println("행사할인\t\t\t\t\t-" + orders.getPromotionDiscount());
        System.out.println("멤버십할인\t\t\t\t\t-" + orders.getMembership(membership));
        System.out.println("내실돈\t\t\t\t\t" + orders.getPayment(membership));
    }

    private static void printNewLine() {
        System.out.printf(NEW_LINE);
    }
}
