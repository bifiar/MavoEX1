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
    public static Factor cptToFactor(Node node, HashMap<String, String> evidenceNameAndVal) {
        Factor factor = new Factor();
        factor.setValueName(node.getName());
        if(!node.getParentsNames().equals("none")) {
            factor.addVarNames(node.getParentsNames());
        }
        factor.addVarName(node.getName());

        HashMap<String, HashMap<String, Double>> cptTable = node.getCptTable();
        Iterator<String> iterMapKeys = cptTable.keySet().iterator();

        int i = 0;
        while (iterMapKeys.hasNext()) { // iterate on cpt keys (like 1,0)
            String key = iterMapKeys.next();
            String keySplited[] = key.split(","); // split 0,0 key to [0][0]


            Iterator<String> iterNodeKeys = cptTable.get(key).keySet().iterator(); //iter for 0->0.2
            while (iterNodeKeys.hasNext()) {
                for (int j = 0; j < keySplited.length; j++) {
                    if(!keySplited[j].equals("none")){
                    factor.addFactorKey(i, keySplited[j]);
                    }
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
     * @param evidenceNameAndVal
     */
    public static void removeIreleventRowsAndcols(Factor factor, HashMap<String, String> evidenceNameAndVal){
        ArrayList<String> varNames=factor.getVarNames();
        ArrayList<Integer> indexToRemove=new ArrayList<>();
        for(int i=0;i<varNames.size();i++){
            if(evidenceNameAndVal.containsKey(varNames.get(i))){
                indexToRemove.add(i);
            }
        }
        ArrayList<ArrayList<String>> factorTable=factor.getFactorTable();
        Iterator<ArrayList<String>> rowIterator=factorTable.iterator();
        Iterator<Double> factorValueIter=factor.getFactorValues().iterator();
        while(rowIterator.hasNext()){
            factorValueIter.next();
            ArrayList<String> cols=rowIterator.next();
            Iterator<String> colsIterator=cols.iterator();
            int i=0; boolean removeRaw=false;
            while (colsIterator.hasNext()){
                String singleKey=colsIterator.next();//TODO check cond two
                if(indexToRemove.contains(i) && evidenceNameAndVal.containsKey(varNames.get(i))) {
                    if(!singleKey.equals(evidenceNameAndVal.get(varNames.get(i)))){
                        removeRaw=true;
                    }
                }
                i++;
            }
            if(removeRaw){
                rowIterator.remove();
                factorValueIter.remove();
            }

        }
        System.out.println("Debug ****before removing cols***"); //
        System.out.println(factor);
        removeIreleventCols(factor , indexToRemove);
    }
    private static void removeIreleventCols(Factor factor,ArrayList<Integer>  indexToRemove){
        ArrayList<ArrayList<String>> factorTable=factor.getFactorTable();
        for (int i=0; i<indexToRemove.size();i++){
            indexToRemove.set(i,indexToRemove.get(i)-(i));
        }
        for (ArrayList<String> aFactorTable : factorTable) {
            for (Integer anindexToRemove : indexToRemove) {
                aFactorTable.remove((int) anindexToRemove);
            }
        }
        for (Integer anindexToRemove : indexToRemove) {
            factor.removeFactorVarName(anindexToRemove);
        }
    }


}
