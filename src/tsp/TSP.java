package tsp;




/**
 *
 * @author Pelin
 */
public class TSP {
final int POP_SIZE=8;    
   
    

    public static void main(String[] args) {
        int i;
        for(i=0;i<10;i++){
        runTSP();
        }
    }
    
    
    private static void runTSP(){

        Population pop=new Population();
       
        Individual bestOffspring;
        Individual popBest;
        
        int count=0,i;
        Individual[] parentPop;
        Individual[] childPop=null;
        parentPop=pop.init_population();
        bestOffspring=pop.getFittest(parentPop);
        

        while(true){
            if((bestOffspring.getFitness()==319) ) break;
            
            childPop=pop.generatePop(parentPop);
            popBest=pop.getFittest(childPop);
            
            if(popBest.getFitness() < bestOffspring.getFitness()){
            bestOffspring=popBest;
            }
            
           parentPop= childPop;
            count++;
        }
        
        
        System.out.println("route "+bestOffspring.Genes);
        System.out.println("# of iterations: "+count);
        System.out.println("Fitness Value: "+ bestOffspring.getFitness());
        System.out.println("************************");
      
        
    }
    
  

    

    

}
