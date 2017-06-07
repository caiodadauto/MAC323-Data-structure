/****************************************************************************
  * Desenvolvido por:
  * 	Alex Valerio Andriati  Nusp: 7994788
  *     Caio Vinicius Dadauto  Nusp: 7994808
  * 
  * Compilation: javac-algs4 DrawMap.java
  * Dependencias:
  * 	Location.java
  * 	GeoInto.java
  * 	SymbolGeoEWDigraph.java
  * 	AllEdgesDFS.java
  * 
  * manipulação da representação gráfica do mapa.
  *
  **************************************************************************/

public class MapImage {
	private SymbolGeoEWDigraph map;
	private AllEdgesDFS mapEdges;
	private DijkstraSP traj;
	private int toVertice   = -1;
	private int fromVertice = -1;
	private boolean viewEdges = false;
	
	public MapImage (SymbolGeoEWDigraph map) {
		this.map = map;
		mapEdges = new AllEdgesDFS(map.graph());
	}

	private void ImageProperties (int width, int height) {
		StdDraw.setCanvasSize(width, height);
		StdDraw.setXscale(map.MinLoc().lon(), map.MaxLoc().lon());
		StdDraw.setYscale(map.MinLoc().lat(), map.MaxLoc().lat());
		StdDraw.setPenRadius(.004);
	}
	
	
	public void SetTrajectory(Location from, Location to) {
		fromVertice = map.closer(from);
		toVertice   = map.closer(to);
		traj = new DijkstraSP(map.graph(), fromVertice);
		DrawTrajectory(toVertice);
	}

	public void DrawMap () {
		StdDraw.clear();
		ImageProperties(800, 600);
		for (DirectedEdge e : mapEdges.GraphEdges()) {
			double lonFrom = map.verticeToLoc(e.from()).lon();
			double latFrom = map.verticeToLoc(e.from()).lat();
			Location from  = new Location(lonFrom, latFrom);

			double lonTo = map.verticeToLoc(e.to()).lon();
			double latTo = map.verticeToLoc(e.to()).lat();
			Location to  = new Location(lonTo, latTo);

			if (!map.isVisible(from) && !map.isVisible(to)) continue;
			if (viewEdges) StdDraw.line(lonFrom, latFrom, lonTo, latTo);
			StdDraw.point(lonFrom,latFrom);
		}

		if (traj != null && toVertice > 0) DrawTrajectory(toVertice);
		StdDraw.show();
	}

	private void DrawTrajectory (int destiny) {
		if (!traj.hasPathTo(destiny)) {
			StdOut.println("Ocorreu um erro e não há este caminho.");
			return;
		}
		StdDraw.setPenColor(0, 0, 255);
		StdDraw.setPenRadius(.008);
		for (DirectedEdge e : traj.pathTo(destiny)) {
			double lonFrom = map.verticeToLoc(e.from()).lon();
			double latFrom = map.verticeToLoc(e.from()).lat();
			Location from = new Location(lonFrom, latFrom);

			double lonTo = map.verticeToLoc(e.to()).lon();
			double latTo = map.verticeToLoc(e.to()).lat();
			Location to = new Location(lonTo, latTo);

			if (!map.isVisible(from) && !map.isVisible(to)) continue;
			StdDraw.line(lonFrom, latFrom, lonTo, latTo);
		}
		StdDraw.setPenColor(0, 0, 0);
		StdDraw.setPenRadius(.004);
	}
	
	// Algumas opcoes.
	public void ClearTrajectory() { toVertice = -1; DrawMap(); }
	public void Save (String filename) { StdDraw.save(filename); }
	public void ShowEdges (boolean viewEdges) { this.viewEdges = viewEdges; }
	
	public Location getMousePressedLoc () {
		while (!StdDraw.mousePressed()) { continue; }
		double lon = StdDraw.mouseX();
		double lat = StdDraw.mouseY();
		waitReleaseMouse();
		StdOut.print("\nClique em um ponto ");
		StdOut.println("(" + lon + "," + lat + ")");
		return new Location(lon, lat);
	}

	private void waitReleaseMouse () {
		while (StdDraw.mousePressed()) { continue; }
	}
}
