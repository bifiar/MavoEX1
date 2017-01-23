import java.util.*;

/**
 * Created by Ofir on 1/22/2017.
 */
public class CptToFactor {
    /**
     * @param node               node to convert
     * @param evidenceNameAndVal "E"->0 for E=0 evidance (const)
     * @return
     */
    public static Factor factorForHidden(Node node, HashMap<String, String> evidenceNameAndVal) {
        Factor factor = new Factor();
        factor.addVarNames(node.getParentsNames());
        factor.addVarName(node.getName());
      //  addReleventsVarNames(factor, node, evidenceNameAndVal);// adding relevents var names to factor

        HashMap<String, HashMap<String, Double>> cptTable = node.getCptTable();
        Iterator<String> iterMapKeys = cptTable.keySet().iterator();

        int i = 0;
        while (iterMapKeys.hasNext()) { // iterate on cpt keys (like 1,0)
            String key = iterMapKeys.next();
            String keySplited[] = key.split(","); // split 0,0 key to [0][0]


            Iterator<String> iterNodeKeys = cptTable.get(key).keySet().iterator(); //iter for 0->0.2
            while (iterNodeKeys.hasNext()) {
                for (int j = 0; j < keySplited.length; j++) {
                    factor.addFactorKey(i, keySplited[j]);
                }
                String valueNodeKey = iterNodeKeys.next();
                factor.addFactorKey(i++, valueNodeKey);
                factor.addFactorValue(cptTable.get(key).get(valueNodeKey));
            }

        }
        return factor;
    }

    /**
     *
     * @param factor
     * @param node
     * @param evidenceNameAndVal
     */
    public static void removeIreleventRows(Factor factor,Node node, HashMap<String, String> evidenceNameAndVal){
        ArrayList<String> varNames=factor.getVarNames();

        ArrayList<Integer> indexToRemvoe=new ArrayList<>();
        for(int i=0;i<varNames.size();i++){
            if(evidenceNameAndVal.containsKey(varNames.get(i))){
                indexToRemvoe.add(i);
            }
        }

        ArrayList<ArrayList<String>> factorTable=factor.getFactorTable();
        Iterator<ArrayList<String>> rowIterator=factorTable.iterator();
        Iterator<Double> factorValueIter=factor.getFactorValues().iterator();
        while(rowIterator.hasNext()){
            factorValueIter.next();
            ArrayList<String> cols=rowIterator.next();
            Iterator<String> colsIterator=cols.iterator();
            int i=0,countRemoveRow=0;
            while (colsIterator.hasNext()){
                String singleKey=colsIterator.next();
                if(indexToRemvoe.contains(i) && evidenceNameAndVal.containsKey(varNames.get(i))) {
                    if(!singleKey.equals(evidenceNameAndVal.get(varNames.get(i)))){
                        countRemoveRow++;
                    }
                }
                i++;
            }
            if(countRemoveRow==indexToRemvoe.size()){
                rowIterator.remove();
                factorValueIter.remove();
            }

        }
        System.out.println("before removing cols");
        System.out.println(factor);
        removeIreleventCols(factor , indexToRemvoe);
    }
    public static void removeIreleventCols(Factor factor,ArrayList<Integer>  indexToRemvoe){
        ArrayList<ArrayList<String>> factorTable=factor.getFactorTable();
        for (int i=0; i<indexToRemvoe.size();i++){
            indexToRemvoe.set(i,indexToRemvoe.get(i)-(i));
        }
        for (int i=0; i<factorTable.size();i++){
            for(int j=0;j<indexToRemvoe.size();j++) {
                factorTable.get(i).remove((int)indexToRemvoe.get(j));
            }
        }
        for (int i = 0; i <indexToRemvoe.size() ; i++) {
            factor.removeFactorVarName(indexToRemvoe.get(i));
        }
    }


}
