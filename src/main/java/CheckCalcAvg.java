import java.util.List;

public class CheckCalcAvg {

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
