package at3.hw.hw2;

import at3.hw.*;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class RunAllTestsTest {

    //простой тест с рандомным значением
    @Test
    void getGradeWithRandomScore(){

        int random = ThreadLocalRandom.current().nextInt(1,100);
        String result = CheckGrade.getGrade(random);
        String expected = calculateEpectedGrade(random);

        if(result.equals(expected)){
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }

    @RepeatedTest(10)
    void repeatedTestReverseWord(){
        String[] rev = {"one","two","three"};

        String[] result = CheckReverse.reverse(rev);
        String[] expected = {"three","two","one"};

        if(Arrays.equals(result,expected)){
            System.out.println("TEST PASSED");
        }else {
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
    void parametrizedTestFromCsv(int score, String expected){
        String result = CheckGrade.getGrade(score);

        if(result.equals(expected)){
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }


   @Test
    void startMethodCheckHasBug(){

        String[] arr = {"BUG","Error","Fiature"};

        String expected = "BUG";
        String result = String.valueOf(CheckHasBug.hasBug(arr));
        if(result.equals(expected)){
            System.out.println("TEST PASSED");
        }
        else{
            System.out.println("TEST FAILED");
        }


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

        if (result.equals(expected)) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
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
