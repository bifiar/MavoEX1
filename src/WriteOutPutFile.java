import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**Write to output file
 * Created by Ofir on 1/22/2017.
 */
class WriteOutPutFile {

    static void writeOutPutFile(String FileName, ArrayList<QueryAnsFormat> answers) {
        BufferedWriter writer =null;
        try {
            writer = new BufferedWriter(new FileWriter(FileName));
            for(QueryAnsFormat ans:answers){
                if(ans==null){
                    writer.write("problem on txt file, continue to next line");
                    writer.newLine();
                }else {
                    writer.write(ans.toString());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
