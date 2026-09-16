package at3.hw;

import at3.hw.CheckGrade;
import org.junit.jupiter.api.Test;

public class CheckGradeTest {
    @Test
    void checkGrade(){
        System.out.println("Грейд ученика: " + CheckGrade.getGrade(50));
    }
}
