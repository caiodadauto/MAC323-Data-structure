import java.util.NoSuchElementException;

public class Stack<Item>{
   private Node
	  first;
   private int
	  N;

   private class Node{
	  private Item
		 item;
	  private Node
		 next;
   }

   public Stack(){
	  this.first = null;
   }

   public boolean isEmpty(){
	  return this.first == null;
   }

   public void push(Item item){
	  Node
		 oldfirst	  = this.first;
	  this.first	  = new Node();
	  this.first.item = item;
	  this.first.next = oldfirst;
	  this.N++;
   }

   public Item pop(){
	  if(this.isEmpty())
		 throw new RuntimeException("Stack underflow.");
	  Item
		 item	 = this.first.item;
	  this.first = this.first.next;
	  this.N--;
	  return item;
   }
}
