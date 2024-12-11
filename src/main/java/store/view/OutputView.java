package store.view;

public class OutputView {
    private static final String NEW_LINE = System.lineSeparator();

    private static void printNewLine() {
        System.out.printf(NEW_LINE);
    }

    public static void printErrorMessage(String message) {
        printNewLine();
        System.out.println(message);
    }
}
