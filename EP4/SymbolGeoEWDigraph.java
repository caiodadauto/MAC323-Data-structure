/****************************************************************************
  * Desenvolvido por:
  * 	Alex Valerio Andriati  Nusp: 7994788
  *     Caio Vinicius Dadauto  Nusp: 7994808
  * 
  * Compilation: javac-algs4 SymbolGeoEWDigraph.java
  * Dependencias:
  * 	Location.java
  * 	GeoInto.java
  * 
  * Estrutura que carrega informações sobre o mapa e utilizada para 
  * desenhá-lo; Seu construtor constrói ambos grafos e vetor de referência
  * onde seus indices representam vértices;
  * 
  * OBS: Algumas posições do vetor de referência Vertice-Localização não
  *      apontem para localizações, isso porque nem todos os nós da classe
  *      GeoInto lançam arestas para outros.
  *
  **************************************************************************/

public class SymbolGeoEWDigraph {
	private Location[] verticeLoc;
	private EdgeWeightedDigraph G;
	private GeoInto mapRef;
	private Location limInf;
	private Location limSup;

	public SymbolGeoEWDigraph (String filename, String delimiter, GeoInto g) {
		double bordaX = (g.getMaxLoc().lon() - g.getMinLoc().lon()) / 4;
		double bordaY = (g.getMaxLoc().lat() - g.getMinLoc().lat()) / 4;
		double lonMin = g.getMinLoc().lon() - bordaX;
		double lonMax = g.getMaxLoc().lon() + bordaX;
		double latMin = g.getMinLoc().lat() - bordaY;
		double latMax = g.getMaxLoc().lat() + bordaY;

		limInf = new Location(lonMin, latMin);
		limSup = new Location(lonMax, latMax);
		mapRef = g;
		
		verticeLoc = new Location[mapRef.getSize()];
		In in = new In(filename);
		G = new EdgeWeightedDigraph(mapRef.getSize());
		mountGraph(in, delimiter);
		in.close();
	}

	private void mountGraph(In conections, String delimiter) {
		while (conections.hasNextLine()) {
			String line = conections.readLine();
			if (line.charAt(0) == '#') continue;

			// Linhas com conexoes a serem feitas.
			String[] fields = line.split(delimiter);
			Location from   = mapRef.getLoc(Long.parseLong(fields[0]));
			verticeLoc[from.ID()] = from;

			// Percorre apenas arestas que serão adjacentes a origem.
			for (int i = 1; i < fields.length; i++) {
				Location to = mapRef.getLoc(Long.parseLong(fields[i]));
				verticeLoc[to.ID()] = to;
				double length = from.distTo(to);
				G.addEdge(new DirectedEdge(from.ID(), to.ID(), length));
			}
		}
	}

	public void ChangeViewLimits (Location limInf, Location limSup) {
		double bordaX = (limSup.lon() - limInf.lon()) / 4;
		double bordaY = (limSup.lat() - limInf.lat()) / 4;
		double lonMin = limInf.lon() - bordaX;
		double lonMax = limSup.lon() + bordaX;
		double latMin = limInf.lat() - bordaY;
		double latMax = limSup.lat() + bordaY;

		this.limInf = new Location(lonMin, latMin);
		this.limSup = new Location(lonMax, latMax);
	}

	public boolean isVisible (Location P) {
		if (P.lon() > limInf.lon() && P.lon() < limSup.lon()) { 
			if (P.lat() > limInf.lat() && P.lat() < limSup.lat())
				return true;
		}
		return false;
	}

	public Location MinLoc () { return limInf; }
	public Location MaxLoc () { return limSup; }
	public Location mapMaxLoc () { return mapRef.getMaxLoc(); }
	public Location mapMinLoc () { return mapRef.getMinLoc(); }

	public EdgeWeightedDigraph graph () { return G; }
	public Location verticeToLoc(int v) { return verticeLoc[v]; }

	public int closer (Location sentinel) {
		double closerDistance = mapMaxLoc().distTo(mapMinLoc());
		int j = 0;
		for (int i = 0; i < verticeLoc.length; i++) {
				if (verticeLoc[i] == null) continue;
			if (verticeLoc[i].distTo(sentinel) < closerDistance) {
				j = i;
				closerDistance = verticeLoc[i].distTo(sentinel);
			}
		}
		return j;
	}
}
