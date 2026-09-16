package at3.hw.hw2;

import at3.hw.CheckAccess;
import at3.hw.CheckGrade;
import at3.hw.CheckIsEven;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CheckThreeClassesFromFirstLesson {
    @BeforeEach
    void init(){

            System.out.println("====================Test method start");

    }

    @Test
    void checkFirstTestFromFirstFirstLesson(){
        int random = ThreadLocalRandom.current().nextInt(1,100);

        System.out.println(CheckIsEven.isEven(random));
    }

    @RepeatedTest(value = 20)
    void checkSecondTestFromFirstLesson(){

        int randomAge = ThreadLocalRandom.current().nextInt(0,99);

        System.out.println(CheckAccess.checkAccess(randomAge));
    }

    //генератор случайных чисел от 0 до 100
    private static Stream<Integer> randomScores(){
        Random random = new Random();
        return IntStream.range(0,100)
                .map(i -> random.nextInt(101))
                .boxed();
    }

    //Проверяем случайные числа
    @ParameterizedTest
    @MethodSource("randomScores")
    void testGetGradeWithRandomScores(int score){
        String actualGrade = CheckGrade.getGrade(score);
        System.out.println(actualGrade);
    }

    @AfterEach
    void close(){
        System.out.println("Test metod end\n" +
                "\n" +
                "=================");
    }

}
