/*************************************************************************
 *  
 *  Nome: Caio Vinicius Dadauto
 *  Nusp: 7994808
 *
 *************************************************************************/
import java.util.Arrays;

public class MultiwordSearchWithoutOrder{
    public static void main(String[] args){
        int
            i,
            j,
            argslength = args.length;
        int[] 
	        index = new int[argslength];
        String[]
            words = StdIn.readAllStrings();

        Queue<Integer>[] 
            queues = (Queue<Integer>[]) new Queue[argslength];
            
        for(j = 0; j < argslength; j++)
            queues[j] = new Queue<Integer>();
        for(i = 0; i < words.length; i++){
            for (j = 0; j < argslength; j++) {
                if(words[i].equals(args[j]))
                    queues[j].enqueue(i);
            }
        }

        boolean
            done = false;
        int
            hi,
            lo,
            bestlo = -1,
            besthi = words.length;
        Queue<Integer>
            aux;
            
        while(!done){
            for (j = 0; j < argslength; j++){
                if(queues[j].isEmpty()){
                    done = true;
                    break;
                }
                index[j] = queues[j].peek();
            }
            if(!done){
                Arrays.sort(index);
                lo = index[0];
                hi = index[argslength - 1];
            
                for(j = 0; j < argslength; j++){
                    if(Arrays.binarySearch(index, queues[j].peek()) == 0)
                        break;
                }
                queues[j].dequeue();
                if (hi - lo < besthi - bestlo) {
                    besthi = hi;
                    bestlo = lo;
                }
            }
        }
        if (bestlo >= 0) {
            for (i = bestlo; i <= besthi; i++)
                StdOut.print(words[i] + " ");
            StdOut.println();
        }
        else
            StdOut.println("NOT FOUND");
    }
}
