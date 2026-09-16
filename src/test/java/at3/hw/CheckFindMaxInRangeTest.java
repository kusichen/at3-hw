package at3.hw;

import at3.hw.CheckFindMaxInRange;
import org.junit.jupiter.api.Test;

public class CheckFindMaxInRangeTest {
    @Test
    void findMaxNumberInRange() {
        int[] numberRange = {17, 1, 2, 10, 28};
        System.out.println(CheckFindMaxInRange.findMax(numberRange));
    }
}