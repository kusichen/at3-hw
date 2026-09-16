package at3.hw.hw3;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckAssertionsTest {

    public boolean hasNoEvenNumbers(int[] numbers) {
        for (int num : numbers) {
            if (num % 2 != 0) {
                return true;
            }
        }
        return false;
    }

    @Test
    void testHasNoEvenNumbers() {
        // Успешный случай: в массиве только нечетные числа
        int[] noEvens = {1, 7, 9, 17};
        assertTrue(hasNoEvenNumbers(noEvens), "Массив содержит нечетные числа");

    }

    @Test
    void testListContainsValue() {

        List < String> list = new ArrayList<>();

        list.add("Apple");

        String expectedValue = "Apple";

        // Проверяем, что элемент присутствует в списке
        assertTrue(list.contains(expectedValue),
                "Список должен содержать элемент: " + expectedValue);

    }

    @Test
    public void testFailed() {
        int result = sum(7, 1);
        assertEquals(9, result, "Неверный подсчет значений");
    }

    private int sum(int a, int b) {
        return a + b;
    }

    @Test
     void checkArrayLengthLimit() {
        List<String> list = new ArrayList<>();
        int maxLimit = 4;

        list.add("Vaz");
        list.add("Volga");
        list.add("Niva");

        // 3. Assert (Проверка) — ожидаем, что размер списка (3) меньше или равен лимиту (5)
        assertTrue(list.size() <= maxLimit,
                "Размер массива превысил допустимый лимит: " + maxLimit);
    }

}


