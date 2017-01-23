import java.util.ArrayList;

/**
 * Created by Ofir on 1/23/2017.
 */
public class JoinFactors {
    public static void joinFactors(Factor f1,Factor f2){
        ArrayList<ArrayList<String>> table1=f1.getFactorTable();
        ArrayList<ArrayList<String>> table2=f2.getFactorTable();
        ArrayList<String> varNames1=f1.getVarNames();
        ArrayList<String> varNames2=f2.getVarNames();
        ArrayList<Double> values1=f1.getFactorValues();
        ArrayList<Double> values2=f1.getFactorValues();


    }
}
