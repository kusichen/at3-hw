package at3.hw;

import at3.hw.CheckHasBug;
import org.junit.jupiter.api.Test;

public class CheckHasBugTest {
    @Test
    void checkMessages() {
        String[] myMessages = {"Defect", "Failure", "BUG", "Fiature"};
        System.out.println(CheckHasBug.hasBug(myMessages));
    }
}