package at3.hw.hw2;

import at3.hw.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;


public class RunAllTestsTest {

    //простой тест с рандомным значением
    @RepeatedTest(10)
    @Tag("N-Test")
    void getGradeWithRandomScore() {

        int random = ThreadLocalRandom.current().nextInt(1, 100);
        String result = CheckGrade.getGrade(random);
        String expected = calculateEpectedGrade(random);

        assertEquals(expected, result, "TEST RUN NOT SUCCESSFUL: " + result + " Ожидалось: " + expected);
    }

    @RepeatedTest(10)
    @Tag("N-Test")
    void repeatedTestReverseWord() {
        String[] rev = {"one", "two", "three"};

        String[] result = CheckReverse.reverse(rev);
        String[] expected = {"three", "two", "one"};

        assertArrayEquals(result, expected, "TEST RUN NOT SUCCESSFUL: " + result + " Ожидалось: " + expected);

    }


    @ParameterizedTest
    @Tag("N-Test")
    @CsvSource(value = {
            "15, E",
            "30, D",
            "50, C",
            "75, B",
            "90, A",
            "80, B",
            "60, C",
            "100,A",
            "-5, Error",
            "105, Error"
    }, delimiter = ',')
    @Tag("N-Test")
    void parametrizedTestFromCsv(int score, String expected) {
        String result = CheckGrade.getGrade(score);

        assertEquals(expected, result, "TEST RUN NOT SUCCESSFUL: " + result + " Ожидалось: " + expected);
    }


    @RepeatedTest(10)
    @Tag("N-Test")
    void startMethodCheckHasBug() {

        String[] arr = {"Error", "Fiature", "Task", "BUG"};

        assertTrue(Boolean.parseBoolean(String.valueOf((CheckHasBug.hasBug(arr)))),
                "Результат: " + Arrays.toString(arr) + " Ожидалось : Error, Fiature, Task, BUG");
    }

    @RepeatedTest(10)
    @Tag("N-Test")
    void startCalcAvg() {
        List<Integer> numberList = Arrays.asList(1, 2, 3, 4, 5);
        double expectedAvg = 3.0;
        double actualAvg = CheckCalcAvg.calcAverage(numberList);

        Assertions.assertEquals(expectedAvg, actualAvg, "Results not matched" + actualAvg + " Ожидалось: " + expectedAvg);

    }

    @RepeatedTest(10)
    @Tag("N-Test")
    void startCheckEvenInRange() {

        String expected = "2 4 6 8 10";
        String result = CheckEvenInRange.getEvenInRange(1, 10);

        assertEquals(expected, result, "Evens in range not correct: " + result + " Ожидалось: " + expected);
    }


    @RepeatedTest(10)
    @Tag("N-Test")
    void startIsBlastOF() {

        String expected = "5 4 3 2 1 Поехали!";

        String actual = CheckIsBlastOff.isBlastOff(5);

        assertEquals(expected, actual, "TEST RESULT NOT PASSED: " + actual + " Ожидалось: " + expected);
    }

    @RepeatedTest(10)
    @Tag("N-Test")
    void startCheckAccess() {

        int randomAge = ThreadLocalRandom.current().nextInt(19, 100);

        String expectedResult = "Allowed";
        String actualResult = CheckAccess.checkAccess(randomAge);

        assertEquals(expectedResult, actualResult, "Age is not correct: " + randomAge + " " + actualResult + " Ожидалось: " + expectedResult);

    }

    @RepeatedTest(value = 10)
    @Tag("N-Test")
    void startFindMaxInRange() {

        int[] arrNumbers = {4, 3, 15, 20, 5};
        int expectedMaxNumber = 20;
        int actualResult = CheckFindMaxInRange.findMax(arrNumbers);
        assertEquals(expectedMaxNumber, actualResult, "Max number is not correct: " + actualResult + " Ожидалось: " + expectedMaxNumber);
    }

    @RepeatedTest(10)
    @Tag("N-Test")
    void startCheckIsEven() {

        int randomNum = ThreadLocalRandom.current().nextInt(1, 100);
        assertTrue(CheckIsEven.isEven(randomNum), "A transferred number must be even!!!" + " Переданное число: " + randomNum + " Ожидалось, что число будет четным");
    }

    @RepeatedTest(10)
    @Tag("N-Test")
    void startCheckIsPositive() {

        int numberRandom = ThreadLocalRandom.current().nextInt(-5, 5);
        int[] expNumbers = {0, 1, 2, 3, 4, 5};

        assertTrue(CheckIsPositive.isPositive(numberRandom), "Передано отрицательное число: " + numberRandom + " Ожидается число положительное: " + Arrays.toString(expNumbers));
    }

    @RepeatedTest(10)
    @Tag("N-Test")
    void startCheckRemovedName() {

        List<String> namesInList = new ArrayList<>(Arrays.asList("Вова", "Петя", "Аня", "Зина"));

        List<String> expectedList = new ArrayList<>(Arrays.asList("Вова", "Петя", "Аня"));

        List<String> namesInResult = CheckRemovedName.removeSpecialName(namesInList, "Зина");

        assertTrue(expectedList.equals(namesInResult), "Ожидаемый список: " + expectedList + " Полученный список: " + namesInResult);

    }


    @RepeatedTest(value = 10)
    @Tag("N-Test")
    void startCheckSumToN() {

        int provNumber = 4;
        int expNumber = 10;
        int result = CheckSumToN.sumToN(provNumber);

        assertEquals(expNumber, result, "Ошибка!" + " Полученный результат: " + result + " Ожидалось: " + expNumber);
    }


    //Вспомогательный метод для логики оценки
    private static String calculateEpectedGrade(int score) {
        if (score >= 0 && score <= 20) {
            return "E";
        } else if (score >= 21 && score <= 40) {
            return "D";
        } else if (score >= 41 && score <= 60) {
            return "C";
        } else if (score >= 61 && score <= 80) {
            return "B";
        } else if (score >= 81 && score <= 100) {
            return "A";
        } else {
            return "Error";
        }
    }
}
