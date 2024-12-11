package store.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import store.domain.Product;
import store.domain.Products;
import store.domain.Promotion;
import store.domain.Promotions;
import store.exception.ErrorMessage;
import store.exception.StoreException;

public class FileLoader {
    private final static String PRODUCTS_FILE_NAME = "products.md";
    private final static String PROMOTIONS_FILE_NAME = "promotions.md";

    public static Products getProducts() {
        Promotions promotions = getPromotions();
        List<Product> productList = getProductsInfo().stream()
                .skip(1)
                .map(info -> new Product(parseInfo(info), promotions))
                .toList();

        return new Products(productList);
    }

    private static Promotions getPromotions() {
        List<Promotion> promotionList = getPromotionsInfo().stream()
                .skip(1)
                .map(info -> new Promotion(parseInfo(info)))
                .toList();

        return new Promotions(promotionList);
    }

    public static List<String> getPromotionsInfo() {
        return loadMarkdownFile(PROMOTIONS_FILE_NAME);
    }

    private static List<String> getProductsInfo() {
        return loadMarkdownFile(PRODUCTS_FILE_NAME);
    }

    private static List<String> loadMarkdownFile(String fileName) {
        try (InputStream inputStream = FileLoader.class.getClassLoader().getResourceAsStream(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            return reader.lines().collect(Collectors.toList());

        } catch (IOException e) {
            throw new StoreException(ErrorMessage.INVALID_FILE_CONTENT);
        }
    }

    private static List<String> parseInfo(String input) {
        return Arrays.stream(input.split(",")).toList();
    }
}
