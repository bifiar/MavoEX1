import java.util.ArrayList;

/**
 * Created by Ofir on 1/22/2017.
 */
public class FactorManager {
    private ArrayList<Factor> factors;

    public FactorManager() {
        this.factors = new ArrayList<>();
    }
    public void removeFactor(Factor f){factors.remove(f);}
    public void addFactor(Factor f){
        factors.add(f);
    }
    public Factor getFactor(int index){
       return factors.get(index);
    }
    public ArrayList<Factor> getFactorByValueName(String varName){
        ArrayList<Factor> factorsByName=new ArrayList<>();
        for(Factor factor:factors){
            if(factor.getVarNames().contains(varName)){
                factorsByName.add(factor);
            }
        }
        return factorsByName;
    }

    @Override
    public String toString() {
        return "FactorManager\n" +
                 factors +
                '}';
    }
}
