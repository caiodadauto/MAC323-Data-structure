public class WeightedQuickUnionPathC{
   private int[]
	  parent,
	  size;

   public WeightedQuickUnionPathC(int N){
	  parent = new int[N];
	  size = new int[N];
	  for(int i = 0; i < N; i++){
		 parent[i] = i;
		 size[i] = 1;
	  }
   }

   private void validate(int p){
	  if(p < 0 || p >= parent.length)
		 throw new IndexOutOfBoundsException("Index out of the range");
   }

   public int findRoot(int p){
	  validate(p);
	  while(p != parent[p]){
		 parent[p] = parent[parent[p]];
		 p = parent[p];
	  }
	  return p;
   }

   public boolean connected(int p, int q){
	  return findRoot(p) == findRoot(q);
   }

   public void union(int p, int q){
	  int rootp = findRoot(p);
	  int rootq = findRoot(q);
	  if(rootq  == rootp)
		 return;

	  if(size[rootp] < size[rootq]){
		 parent[rootp] = rootq;
		 size[rootq] += size[rootp];
	  }
	  else{
		 parent[rootq] = rootp;
		 size[rootp] += size[rootq];
	  }
   }
}
