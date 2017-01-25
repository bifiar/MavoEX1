import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Ofir on 1/25/2017.
 */
public class AlgoThree {

    /**
     *
     * @param hiddens
     * @param factorManager
     * @return
     */
    public static ArrayList<String> sortHiddensBySize(ArrayList<String> hiddens,FactorManager factorManager){
        List<Factor> factors= factorManager.getFactors();
        Collections.sort(factors, (o1, o2) -> {
            int ans=0;
            if(o1.getVarNames().size()>o2.getVarNames().size()){
                ans=1;
            }else if(o1.getVarNames().size()>o2.getVarNames().size()){
                ans=-1;
            }
            return ans;
        });
        ArrayList<String> newHiddenVarNames=new ArrayList<>();
        for(Factor factor:factors){
            if(hiddens.contains(factor.valueName)){
                newHiddenVarNames.add(factor.valueName);
            }
        }
        return newHiddenVarNames;
    }
}
