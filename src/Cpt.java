import java.util.HashMap;

/** CPT Table
 * Created by Ofir on 1/17/2017.
 */
class Cpt {                                              //parent A B -> Node value->value
    private HashMap<String,HashMap<String,Double>> tableCpt;//key 0,1-> key 0 -> value 0.2

    Cpt() {
       this.tableCpt=new HashMap<>();
    }
    void insetLine(String firstKey, String secondKey, Double value){
        if(!tableCpt.containsKey(firstKey)){
            tableCpt.put(firstKey,new HashMap<>());
        }
        tableCpt.get(firstKey).put(secondKey,value);
    }
    double getValue(String query, String varValue){
        return tableCpt.get(query).get(varValue);
    }

    public HashMap<String, HashMap<String, Double>> getTableCpt() {
        return tableCpt;
    }

    @Override
    public String toString() {
        return "Cpt{" +
                "tableCpt=" + tableCpt +
                '}';
    }
}
