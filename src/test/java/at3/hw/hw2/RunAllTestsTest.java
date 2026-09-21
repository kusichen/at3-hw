package at3.hw.hw2;

import at3.hw.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


public class RunAllTestsTest {

    //простой тест с рандомным значением
    @RepeatedTest(10)
    @Tag("N-Test")
    void getGradeWithRandomScore(){

        int random = ThreadLocalRandom.current().nextInt(1,100);
        String result = CheckGrade.getGrade(random);
        String expected = calculateEpectedGrade(random);

        assertEquals(expected,result,"TEST PASSED");
    }

    @RepeatedTest(10)
    @Tag("N-Test")
    void repeatedTestReverseWord(){
        String[] rev = {"one","two","three"};

        String[] result = CheckReverse.reverse(rev);
        String[] expected = {"three","two","one"};

        assertArrayEquals(result,expected,"TEST PASSED");

    }


    @ParameterizedTest
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
   /* @CsvFileSource(resources = "/test_scores.csv",numLinesToSkip = 1)*/
    void parametrizedTestFromCsv(int score, String expected){
        String result = CheckGrade.getGrade(score);

        assertEquals(expected,result,"TEST PASSED");
    }


   @RepeatedTest(10)
   @Tag("N-Test")
    void startMethodCheckHasBug(){

        String[] arr = {"BUG","Error","Fiature"};

        assertTrue(Boolean.parseBoolean(String.valueOf(CheckHasBug.hasBug(arr))),"TEST PASSED");
    }


    @ParameterizedTest
    @Tag("N-Test")
    @ValueSource(ints = {1})
    void startCalcAvg(Integer c){

        Assertions.assertEquals(1.0,CheckCalcAvg.calcAverage(Collections.singletonList(c)),"TESSED PASSED");

    }

    @RepeatedTest(value = 10)
    @Tag("N-Test")
    void startEvenInRange(){

        final Random random = new Random();

        int start = random.nextInt(1);
        int end = start + random.nextInt(100);

        String actualResult = CheckEvenInRange.getEvenInRange(start, end);

        String expectedResult = IntStream.rangeClosed(start, end)
                .filter(i -> i % 2 == 0)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(" "));

        Assertions.assertEquals(expectedResult, actualResult,
                String.format("Ошибка для диапазона от %d до %d", start, end));
    }


    @RepeatedTest(10)
    void startIsBlastOF() {

        String[] expected = {"5", "4", "3", "2", "1"};

        String[] result = new String[]{CheckIsBlastOff.isBlastOff(5)};

        assertArrayEquals(expected,result,"TEST PASSED");
    }

    //Вспомогательный метод для проверки возраста
    private static Stream<Arguments> agesForAccess() {
        return Stream.of(
                Arguments.of(19, "Allowed"),
                Arguments.of(20, "Allowed"),
                Arguments.of(25, "Allowed"),
                Arguments.of(50, "Allowed"),
                Arguments.of(100, "Allowed"),

                // 5 проверок для запрещенного доступа (включая граничное значение 18)
                Arguments.of(18, "Denied"),
                Arguments.of(17, "Denied"),
                Arguments.of(10, "Denied"),
                Arguments.of(5, "Denied"),
                Arguments.of(0, "Denied")
        );
    }

    @ParameterizedTest
    @Tag("N-Test")
    @MethodSource("agesForAccess")
    void shouldCheckAccessCorrectly(int age, String expectedResult) {

        String actualResult = CheckAccess.checkAccess(age);

        assertThat(actualResult)
                .as("Проверка возраста для пользователя ", age)
                .isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    @Tag("N-Test")
    void testSumToNRepeated(int iteration) {

        assertEquals(0, CheckSumToN.sumToN(0), "Ошибка если будет 0");
        assertEquals(1, CheckSumToN.sumToN(1), "Ошибка если будет 1");
        assertEquals(15, CheckSumToN.sumToN(5), "Ошибка если будет 5");
        assertEquals(55, CheckSumToN.sumToN(10), "Ошибка если будет 10");
    }

    @ParameterizedTest
    @Tag("N-Test")
    @ValueSource(strings = {
            "Мария", "Мария", "Мария", "Мария", "Мария",
            "Мария", "Мария", "Мария", "Мария", "Мария"
    })
    void removeName(String nameToRemove) {

        List<String> inputList = List.of("Иван", "Мария", "Петр");
        List<String> expectedResult = List.of("Иван", "Петр");
        List<String> actualResult = CheckRemovedName.removeSpecialName(inputList, nameToRemove);

        Assertions.assertEquals(expectedResult,actualResult,"Имя удалено");


    }


    //Вспомогательный метод для логики оценки
    private static String calculateEpectedGrade(int score){
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
