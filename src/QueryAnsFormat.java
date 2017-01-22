import java.text.DecimalFormat;

/** for output file answer line
 * ans+','+numOfPlus+","+numOfMulti
 * Created by Ofir on 1/22/2017.
 */
class QueryAnsFormat {
    private String ans;
    private int numOfPlus;
    private int numOfMulti;

    QueryAnsFormat(double ans, int numOfPlus, int numOfMulti) {
        this.ans = new DecimalFormat("#.#####").format(ans);
        this.numOfPlus = numOfPlus;
        this.numOfMulti = numOfMulti;
    }
    @Override
    public String toString() {
        return ans+','+numOfPlus+","+numOfMulti;
    }
}
