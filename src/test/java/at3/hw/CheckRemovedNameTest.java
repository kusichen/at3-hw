package at3.hw;

import at3.hw.CheckRemovedName;
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

        System.out.println(CheckRemovedName.removeSpecialName(nameList, nameRemoved));
    }
}
