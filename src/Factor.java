import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Ofir on 1/22/2017.
 */
public class Factor implements Comparable<Factor>{
    private ArrayList<ArrayList<String>> factorTable;
    private ArrayList<Double> factorValues;
    private ArrayList<String> varNames;
    String valueName=""; // "P(M|A)"
    private static int factorSeqNum=0;
    private int seqNumber;

    public Factor() {
        factorTable=new ArrayList<>();
        factorValues=new ArrayList<>();
        varNames=new ArrayList<>();
        seqNumber=factorSeqNum++;
    }
    public Factor(Factor other){
        factorTable=new ArrayList<>(other.getFactorTable());
        factorValues=new ArrayList<>(other.getFactorValues());
        varNames=new ArrayList<>(other.getVarNames());
        seqNumber=factorSeqNum++;
    }
    public int getVarNameIndex(String name){
        return varNames.indexOf(name);
    }

    public void setFactorTable(ArrayList<ArrayList<String>> factorTable) {
        this.factorTable = factorTable;
    }
    public void setValue(int index,Double value){
        factorValues.set(index,value);
    }
    public Double getValue(int index){
        return factorValues.get(index);
    }

    public void setFactorValues(ArrayList<Double> factorValues) {
        this.factorValues = factorValues;
    }

    public ArrayList<String> getVarNames() {
        return varNames;
    }

    public void removeFactorValues(int index) {
       factorValues.remove(index);
    }

    public ArrayList<Double> getFactorValues() {
        return factorValues;
    }
    public void removeFactorVarName(int index){
        varNames.remove(index);
    }

    public void addFactorKey(int i, String key){
        try {
            factorTable.get(i).add(key);

        }catch (IndexOutOfBoundsException ex){
            factorTable.add(new ArrayList<>());
            factorTable.get(i).add(key);
        }
    }
    public void addFactorValue(double val){
        factorValues.add(val);
    }
    public void addVarNames(ArrayList<String> varNames){
        this.varNames.addAll(varNames);
    }
    public void addVarName(String name){
        varNames.add(name);
    }

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public static int getFactorSeqNum() {
        return factorSeqNum;
    }

    public ArrayList<ArrayList<String>> getFactorTable() {
        return factorTable;
    }

    @Override
    public String toString() {
        return
                "\nseqNumber= " +seqNumber+
                "\nfactorTable=" + factorTable +
                "\nfactorValues=" + factorValues +
                "\nvarNames=" + varNames
                ;
    }



    @Override
    public int compareTo(Factor o) {
        int ans=0;
        if(this.getVarNames().size()>o.getVarNames().size()){
            ans=1;
        }else if(this.getVarNames().size()<o.getVarNames().size()){
            ans=-1;
        }
        return ans;
    }
}
