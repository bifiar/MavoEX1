/**Read input file in ex format
 * Created by Ofir on 1/17/2017.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

abstract class ReadInputFile {
    private static Node currentNode;
    private static BayesinNetwork bayesinNetwork;
    private static final String outFileName="output.txt";

    static void readFileLineByLine(String fileName){
        List<String> list;

        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {

            //br returns as stream and convert it into a List
            list = br.lines().collect(Collectors.toList());
            parseListToBayesin(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void parseListToBayesin(List<String> list){
        if(!list.get(0).contains("Network")) {
            throw new RuntimeException("input file must start with:Network");
        }
        bayesinNetwork=new BayesinNetwork();
        int numOfVar= Utils.countOccurrences(list.get(1),',')+1;
        for (int i=3;i<list.size();i++){
            if(list.get(i).startsWith("Var")){
                caseVar(list.get(i));
            }else if(list.get(i).startsWith("Values")){
                caseValues(list.get(i));
            }else if(list.get(i).startsWith("Parents")){
                caseParents(list.get(i));
            }else if(list.get(i).startsWith("CPT")){
                i=caseCPT(list,++i);
            }else if(list.get(i).startsWith("Queries")){
                i=caseQueries(list,++i);
            }
        }

    }
    private static void caseVar(String line){
        String varName=line.replaceFirst("Var ","");
        currentNode=new Node(varName);
        bayesinNetwork.addNode(varName,currentNode);
    }
    private static void caseValues(String line){
        String valLine[]=line.replaceFirst("Values: ","").split(",");
        for (int i=0;i<valLine.length;i++){
        valLine[i]=valLine[i].replaceFirst(" ","");
        currentNode.addValue(valLine[i]);
        }
    }
    private static void caseParents(String line) {
        String parents[] = line.replaceFirst("Parents: ", "").split(",");
        if(parents[0].equals("none")){
            return;
        }
        for (int i = 0; i < parents.length; i++) {
            currentNode.addParent(bayesinNetwork.getNode(parents[i]));
        }
    }
    private static int caseCPT(List<String> list,int startCptIndex){
        String currentLine=list.get(startCptIndex);
       while ((!currentLine.startsWith("Var"))&&(!currentLine.startsWith("Queries"))&&(!currentLine.equals(""))){
           currentNode.generateCptLine(currentLine);
           startCptIndex++;
           currentLine=list.get(startCptIndex);
       }
       return startCptIndex;
    }
    private static int caseQueries(List<String> list,int startCptIndex){
        ArrayList<QueryAnsFormat> queryAnsFormats=new ArrayList<>();
        int i=0;
        for (i=startCptIndex;i<list.size();i++){
            queryAnsFormats.add(bayesinNetwork.getQueryAns(list.get(i)));
        }
        WriteOutPutFile.writeOutPutFile(outFileName,queryAnsFormats); //write to file
        return i;
    }

    public static BayesinNetwork getBayesinNetwork() {
        return bayesinNetwork;
    }
}

