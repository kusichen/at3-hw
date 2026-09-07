import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CheckCalcAvgTest {

    @Test
    void checkCalcAvg(){
        List<Integer> myNumbers = new ArrayList<>();

        myNumbers.add(1);
        myNumbers.add(2);
        myNumbers.add(3);

        System.out.println(CheckFindMaxInRangeTest.CheckCalcAvg.calcAverage(myNumbers));
    }
}