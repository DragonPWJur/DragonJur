package utils;

import java.util.Random;

public class TestUtils {

    public static  <T> T getRandomValue(T[] array) {
        Random random = new Random();

        return array[random.nextInt(array.length)];
    }
}
