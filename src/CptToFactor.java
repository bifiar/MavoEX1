import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Ofir on 1/22/2017.
 */
public class CptToFactor {

    public static Factor factorForHidden(Node node,HashMap<String,String> evidenceNameAndVal){
        Factor factor=new Factor();
        factor.addVarNames(node.getParentsNames());
        factor.addVarName(node.getName());
        HashMap<String,HashMap<String,Double>> cptTable=node.getCptTable();
        Iterator<String> iterMapKeys=cptTable.keySet().iterator();

        int i=0;
        while (iterMapKeys.hasNext()){
            String key=iterMapKeys.next();
            String keySplited[]=key.split(","); // split 0,0 key

            Iterator<String> iterNodeKeys=cptTable.get(key).keySet().iterator(); //iter for 0->0.2
            while (iterNodeKeys.hasNext()){
                for (int j = 0;j<keySplited.length ; j++) {
                    factor.addFactorKey(i,keySplited[j]);
                }
                String valueNodeKey=iterNodeKeys.next();
                factor.addFactorKey(i++,valueNodeKey);
                factor.addFactorValue(cptTable.get(key).get(valueNodeKey));
            }

        }
        return factor;
    }

    public static Factor factorForQueryVar(Node node){

        return null;
    }
    public static Factor factorforEvidence(Node node,String evidenceValue){

        return null;
    }
}
