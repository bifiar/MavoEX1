import java.util.ArrayList;

/** input file manualy, for test read input file only
 * Created by Ofir on 1/20/2017.
 */
public class InputNanually {
    public static BayesinNetwork bayesinNetwork2(){
        //input2.txt

        BayesinNetwork bayesinNetwork=new BayesinNetwork();
        Node a=new Node("A");
        bayesinNetwork.addNode("A",a);
        a.addValue("true");
        a.addValue("false");
        a.generateCptLine("=true,0.1");


        Node b=new Node("B");
        bayesinNetwork.addNode("B",b);
        b.addValue("set");
        b.addValue("noset");
        b.addValue("maybe");
        b.generateCptLine("=set,0.2,=noset,0.5");

        Node c=new Node("C");
        bayesinNetwork.addNode("C",c);
        c.addValue("go");
        c.addValue("stay");
        c.addValue("run");
        c.addParent(bayesinNetwork.getNode("A"));
        c.addParent(bayesinNetwork.getNode("B"));
        c.generateCptLine("true,set,=go,0.25,=stay,0.7");
        c.generateCptLine("true,noset,=go,0.2,=stay,0.6");
        c.generateCptLine("true,maybe,=go,0.3,=stay,0.2");
        c.generateCptLine("false,set,=go,0.55,=stay,0.15");
        c.generateCptLine("false,noset,=go,0.28,=stay,0.3");
        c.generateCptLine("false,maybe,=go,0.45,=stay,0.25");

        ArrayList<QueryAnsFormat> queryAnsFormats=new ArrayList<>();
        queryAnsFormats.add(bayesinNetwork.getQueryAns("P(C=run|B=set,A=true),1"));
        queryAnsFormats.add(bayesinNetwork.getQueryAns("P(C=run|B=set,A=true),2"));
        queryAnsFormats.add(bayesinNetwork.getQueryAns("P(C=run|B=set,A=true),3"));
        queryAnsFormats.add(bayesinNetwork.getQueryAns("P(A=true|C=run),1"));
        queryAnsFormats.add(bayesinNetwork.getQueryAns("P(A=true|C=run),2"));

        WriteOutPutFile.writeOutPutFile("output.txt",queryAnsFormats);
        return bayesinNetwork;
    }

    public static BayesinNetwork bayesinNetwork1(){
        BayesinNetwork bayesinNetwork=new BayesinNetwork();
        Node b=new Node("B");
        bayesinNetwork.addNode("B",b);
        b.addValue("true");
        b.addValue("false");
        b.generateCptLine("=true,0.001");


        Node e=new Node("E");
        bayesinNetwork.addNode("E",e);
        e.addValue("true");
        e.addValue("false");
        e.generateCptLine("=true,0.002");

        Node a=new Node("A");
        bayesinNetwork.addNode("A",a);
        a.addValue("true");
        a.addValue("false");
        a.addParent(bayesinNetwork.getNode("B"));
        a.addParent(bayesinNetwork.getNode("E"));
        a.generateCptLine("true,true,=true,0.95");
        a.generateCptLine("true,false,=true,0.94");
        a.generateCptLine("false,true,=true,0.29");
        a.generateCptLine("false,false,=true,0.001");

        Node j=new Node("J");
        bayesinNetwork.addNode("J",j);
        j.addValue("true");
        j.addValue("false");
        j.addParent(bayesinNetwork.getNode("A"));
        j.generateCptLine("true,=true,0.9");
        j.generateCptLine("false,=true,0.05");

        Node m=new Node("M");
        bayesinNetwork.addNode("M",m);
        m.addValue("true");
        m.addValue("false");
        m.addParent(bayesinNetwork.getNode("A"));
        m.generateCptLine("true,=true,0.7");
        m.generateCptLine("false,=true,0.01");

//        ArrayList<QueryAnsFormat> queryAnsFormats=new ArrayList<>();
//        queryAnsFormats.add(bayesinNetwork.getQueryAns("P(B=true|J=true,M=true),1"));
//        queryAnsFormats.add(bayesinNetwork.getQueryAns("P(B=true|J=true,M=true),2"));
//        queryAnsFormats.add(bayesinNetwork.getQueryAns("P(B=true|J=true,M=true),3"));
//        queryAnsFormats.add(bayesinNetwork.getQueryAns("P(J=true|B=true),1"));
//        queryAnsFormats.add(bayesinNetwork.getQueryAns("P(J=true|B=true),2"));
//
//        WriteOutPutFile.writeOutPutFile("output.txt",queryAnsFormats);

        return bayesinNetwork;
    }
}
