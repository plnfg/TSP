package tsp;

import java.util.ArrayList;
import java.util.Collections;





public class Individual {
    private final int GeneLength=10;
    ArrayList<Integer> Genes=new ArrayList<>(GeneLength);;
    private int fitness;
    

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    
    

     void  setGenes() {
        int i;
        for (i = 1; i <= GeneLength; i++) {
            Genes.add(i);
        }
        Collections.shuffle(Genes);

    }

     
     
     
  
    
    
}
