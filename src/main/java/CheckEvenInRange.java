public class CheckEvenInRange {
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
