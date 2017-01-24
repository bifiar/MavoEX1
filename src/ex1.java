
import java.util.HashMap;

/**
 * Created by Ofir on 1/17/2017.
 */
public class ex1 {
    public static void main(String[] args) {
      //  ReadInputFile.readFileLineByLine("input.txt");
        HashMap<String,String> evdinceVarNameAndVal=new HashMap<>();

        System.out.println("input 1");
        BayesinNetwork bn1=InputNanually.bayesinNetwork2();
        Factor f1=CptToFactor.cptToFactor(bn1.getNode("C"),evdinceVarNameAndVal);
        System.out.println(f1);
 //       evdinceVarNameAndVal.put("J","0");
        evdinceVarNameAndVal.put("B","0");
        CptToFactor.removeIreleventRowsAndcols(f1,evdinceVarNameAndVal);
        System.out.println(f1);

//        System.out.println("input 2");
//        BayesinNetwork bn2=InputNanually.bayesinNetwork2();
//        Factor f2=CptToFactor.cptToFactor(bn2.getNode("C"),evdinceVarNameAndVal);
//        System.out.println(f2);
    }
}
