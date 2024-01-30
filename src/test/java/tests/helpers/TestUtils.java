package tests.helpers;

import com.microsoft.playwright.Locator;

import java.util.Arrays;
import java.util.List;
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

    public static List<Integer> getStatistic(Locator stack) {

        String[] statistic = stack.innerText().replace("%", "").split("\n");
        String packName = statistic[statistic.length - 1];

        return Arrays
                .stream(statistic)
                .filter(x -> !x.contains(packName))
                .map(Integer::parseInt)
                .toList();
    }

    public static int getSum(List<Integer> stack) {

        int sum = 0;
        for (int i : stack) {
            sum += i;
        }
        return (int) sum;
    }
}
