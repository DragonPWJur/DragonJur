package tests.helpers;

import java.util.Random;

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

    public static String getRandomString(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < length; i++) {
            randomString.append(chars.charAt(new Random().nextInt(chars.length())));
        }

        return randomString.toString();
    }
}
