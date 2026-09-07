import org.junit.jupiter.api.Test;

public class CheckHasBugTest {
    @Test
    void checkMessages() {
        String[] myMessages = {"Defect", "Failure", "BUG", "Fiature"};
        System.out.println(CheckFindMaxInRangeTest.CheckHasBug.hasBug(myMessages));
    }
}