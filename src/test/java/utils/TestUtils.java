package utils;

import java.util.List;
import java.util.Random;

public class TestUtils {

    public static  <T> T getRandomValue(List<T> listValues) {
        Random random = new Random();

        return listValues.get(random.nextInt(listValues.size()));
    }
}
