import java.util.ArrayList;
import java.util.List;

public class CheckRemovedName {

    public static List<String> removeSpecialName(List<String> list, String nameToRemove) {
        List<String> newResultList = new ArrayList<>();
        if (list == null) return newResultList;

        for (String name : list) {
            if (nameToRemove == null) {
                if (name != null)
                    newResultList.add(name);
            } else if (!nameToRemove.equals(name)) {
                newResultList.add(name);
            }
        }
        return newResultList;
    }
}
