/****************************************************************************
  * 
  * Desenvolvido Por:
  * 	Alex Valerio Andriati  Nusp: 7994788
  * 	Caio Vinicius Dadauto  Nusp: 7994808
  * 
  * Compilation: javac-algs4 Edges.java
  * 
  * Implementa busca em profundida para todo grafo, retornando todos os
  * caminhos possiveis de se traçar neste a partir de uma Bag com as
  * arestas dirigidas.
  *
  **************************************************************************/

public class AllEdgesDFS {
	private boolean[] marked;
	private Bag<DirectedEdge> allEdges;

	public AllEdgesDFS (EdgeWeightedDigraph G) {
		allEdges = new Bag<DirectedEdge>();
		marked = new boolean[G.V()];
		for (int v = 0; v < G.V(); v++) 
			if (!marked[v]) dfs(G, v);
	}

	private void dfs(EdgeWeightedDigraph G, int v) {
		marked[v] = true;
		for (DirectedEdge e : G.adj(v)) {
			allEdges.add(e);
			if (!marked[e.to()]) dfs(G, e.to());
		}
	}

	public Iterable<DirectedEdge> GraphEdges() {
		return allEdges;
	}
}
