import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class CheckReverseTest {

    @Test
    void checkReverse() {
        String[] rev = {"One", "Two", "Three"};
        System.out.println(Arrays.toString(CheckReverse.reverse(rev)));
    }
}
