package tests.helpers;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import utils.runner.ProjectProperties;

import java.util.List;
import java.util.Random;

import static utils.reports.LoggerUtils.logInfo;

public final class TestUtils {

    public static boolean isOnPage(String endPoint, Page page) {
        String pageUrl = ProjectProperties.BASE_URL + endPoint;
        int count = 3;
        while (count > 0) {
            if (!page.url().equals(pageUrl)) {
                page.waitForURL(pageUrl);
                count--;
            } else {
                logInfo("On page '" + endPoint + "'");

                return true;
            }
        }

        return page.url().equals(pageUrl);
    }



//    public static int getRandomNumber(Locator list) {
//        if (list.count() == 0) {
//            return 0;
//        }
//
//        return new Random().nextInt(1, list.count());
//    }
//
//    public static int getRandomNumber(List<Locator> list) {
//
//        return new Random().nextInt(0, list.size() - 1);
//    }
//
//
//    public static int getRandomInt(int min, int max) {
//        Random random = new Random();
//
//        return random.nextInt(max - min) + min;
//    }
//
//    public static void clickRandomElement(Locator list) {
//        int randomValue = getRandomNumber(list);
//        list.nth(randomValue).click();
//    }

    public static int getInt(String text) {
        return Integer.parseInt(text);
    }

    public static String getString(int number) {
        return Integer.toString(number);
    }

    public static String add(String text, int number) {
        return getString(getInt(text) + number);
    }

//    public static String getRandomTextValue(Locator listValues) {
//
//        return listValues.all().get(getRandomNumber(listValues)).innerText();
//    }
//
//    public static String geteRandomString(int length) {
//        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
//        StringBuilder randomString = new StringBuilder();
//        for (int i = 0; i < length; i++) {
//            randomString.append(chars.charAt(new Random().nextInt(chars.length())));
//        }
//
//        return randomString.toString();
//    }
//
//    public static double getPercentageOfNumber(int forNumber, int fromNumber) {
//        return Math.round(((double) forNumber / fromNumber * 100) * 10) / 10.0;
//    }
}
