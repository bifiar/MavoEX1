import java.util.ArrayList;

/**
 * Created by Ofir on 1/22/2017.
 */
public class FactorManager {
    private ArrayList<Factor> factors;

    public FactorManager() {
        this.factors = new ArrayList<>();
    }

    public void addFactor(Factor f){
        factors.add(f);
    }
    public Factor getFactor(int index){
       return factors.get(index);
    }
}
