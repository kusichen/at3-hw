import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CheckFindMaxInRangeTest {
    @Test
    void findMaxNumberInRange() {
        int[] numberRange = {17, 1, 2, 10, 28};
        System.out.println(CheckFindMaxInRange.findMax(numberRange));
    }

    public static class CheckAccess {

        public static String checkAccess(int age){
            if(age > 18) {
                return "Allowed";
            }
            else {
                return "Denied";
            }
        }
    }

    public static class CheckCalcAvg {

        public static double calcAverage(List<Integer> list){
            if(list == null || list.isEmpty()){
                return 0.0;
            }
            double sum = 0;
            for(int num: list) {
                sum += num;
            }
            return sum/list.size();
        }
    }

    public static class CheckEvenInRange {
        public static  String getEvenInRange(int start, int end) {

            StringBuilder myString = new StringBuilder();

            for(int i = start; i <= end; i++) {
                if(i%2 == 0){
                    if(myString.length() > 0) {
                        myString.append(" ");
                    }
                    myString.append(i);
                }
            }
            return myString.toString();
        }
    }

    public static class CheckFindMaxInRange {
        public static int findMax(int[] arr) {
            int max = arr[0];

            for (int i = 1; i < arr.length; i++) {
                if (arr[i] > max) {
                    max = arr[i];
                }
            }
            return max;
        }
    }

    public static class CheckGrade {

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

    public static class CheckHasBug {

        public static boolean hasBug(String[]messages){
            if(messages == null)return false;
            for(String msg : messages){
                if(msg != null && msg.equalsIgnoreCase("Bug")){
                    return true;
                }
            }
            return false;
        }
    }

    public static class CheckIsBlastOff {
        public static String isBlastOff(int start) {
            StringBuilder newString = new StringBuilder();

            for (int i = start; i >= 1; i--) {
                newString.append(i).append(" ");
            }
            newString.append("Поехали!");
            return newString.toString();
        }
    }

    public static class CheckIsEven {
        public static boolean isEven(int n){
            return n % 2 ==0;
        }
    }

    public static class CheckIsPositive {
        public static boolean isPositive(int n){
            return  n >=0;
        }
    }

    public static class CheckRemovedName {

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

    public static class CheckReverse {

        public static String[] reverse(String[] arr){
            if(arr== null)return null;
            String[] reverseWords = new String[arr.length];

            for(int i = 0; i < arr.length; i++){
                reverseWords[i]= arr[arr.length-1 -i];
            }
            return reverseWords;
        }
    }

    public static class CheckSumToN {

        public static int sumToN(int n) {
            int sum = 0;

            for (int i = 1; i <= n; i++) {
                sum += i;
            }
            return sum;
        }
    }
}