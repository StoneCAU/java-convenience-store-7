package store.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import store.domain.Order;
import store.domain.Orders;
import store.domain.Products;
import store.exception.ErrorMessage;
import store.exception.StoreException;

public class InputValidator {
    public static Orders getOrders(Products products, String input) {
        List<Order> orderList = generateOrderList(products, input);
        return new Orders(orderList, products);
    }

    public static String validateReply(String input) {
        if (!input.equals("Y") && !input.equals("YES")) {
            throw new StoreException(ErrorMessage.INVALID_OTHERS);
        }

        return input;
    }

    private static List<String> parseInput(String input) {
        return Arrays.stream(input.split(",")).toList();
    }

    private static List<String> parseOrderString(String input) {
        List<String> parsed = parseInput(input);
        if (!isValidParsed(parsed)) {
            throw new StoreException(ErrorMessage.INVALID_INPUT);
        }
        return parsed.stream()
                .map(str -> str.replaceAll("[\\[\\]]", ""))
                .toList();
    }

    private static List<Order> generateOrderList(Products products, String input) {
        List<String> parsed = parseOrderString(input);
        return parsed.stream()
                .map(str -> {
                    List<String> parsedOrder = parseOrder(str);
                    validateParsedOrder(products, parsedOrder);
                    return new Order(parsedOrder.get(0), getNumber(parsedOrder.get(1)));
                }).collect(Collectors.toList());
    }

    private static List<String> parseOrder(String input) {
        return Arrays.stream(input.split("-")).toList();
    }

    private static void validateParsedOrder(Products products, List<String> parsedOrder) {
        if (!isValidProduct(products, parsedOrder.get(0))) {
            throw new StoreException(ErrorMessage.INVALID_PRODUCT);
        }

        if (!isValidQuantity(products, parsedOrder.get(0), getNumber(parsedOrder.get(1)))) {
            throw new StoreException(ErrorMessage.INVALID_QUANTITY);
        }
    }

    private static int getNumber(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new StoreException(ErrorMessage.INVALID_QUANTITY);
        }
    }

    private static boolean isValidParsed(List<String> parsed) {
        return parsed.stream()
                .allMatch(str -> str.contains("[") && str.contains("]"));
    }

    private static boolean isValidProduct(Products products, String name) {
        return products.getProducts().stream()
                .anyMatch(product -> product.getName().equals(name));
    }

    private static boolean isValidQuantity(Products products, String name, int quantity) {
        return products.getTotalQuantityByName(name) >= quantity;
    }
}
