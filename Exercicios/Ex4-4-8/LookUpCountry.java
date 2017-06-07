//------------------------------------------------------//
// Nome:Caio Vinicuis Dadauto       Estrutura de Dados  //
// Nusp: 7994808                                        //
//                                   Ex. 4.4.8          //
//------------------------------------------------------//

public class LookUpCountry{
   public static void main(String[] args){
      RedBlackBST<Long, String> bst = new RedBlackBST<Long, String>();
      In dataBase                   = new In(args[0]);
      String[] lines                = dataBase.readAllLines();
      
      String[] line;
      for(int i = 0; i < lines.length; i++){
         line = lines[i].split("\"");
         bst.put(Long.parseLong(line[5]), line[11]);
         bst.put(Long.parseLong(line[7]), line[11]);
      }

      Long ipInt;
      String[] ip;
      String country;
      while(!StdIn.isEmpty()){
         ip      = (StdIn.readLine()).split("\\.");
         ipInt   = 16777216 * Long.parseLong(ip[0]) 
            + 65536 * Long.parseLong(ip[1]) 
            + 256 * Long.parseLong(ip[2]) 
            + Long.parseLong(ip[3]);
         
         country = bst.get(ipInt);
         if(country != null)
            StdOut.println(country);
         else
            StdOut.println(bst.get(bst.ceiling(ipInt)));  
      }
   }
}
