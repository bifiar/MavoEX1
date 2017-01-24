
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Ofir on 1/17/2017.
 */
public class ex1 {
    public static void main(String[] args) {
      //  ReadInputFile.readFileLineByLine("input.txt");
        HashMap<String,String> evdinceVarNameAndVal=new HashMap<>();

        System.out.println("input 1");
        BayesinNetwork bn1=InputNanually.bayesinNetwork1();
        Factor f1=CptToFactor.cptToFactor(bn1.getNode("A"),evdinceVarNameAndVal);
        System.out.println(f1);
        List<String> list=new ArrayList<>();list.add("0");list.add("1");list.add("2");
        String t="T";
        JoinFactors.addNodeToFactor(f1,t,list);
        System.out.println("after addnig line\n"+f1);
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
