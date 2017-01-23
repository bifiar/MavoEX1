
import java.util.HashMap;

/**
 * Created by Ofir on 1/17/2017.
 */
public class ex1 {
    public static void main(String[] args) {
      //  ReadInputFile.readFileLineByLine("input.txt");
        HashMap<String,String> evdinceVarNameAndVal=new HashMap<>();

        System.out.println("input 2");
        BayesinNetwork bn1=InputNanually.bayesinNetwork1();
        Factor f1=CptToFactor.factorForHidden(bn1.getNode("A"),evdinceVarNameAndVal);
        System.out.println(f1);
        evdinceVarNameAndVal.put("A","1");
        evdinceVarNameAndVal.put("E","1");
        CptToFactor.removeIreleventRows(f1,bn1.getNode("A"),evdinceVarNameAndVal);
        System.out.println(f1);

//        System.out.println("input 2");
//        BayesinNetwork bn2=InputNanually.bayesinNetwork2();
//        Factor f2=CptToFactor.factorForHidden(bn2.getNode("C"),evdinceVarNameAndVal);
//        System.out.println(f2);
    }
}
