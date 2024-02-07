package pages;

import com.microsoft.playwright.Locator;

import java.util.List;
import java.util.Random;

interface IRandom {

    default <T> T getRandomValue(List<T> listValues) {
        Random random = new Random();

        return listValues.get(random.nextInt(listValues.size()));
    }

    default int getRandomNumber(Locator locator) {
        if (locator.count() == 0) {
            return 0;
        }

        return new Random().nextInt(1, locator.count());
    }

    default int getRandomNumber(List<Locator> list) {

        return new Random().nextInt(0, list.size() - 1);
    }

    default int getRandomInt(int min, int max) {
        Random random = new Random();

        return random.nextInt(max - min) + min;
    }

    default int getRandomInt(int max) {
        Random random = new Random();

        return random.nextInt(1, max + 1);
    }

    default String getRandomTextValue(Locator locator) {

        return locator.all().get(getRandomNumber(locator)).innerText();
    }

    default String getRandomString(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < length; i++) {
            randomString.append(chars.charAt(new Random().nextInt(chars.length())));
        }

        return randomString.toString();
    }

    default void clickRandomElement(Locator locator) {
        int randomValue = getRandomNumber(locator);
        locator.nth(randomValue).click();
    }
}
