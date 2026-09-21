package at3.hw.hw2;

import at3.hw.*;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RunAllTestsTest {

    @RepeatedTest(10)
    void repeatedTestReverseWord() {
        String[] rev = {"one", "two", "three"};

        String[] result = CheckReverse.reverse(rev);
        String[] expected = {"three", "two", "one"};

        if (Arrays.equals(result, expected)) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }

    }

    @ParameterizedTest
    @CsvSource(value = {
            "15, E",
            "30, D",
            "50, C",
            "75, B",
            "90, A",
            "-5, Error",
            "105, Error"
    }, delimiter = ',')
        /* @CsvFileSource(resources = "/test_scores.csv",numLinesToSkip = 1)*/
    void parametrizedTestFromCsv(int score, String expected) {
        String result = CheckGrade.getGrade(score);

        if (result.equals(expected)) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }


    @Test
    void startMethodCheckHasBug() {

        if (!CheckHasBug.hasBug(new String[]{"Task", "Fiature", "BUG", "Error"})) {
            System.out.println("TEST FAILED");
        } else {
            System.out.println("TEST PASSED");
        }
    }


    @Test
    void startCheckCalcAvg() {
        List<Integer> numberList = Arrays.asList(1, 2, 3, 4, 5);
        double expectedAvg = 3.0;
        double actualAvg = CheckCalcAvg.calcAverage(numberList);
        if (actualAvg == expectedAvg) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");

        }
    }

    @Test
    void startCheckEvenInRange() {

        String result = CheckEvenInRange.getEvenInRange(1, 10);
        if (result.equals("2 4 6 8 10")) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }

    @RepeatedTest(2)
    void startIsBlastOF() {

        String expected = "5 4 3 2 1 Поехали!";

        String actual = CheckIsBlastOff.isBlastOff(5);

        if (actual.equals(expected)) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }


    @Test
    void startCheckAccess() {

        int randomAge = ThreadLocalRandom.current().nextInt(0, 100);

        String expectedAccessAge = CheckAccess.checkAccess(randomAge);

        if (expectedAccessAge.equals("Allowed")) {
            System.out.println("TEST PASSED " + "Возраст для входа удовлетворяет требования и равен:  " + randomAge);
        } else {
            System.out.println("TEST FAILED " + "Возраст для входа не удовлетворяет требования и равен: " + randomAge);
        }
    }

    @Test
    void startFindMaxInRange() {

        int[] arrNumbers = {4, 3, 15, 20, 5};
        int expectedMaxNumber = 20;
        int result = CheckFindMaxInRange.findMax(arrNumbers);
        if (result == expectedMaxNumber) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }

    @Test
    void startCheckIsEven() {

        int randomNum = ThreadLocalRandom.current().nextInt(1, 100);

        if (CheckIsEven.isEven(randomNum)) {
            System.out.println("TEST PASSED " + "Четное: " + randomNum);
        } else {
            System.out.println("TEST FAILED " + "Нечетное: " + randomNum);
        }
    }

    @Test
    void startCheckIsPositive() {

        int numberRandom = ThreadLocalRandom.current().nextInt(-5, 5);

        if (CheckIsPositive.isPositive(numberRandom)) {
            System.out.println("TEST PASSED");
            System.out.println("Положительное число: " + numberRandom);
        } else {
            System.out.println("TEST FAILED");
            System.out.println("Отрицательное число: " + numberRandom);
        }
    }

    @Test
    void startCheckRemovedName() {

        List<String> namesInList = new ArrayList<>(Arrays.asList("Вова", "Петя", "Аня", "Зина"));
        List<String> namesInResult = CheckRemovedName.removeSpecialName(namesInList, "Зина");
        if (namesInResult.size() == 3 && namesInResult.contains("Вова") && namesInResult.contains("Петя")
                && namesInResult.contains("Аня") && !namesInResult.contains("Зина")) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }

    @Test
    void startCheckSumToN() {

        int rN = ThreadLocalRandom.current().nextInt(2, 6);
        if (CheckSumToN.sumToN(rN) == 3) {
            System.out.println("TEST PASSED");
        } else if (CheckSumToN.sumToN(rN) == 6) {
            System.out.println("TEST PASSED");
        } else if (CheckSumToN.sumToN(rN) == 10) {
            System.out.println("TEST PASSED");
        } else if (CheckSumToN.sumToN(rN) == 15) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
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
