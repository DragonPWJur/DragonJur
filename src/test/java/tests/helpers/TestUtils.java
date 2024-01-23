package tests.helpers;

public final class TestUtils {

    public static int getInt(String text) {
        return Integer.parseInt(text);
    }

    public static String getString(int number) {
        return Integer.toString(number);
    }

    public static String add(String text, int number) {
        return getString(getInt(text) + number);
    }

    public static double getPercentageOfNumber(int forNumber, int fromNumber) {
        return Math.round(((double) forNumber / fromNumber * 100) * 10) / 10.0;
    }
}
