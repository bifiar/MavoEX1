
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Ofir on 1/17/2017.
 */
public class ex1 {
    public static void main(String[] args) {
      //  ReadInputFile.readFileLineByLine("input.txt");
//        HashMap<String,String> evdinceVarNameAndVal=new HashMap<>();
//        evdinceVarNameAndVal.put("J","0");
//        evdinceVarNameAndVal.put("M","0");
        System.out.println("input 2");
        BayesinNetwork bn2=InputNanually.bayesinNetwork2();
     // AlgoTwo.ansForQuery(bn2,"P(C=run|B=set,A=true)");
       AlgoTwo.ansForQuery(bn2,"P(A=true|C=run)");
//        Factor f1=CptToFactor.cptToFactor(bn1.getNode("J"),evdinceVarNameAndVal);
//        Factor f2=CptToFactor.cptToFactor(bn1.getNode("M"),evdinceVarNameAndVal);
//        Factor f3=CptToFactor.cptToFactor(bn1.getNode("A"),evdinceVarNameAndVal);
//
//
//        Factor f4=JoinFactors.joinFactors(f1,f2,bn1);
//        Factor f5=JoinFactors.joinFactors(f4,f3,bn1);
//        System.out.println("after multipale values");
//        System.out.println(f5);
//
//        JoinFactors.eliminateVar(f5,"A");
//
//        System.out.println(f5);

//        List<String> list=new ArrayList<>();list.add("1");list.add("0");
//        String t="T";
//        JoinFactors.addNodeToFactor(f1,t,list);
//        System.out.println("after Adding line\n"+f1);

//        System.out.println("after multipale values");
//        System.out.println(newFactor);

//        evdinceVarNameAndVal.put("E","1");
//        evdinceVarNameAndVal.put("B","0");
//        CptToFactor.removeIreleventRowsAndcols(f1,evdinceVarNameAndVal);
//        System.out.println(f1);

//        System.out.println("input 2");
//        BayesinNetwork bn2=InputNanually.bayesinNetwork2();
//        Factor f2=CptToFactor.cptToFactor(bn2.getNode("C"),evdinceVarNameAndVal);
//        System.out.println(f2);
    }
}
