public class CheckIsBlastOff {
    public static String isBlastOff(int start) {
        StringBuilder newString = new StringBuilder();

        for (int i = start; i >= 1; i--) {
            newString.append(i).append(" ");
        }
        newString.append("Поехали!");
        return newString.toString();
    }
}