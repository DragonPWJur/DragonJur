package pages;

import com.microsoft.playwright.Locator;

import java.util.List;
import java.util.Random;

 interface IRandom {

     default <T> T getRandomValue(List<T> listValues) {
        Random random = new Random();

        return listValues.get(random.nextInt(listValues.size()));
    }

     default int getRandomNumber(Locator list) {
        if (list.count() == 0) {
            return 0;
        }

        return new Random().nextInt(1, list.count());
    }

    default int getRandomNumber(List<Locator> list) {

        return new Random().nextInt(0, list.size() - 1);
    }


    default int getRandomInt(int min, int max) {
        Random random = new Random();

        return random.nextInt(max - min) + min;
    }

}
