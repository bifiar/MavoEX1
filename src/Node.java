import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/** class Node for one Entity like "Alarm"
 * Created by Ofir on 1/17/2017.
 */
class Node {
    private HashMap<String,String> values;// key go value 1
    private ArrayList<Node> parents;
    private Cpt cpt;
    private String name;

    Node(String name) {
        this.values=new HashMap<>();
        this.parents=new ArrayList<>();
        this.cpt=new Cpt();
        this.name=name;
    }
    void addValue(String value){
        values.put(value,String.valueOf(values.size()));
    }
    private String getValueIndex(String value){
        return values.get(value);
    }
    void addParent(Node parent){
        parents.add(parent);
    }
    Set<String> getValuesNames(){
        return values.keySet();
    }

    public HashMap<String, String> getValues() {
        return values;
    }

    HashMap<String, HashMap<String,Double>> getCptTable() {
        return cpt.getTableCpt();
    }
    ArrayList<String> getParentsNames(){
        ArrayList<String> parentsNames=new ArrayList<>();
        for(Node parent:parents){
            parentsNames.add(parent.getName());
        }
        return parentsNames;
    }
    ArrayList<Node> getParents() {
        return parents;
    }

    String getName() {
        return name;
    }

    /**
     * creat input cpt line for cpt object
     * @param inputLine
     */
    void  generateCptLine(String inputLine){
        String afterParse[]=inputLine.split(",");
        String cptKey="";

        double sumValue=0;int i=0;
         boolean varFlag=!(parents.size()==0);
        if(!varFlag){
            cptKey="none";
        }
        while(i<afterParse.length){
            if(varFlag){
                cptKey+=getIndexForValue(afterParse[i],i)+',';
                i++;
            }else{
                int sSize=afterParse[i].length();
                double val= Double.valueOf(afterParse[i+1]);
                cpt.insetLine(cptKey,values.get(afterParse[i].substring(1,sSize)),val);
                sumValue+=val;
                i+=2;
            }//change bool
            if(i==(parents.size())){
                varFlag=false;
                cptKey=cptKey.substring(0,cptKey.length()-1);
            }
        }
        //mashlim
        cpt.insetLine(cptKey,Integer.toString(values.size()-1),1-sumValue);
    }

    /**
     * from P(a|b,e) to answer
     * @param query
     * @return
     */
    double getQueryAnswer(String query){
        String queryVars="";
        String splitedQuery[]=query.split("(,)|(=)");
        String queryVarValue=getValueIndex(splitedQuery[1]);
        if(splitedQuery.length==2){
            return cpt.getValue("none",queryVarValue);
        }
        for (int i = 2; i <splitedQuery.length ; i=i+2) {
            Node parentNode=null;
            for(Node parent:parents){
                if(parent.getName().equals(splitedQuery[i])){
                    parentNode=parent;
                    break;
                }
            }
            queryVars+=parentNode.getValueIndex(splitedQuery[i+1])+",";
        }
        return cpt.getValue(queryVars.substring(0,queryVars.length()-1),queryVarValue);
    }



    private String getIndexForValue(String value, int index){
        return  parents.get(index).getValueIndex(value);
    }

    @Override
    public String toString() {
        return "Node{" +
                ", values=" + values +
                ", cpt=" + cpt +
                '}';
    }
}
