public class CheckHasBug {

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
