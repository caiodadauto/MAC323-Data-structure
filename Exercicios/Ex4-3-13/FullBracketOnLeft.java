//------------------------------------------------
// Nome: Caio Vinicius Dadauto 7994808
// 
// java-algs4 < entrada.txt
// Ou entre com ctrl + d para finalizar o programa
//------------------------------------------------

public class FullBracketOnLeft{
   public static void main(String[] args){
	  Stack<String>
		 operand  = new Stack<String>(),
		 operator = new Stack<String>();

	  while(!StdIn.isEmpty()){
		 String s = StdIn.readString(); 
		 if(s.equals("+"))
			operator.push(s);
		 else if(s.equals("*"))
			operator.push(s);
		 else if(s.equals("-"))
			operator.push(s);
		 else if(s.equals(")")){
			String
			   sopd1,
			   sopd2;
			sopd2 = operand.pop();
			sopd1 = operand.pop();
			operand.push("( " + sopd1 + " " +  operator.pop() + " " + sopd2 + " )");
		 }
		 else
			operand.push(s);
	  }
	  StdOut.println(operand.pop());
   }
}
