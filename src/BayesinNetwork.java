import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**contain all nodes in one Bayesin Network
 * Created by Ofir on 1/17/2017.
 */
class BayesinNetwork {
    private HashMap<String, Node> bayesinNetwork;// Node name->Node

    BayesinNetwork(){
        this.bayesinNetwork=new HashMap<>();
    }
    void addNode(String keyForNode, Node node){
        bayesinNetwork.put(keyForNode,node);
    }
    Node getNode(String keyForNode){
        return bayesinNetwork.get(keyForNode);
    }
    Set<String> getNodesNames(){
        return bayesinNetwork.keySet();
    }
    List<Node>  getNodesValues(){
        return new ArrayList<>(bayesinNetwork.values());
    }
    public boolean isNode(String keyForNode){
        return bayesinNetwork.containsKey(keyForNode);
    }

    /**
     * get Query Ans by Algorithem number
     * @param queryAndAlgo
     * @return
     */
    QueryAnsFormat getQueryAns(String queryAndAlgo){
        QueryAnsFormat ans = null;
        try {
            String queryAlgoSplited[] = queryAndAlgo.split("\\)");
            queryAlgoSplited[1] = queryAlgoSplited[1].replace(",", "");
            queryAlgoSplited[0] = queryAlgoSplited[0] + ")";
            switch (queryAlgoSplited[1]) {
                case "1":
                    ans = AlgoOne.ansForQuery(queryAlgoSplited[0], this);
                    break;
                case "2":
                    ans = AlgoTwo.ansForQuery(queryAlgoSplited[0], this, 2);
                    break;
                case "3":
                    ans = AlgoTwo.ansForQuery(queryAlgoSplited[0], this, 3);
                    break;
            }
        }catch (Exception ex){
            ans=null;
        }
        return ans;
    }

    @Override
    public String toString() {
        return "BayesinNetwork{" +
                "bayesinNetwork=" + bayesinNetwork +
                '}';
    }
}
