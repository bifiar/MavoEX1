import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
        ArrayList<String> newHiddenVarNames= factors.stream().filter(factor -> hiddens.contains(factor.valueName)).map(factor -> factor.valueName).collect(Collectors.toCollection(ArrayList::new));
        return newHiddenVarNames;
    }
}
