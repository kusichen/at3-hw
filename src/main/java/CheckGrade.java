public class CheckGrade {

    public static String getGrade(int score) {

        if (score >= 0 && score <= 20) {
            return "0-20: E";
        } else if (score >= 21 && score <= 40) {
            return "21-40: D";
        } else if (score >= 41 && score <= 60) {
            return "41-60: C";
        } else if (score >= 61 && score <= 80) {
            return "61-80: B";
        } else if (score >= 81 && score <= 100) {
            return "81-100: A";
        } else {
            return "Error";
        }
    }
}
