import java.util.*;

/**
 * Created by Ofir on 1/22/2017.
 */
//P(B=true|J=true,M=true)
public class AlgoTwo{//TODO Kadmon father
    private static FactorManager factorManager=new FactorManager();
    private static BayesinNetwork bayesinNetwork;
    public static QueryAnsFormat ansForQuery(String query,BayesinNetwork bayesinNetwork){
        JoinFactors.countMulti=0; JoinFactors.countPlus=0;
        factorManager=new FactorManager();
        AlgoTwo.bayesinNetwork=bayesinNetwork;
        String queryVarNameAndValue[]=generateVarNameAndValue(query);
        HashMap<String,String> evidance=generateEvidance(query);
        buidFactorManager(evidance,queryVarNameAndValue[0]);
        ArrayList<String> hiddensNames= generateHiddens(query);

        Collections.sort(hiddensNames);
        for(String hiddenName:hiddensNames) {
             // Pick a hidden varibale H
            ArrayList<Factor> factorsByName =new ArrayList<>(factorManager.getFactorByValueName(hiddenName));//sorted by factor varName size
            if(factorsByName.size()<2){
                if(factorsByName.size()==1) {
                    JoinFactors.eliminateVar(factorsByName.get(0), hiddenName);
                }
                continue;
            }
            Factor joinedFactor = joinAllFactors(bayesinNetwork, factorsByName);//join Hidden Factors
            JoinFactors.eliminateVar(joinedFactor,hiddenName); // Eliminate H

            factorManager.addFactor(joinedFactor);

        }
        ArrayList<Factor> allRemainingFactors=factorManager.getFactors();
        Factor joinedFactor;
        if(allRemainingFactors.size()>=2){//join remaining Factors
            joinedFactor = joinAllFactors(bayesinNetwork, allRemainingFactors);
        }else{
            joinedFactor=allRemainingFactors.get(0);
        }

        QueryAnsFormat ans=queryAns(joinedFactor,queryVarNameAndValue);

      return ans;
    }
    private static QueryAnsFormat queryAns(Factor factor,String[] queryVarNameAndValue){
        double numerator=0.0;int countPlus=0;
        ArrayList<ArrayList<String>> factorTable=factor.getFactorTable();
        ArrayList<Double> factorVals=factor.getFactorValues();

        for(int i=0;i<factorTable.size();i++){
            if(factorTable.get(i).get(0).equals(queryVarNameAndValue[1])){
                numerator=factorVals.get(i);
            }

        }
        double denominator=numerator;
        for(int i=0;i<factorTable.size();i++){
            if(!factorTable.get(i).get(0).equals(queryVarNameAndValue[1])){
                denominator+=factorVals.get(i);
                countPlus++;
            }

        }
        QueryAnsFormat ans=new QueryAnsFormat((numerator/denominator),countPlus+JoinFactors.countPlus,JoinFactors.countMulti);
        return ans;
    }

    private static Factor joinAllFactors(BayesinNetwork bayesinNetwork, ArrayList<Factor> factorsToJoin) {
        Factor joinedFactors=null;
        while (factorsToJoin.size()>1){// join all factors  mentioning  H
            Collections.sort(factorsToJoin);
            Factor f1=factorsToJoin.remove(0);
            Factor f2=factorsToJoin.remove(0);
            factorManager.removeFactor(f1);//remove factors from factor Manager
            factorManager.removeFactor(f2);
            joinedFactors=JoinFactors.joinFactors(f1,f2,bayesinNetwork);
            factorsToJoin.add(joinedFactors);
        }
        return joinedFactors;
    }

    private static String[] generateVarNameAndValue(String query){
        String spiltedQuery[]=query.replace("P","").replace("(","").replace(")","").replace("|",",").split(",");
        String varNameAndVal[]=spiltedQuery[0].split("=");
        varNameAndVal[1]=bayesinNetwork.getNode(varNameAndVal[0]).getValues().get(varNameAndVal[1]);
        return varNameAndVal;
    }
    private static HashMap<String,String> generateEvidance(String query){
        HashMap<String,String> evidences=new HashMap<>();
        String spiltedQuery[]=query.replace("P","").replace("(","").replace(")","").replace("|",",").split(",");
        for (int i = 1; i <spiltedQuery.length ; i++) {
            String splitedVar[]=spiltedQuery[i].split("=");
            Node node=bayesinNetwork.getNode(splitedVar[0]);
            String varValue=node.getValues().get(splitedVar[1]);
            evidences.put(splitedVar[0],varValue);
        }
        return evidences;
    }
    private static ArrayList<String> generateHiddens(String query){
        ArrayList<String> notHiddens=new ArrayList<>();
        ArrayList<String> hiddens=new ArrayList<>();
        String spiltedQuery[]=query.replace("P","").replace("(","").replace(")","").replace("|",",").split(",");
        for (int i = 0; i <spiltedQuery.length ; i++) {
            String splitedVar[]=spiltedQuery[i].split("=");
            notHiddens.add(splitedVar[0]);
        }
        Set<String> nodes = bayesinNetwork.getNodesNames();
        for (String nodeName : nodes) {
            if(!notHiddens.contains(nodeName)){
                hiddens.add(nodeName);
            }
        }
        return hiddens;
    }
    private static void buidFactorManager(HashMap<String,String> evidences,String queryVarName){
        List<Node> nodes=bayesinNetwork.getNodesValues();
        removeNotAncestorNode(nodes,evidences.keySet(),queryVarName);
        for(Node node:nodes){
            factorManager.addFactor(CptToFactor.cptToFactor(node,evidences));
        }
    }
    private static void removeNotAncestorNode( List<Node> nodes,Set<String> evidences,String queryVarName){
        ArrayList<String> newEvidences=new ArrayList<>(evidences);
        newEvidences.add(queryVarName);
        List<Node> candidates=new ArrayList<>();
        for(Node node:nodes){
            String nodeName=node.getName();
            if(!newEvidences.contains(nodeName)){
                candidates.add(node);
            }
        }

        for(Node candidate:candidates){
            boolean isAnchoster=false;
            for(String evidanceOrQueryVar:newEvidences){
                if(isAncestor(candidate,evidanceOrQueryVar)){
                    isAnchoster=true;
                }
            }
            if(!isAnchoster){
                nodes.remove(candidate);
            }
        }

    }
    private static boolean isAncestor(Node candidate,String varOrEvidance){
        Node varOrEvidanceNode=bayesinNetwork.getNode(varOrEvidance);
        ArrayList<Node> parents=varOrEvidanceNode.getParents();

        if(parents.contains(candidate)){
            return  true;
        }
        else{
            for(Node parent : parents){
                return isAncestor(candidate, parent.getName());
            }

        }
        return false;
    }
}
