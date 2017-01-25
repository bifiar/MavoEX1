import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Ofir on 1/23/2017.
 */
public class JoinFactors {



    public static Factor joinFactors(Factor f1, Factor f2,BayesinNetwork bayesinNetwork){
        if(f1.getVarNames().size()<f2.getVarNames().size()) {
            Factor tempFactor = f1;
            f1 = f2;
            f2 = tempFactor;
        }
        ArrayList<String> varNames1=f1.getVarNames();
        ArrayList<String> varNames2=f2.getVarNames();

        for(String name:varNames2){
            if(!varNames1.contains(name)){
                addNodeToFactor(f1,name,bayesinNetwork.getNode(name).getValuesNumbers());
            }
        }
       return mlutipaleMatchRows(f1,f2);

    }
    public static void addNodeToFactor(Factor factor,String nodeName,List<String> values){
        ArrayList<Double> factorValues=factor.getFactorValues();
        ArrayList<Double> newFactorValues=new ArrayList<>();
        ArrayList<ArrayList<String>> factorTable=factor.getFactorTable();
        ArrayList<ArrayList<String>> newFactorTable=new ArrayList<>();
        int i=0;
        for(ArrayList<String> factorRow:factorTable){
            for(String value:values){
                ArrayList<String> newRow=new ArrayList<>(factorRow);
                newRow.add(value);
                newFactorTable.add(newRow);
                newFactorValues.add(factorValues.get(i));
            }
            i++;
        }
        factor.addVarName(nodeName);
        factor.setFactorTable(newFactorTable);
        factor.setFactorValues(newFactorValues);

    }
    public static Factor mlutipaleMatchRows(Factor f1,Factor f2){// f1 after join rows from f2(small)
        if(f1.getVarNames().size()<f2.getVarNames().size()) {
            Factor tempFactor = f1;
            f1 = f2;
            f2 = tempFactor;
        }
        ArrayList<ArrayList<String>> factorTable1=f1.getFactorTable();
        ArrayList<ArrayList<String>> factorTable2=f2.getFactorTable();
        ArrayList<String> varNames1=f1.getVarNames();
        ArrayList<String> varNames2=f2.getVarNames();

        HashMap<Integer,Integer> matchF2ToF1=new HashMap<>();

        for (int i = 0; i <varNames1.size() ; i++) {
            int index=varNames2.indexOf(varNames1.get(i));
            if(index!=-1){
                matchF2ToF1.put(index,i);
            }
        }
        for (int i = 0; i <factorTable2.size() ; i++) {

            for (int j = 0; j <factorTable1.size() ; j++) {
              if(isMatchRow(factorTable1.get(j),factorTable2.get(i),matchF2ToF1)){
                  double newVal=f1.getValue(j)*f2.getValue(i);
                  f1.setValue(j,newVal);
              }
            }

        }


        return new Factor(f1);
    }
    private static boolean isMatchRow(ArrayList<String> rowF1,ArrayList<String> rowF2
                                        ,HashMap<Integer,Integer> matchIndecies){
        int countMatch=0;
        for (int i = 0; i <rowF2.size() ; i++) {
            if(rowF1.get(matchIndecies.get(i)).equals(rowF2.get(i))){
                countMatch++;
            }
        }
        return rowF2.size()==countMatch;
    }
    public static void eliminateVar(Factor factor,String varName){
        int indexToRemove=factor.getVarNameIndex(varName);
        ArrayList<Integer> toRemove=new ArrayList<>();
        toRemove.add(indexToRemove);
        CptToFactor.removeIreleventCols(factor,toRemove);
        ArrayList<ArrayList<String>> factorTable=factor.getFactorTable();
        ArrayList<Double> factorValues=factor.getFactorValues();
        ArrayList<Double> newfactorValues=new ArrayList<>();
        ArrayList<ArrayList<String>> newFactorTable=new ArrayList<>();
        int k=-1;
        for (int i = 0; i <factorTable.size()-1 ; i++) {
            if(newFactorTable.contains(factorTable.get(i))){continue;}
            boolean firstTimeAdd=true;
            for (int j = i+1; j <factorTable.size() ; j++) {
                if(factorTable.get(i).equals(factorTable.get(j))){
                    if(firstTimeAdd) {
                        newFactorTable.add(new ArrayList<>(factorTable.get(i)));
                        newfactorValues.add(factorValues.get(i) + factorValues.get(j));// TODO sum plus
                        k++;
                        firstTimeAdd=false;
                    }else{
                        newfactorValues.set(k,newfactorValues.get(k)+factorValues.get(j));
                    }
                }
            }
        }
        factor.setFactorTable(newFactorTable);
        factor.setFactorValues(newfactorValues);
    }
}
