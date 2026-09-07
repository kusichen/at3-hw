public class CheckReverse {

    public static String[] reverse(String[] arr){
        if(arr== null)return null;
        String[] reverseWords = new String[arr.length];

        for(int i = 0; i < arr.length; i++){
            reverseWords[i]= arr[arr.length-1 -i];
        }
        return reverseWords;
    }
}
