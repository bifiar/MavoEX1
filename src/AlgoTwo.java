import java.util.*;

/**
 * Created by Ofir on 1/22/2017.
 */
//P(B=true|J=true,M=true)
public class AlgoTwo{//TODO Kadmon father
    private static FactorManager factorManager=new FactorManager();
    private static BayesinNetwork bayesinNetwork;
    public static QueryAnsFormat ansForQuery(BayesinNetwork bayesinNetwork,String query){
        AlgoTwo.bayesinNetwork=bayesinNetwork;
        HashMap<String,String> evidance=generateEvidance(query);
        buidFactorManager(evidance);
        ArrayList<String> hiddensNames= generateHiddens(query);
//        for(String hiddenName:hiddensNames) {
//            ArrayList<Factor> factorsByName = factorManager.getFactorByValueName(hiddenName);
//            Factor afterJoin = new Factor();
//            if(factorsByName.size()>1){
//                Factor firstFactor = factorsByName.get(0);
//                Factor seconedFactor = factorsByName.get(1);
//            }
//            for(Factor factor : factorsByName ){
//                afterJoin = JoinFactors.joinFactors()
//            }
//
//        }

        System.out.println(factorManager);



      return null;
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
    private static void buidFactorManager(HashMap<String,String> evidences){
        List<Node> nodes=bayesinNetwork.getNodesValues();
        for(Node node:nodes){
            factorManager.addFactor(CptToFactor.cptToFactor(node,evidences));
        }
    }






}
