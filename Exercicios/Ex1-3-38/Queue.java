import java.util.NoSuchElementException;

public class Queue<Item>{
   private Node
	  first,
	  last;
   private int
	  N;

   private class Node{
	  private Item
		 item;
	  private Node
		 next = null;
   }

   public Queue(){
	  first = null;
	  last  = null;
	  N = 0;
   }

   public boolean isEmpty(){
	  return this.first == null;
   }

   public void enqueue(Item item){
	  Node
		 x = new Node();

	  x.item = item;
	  if(this.isEmpty()){
		 this.first = x;
		 this.last  = x;
	  }
	  else{
		 this.last.next = x;
		 this.last      = x;
	  }
	  N++;
   }

   public Item dequeue(){
	  if(this.isEmpty())
		 throw new RuntimeException("Queue underflow");
	  Item
		 item = this.first.item;

	  this.first = this.first.next;
	  N--;
	  if(this.isEmpty())
		 this.last = null;
	  return item;
   }

   public Item peek(){
	  if(this.isEmpty())
		 throw new RuntimeException("Queue underflow");
	  return this.first.item;
   }

   public int size(){
	  return N;
   }
}
