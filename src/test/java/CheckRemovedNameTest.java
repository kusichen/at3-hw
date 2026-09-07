import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CheckRemovedNameTest {
    @Test
    void checkRemovedName() {
        List<String> nameList = new ArrayList<>();

        nameList.add("Петя");
        nameList.add("Маша");
        nameList.add("Коля");

        String nameRemoved = "Коля";

        System.out.println(CheckFindMaxInRangeTest.CheckRemovedName.removeSpecialName(nameList, nameRemoved));
    }
}
