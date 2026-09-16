package at3.hw.hw2;

import at3.hw.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

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

    @Test
    void startOtherMethod()
    {
        CheckCalcAvg calcAvg = new CheckCalcAvg();





        if(calcAvg.equals(14.0)){
            System.out.println("TEST PASSED");
        }
        else{
            System.out.println("TEST FAILED");
        }

        CheckEvenInRange evenInRange = new CheckEvenInRange();


        if(evenInRange.equals(2)){
            System.out.println("TEST PASSED");
        }
        else{
            System.out.println("TEST FAILED");
        }


    }

    @RepeatedTest(2)
    void startIsBlastOF() {

        String[] expected = {"5", "4", "3", "2", "1"};

        String[] result = new String[]{CheckIsBlastOff.isBlastOff(5)};

        assertArrayEquals(expected,result,"TEST PASSED");
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
