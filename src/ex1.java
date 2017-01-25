
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Ofir on 1/17/2017.
 */
public class ex1 {
    public static void main(String[] args) {

        System.out.println("input 1");
        BayesinNetwork bn1=InputNanually.bayesinNetwork1();

        AlgoTwo.ansForQuery(bn1,"P(B=true|J=true,M=true)");//input 1 query 1 0.28417
        AlgoTwo.ansForQuery(bn1,"P(J=true|B=true)");//input 1 query 2 0.84902

        System.out.println("input 2");
        BayesinNetwork bn2=InputNanually.bayesinNetwork2();


        AlgoTwo.ansForQuery(bn2,"P(C=run|B=set,A=true)"); //input 2 query 1 0.05,2,6
        AlgoTwo.ansForQuery(bn2,"P(A=true|C=run)");//input 2 query 2 0.07429,5,12

    }
}
