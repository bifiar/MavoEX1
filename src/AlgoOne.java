import java.util.*;

/**Algo one implementation
 * Created by Ofir on 1/20/2017.
 */
class AlgoOne {
    private static BayesinNetwork bayesinNetwork;
    private static int numofPlus=0; // number of plus
    private static int numofMulti=0;//number of multi

    /**
     * return answer for query like: P(b,j ⌐m)
     * @param query
     * @param bayesinNetwork
     * @return
     */
    static QueryAnsFormat ansForQuery(String query, BayesinNetwork bayesinNetwork){
        AlgoOne.bayesinNetwork = bayesinNetwork;
        numofPlus=0;numofMulti=0;

        double numerator=generateQuery(query);
        System.out.println(query+" "+generateQuery(query));
        String formatQuery = query.replace("(", "").replace(")", "").replace("P", "").replace("|", ",");
        String splitedQuery[]=formatQuery.split("(,)|(=)");
        String queryName=splitedQuery[0];
        String queryVal=splitedQuery[1];
        Node queryVarNode=bayesinNetwork.getNode(queryName);
        Set<String> values=queryVarNode.getValuesNames();
        double denominator=numerator;
        for(String value:values){
            if(!(value.equals(queryVal))) {
                String queryReplaced = query.replace(queryName + "=" + queryVal, queryName + "=" + value);
                System.out.println(queryReplaced+" "+generateQuery(queryReplaced));
                denominator += generateQuery(queryReplaced);
                numofPlus++;
            }
        }
        QueryAnsFormat queryAns=new QueryAnsFormat(numerator/denominator,numofPlus,numofMulti);
        return queryAns;
    }

    /**
     *return answer for query like: P(b,j ⌐m) allPossibilities
     * @param query
     * @return
     */
    private static double generateQuery(String query) {
        double ans=0;
        String formatQuery = query.replace("(", "").replace(")", "").replace("P", "").replace("|", ",");
        HashMap<String, Set<String>> hiddenVars = generateHiddenVars(formatQuery);
        if(hiddenVars.size()==0){
            ArrayList<String> queryLine= generateQueryLine(formatQuery);
            return calculateLine(queryLine);
        }
        ArrayList<String> possibilitiesQuery = allPossibilities(formatQuery, hiddenVars);
        boolean assignfirst=true;
        for(String posQuery :possibilitiesQuery){
            ArrayList<String> queryLine= generateQueryLine(posQuery);
            if(assignfirst){
                ans=calculateLine(queryLine);
                assignfirst=false;
            }
            else{
                ans+=calculateLine(queryLine);
                numofPlus++;
            }
        }
        return ans;
    }

    /**
     * generate hidden vars for query
     * @param query
     * @return
     */
    private static HashMap<String, Set<String>> generateHiddenVars(String query) {
        HashMap<String, Set<String>> hiddenVars = new HashMap<>();
        Set<String> setOfKeyNodes = bayesinNetwork.getNodesNames();
        for (String keyNode : setOfKeyNodes) {
            if (!query.contains(keyNode + "=")) {
                Node hidden = bayesinNetwork.getNode(keyNode);
                hiddenVars.put(keyNode, hidden.getValuesNames());
            }
        }
        return hiddenVars;
    }

    /**
     * generate all posobilites for query
     * @param query
     * @param hiddenVars
     * @return
     */
    private static ArrayList<String> allPossibilities(String query, HashMap<String, Set<String>> hiddenVars) {
        ArrayList<String> possibilitiesQuery = new ArrayList<>();
        int[] lengths = new int[hiddenVars.size()];
        ArrayList<Set<String>> hiddenValues = new ArrayList<>(hiddenVars.values());
        for (int i = 0; i < hiddenValues.size(); i++) {
            lengths[i] = hiddenValues.get(i).size();
        }
        CartesianProduct cp = new CartesianProduct(lengths);
        for (int[] indices : cp) {
            String newQuery = query;
            Iterator<String> iterValName = hiddenVars.keySet().iterator();
            for (int i = 0; i < hiddenValues.size(); i++) {
                String valName = iterValName.next();
                ArrayList<String> listOfVals = new ArrayList<>(hiddenValues.get(i));
                newQuery += "," + valName + "=" + listOfVals.get(indices[i]);
            }

            possibilitiesQuery.add(newQuery);
        }

        return possibilitiesQuery;
    }

    /**
     * generate from P(b,e,a,j,⌐m) to P(b)P(e)P(a|b,e)P(j|a)P(⌐m|a)
     * @param line
     * @return
     */
    private static ArrayList<String> generateQueryLine(String line) {
        ArrayList<String> qureysForLine=new ArrayList<>();
        List<String> varsAndNames= Arrays.asList(line.split("(,)|(=)"));
        List<String> varsAndValues= Arrays.asList(line.split(","));
        for (int i = 0; i < varsAndValues.size(); i++) {
            String query=varsAndValues.get(i);
            String nameAndValue[] = query.split("=");
            Node currentNode = bayesinNetwork.getNode(nameAndValue[0]);
            ArrayList<Node> parents = currentNode.getParents();
            for (Node node : parents) {
                int indexOnVarsAndNames=varsAndNames.indexOf(node.getName());
                if(indexOnVarsAndNames!=-1){//indexOnVarsAndNames
                    query+=","+varsAndNames.get(indexOnVarsAndNames)+"="+varsAndNames.get(indexOnVarsAndNames+1);               }
            }
            qureysForLine.add(query);
        }
        return qureysForLine;
    }

    /**
     * calc one query line like "P(b)P(e)P(a|b,e)P(j|a)P(⌐m|a)"
     * @param queryLine
     * @return
     */
    private static double calculateLine(ArrayList<String> queryLine){
        double ans=1;
        boolean assignFirst=true;
        for (String varNameVal: queryLine) {
            String nameValSplited[]=varNameVal.split("=");
            Node node=bayesinNetwork.getNode(nameValSplited[0]);
            if(assignFirst) {
                ans= node.getQueryAnswer(varNameVal);
                assignFirst=false;
            }else {
                ans *= node.getQueryAnswer(varNameVal);
                numofMulti++;
            }
        }
        return ans;
    }
}