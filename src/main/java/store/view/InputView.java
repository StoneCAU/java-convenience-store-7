package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.domain.Product;

public class InputView {
    private final static String NEW_LINE = System.lineSeparator();

    public static String inputOrders() {
        printNewLine();
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        return input();
    }

    public static String inputPromotionApplicable(Product product) {
        printNewLine();
        System.out.printf("현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)%n", product.getName());
        return input();
    }

    private static void printNewLine() {
        System.out.printf(NEW_LINE);
    }

    private static String input() {
        return Console.readLine();
    }
}
