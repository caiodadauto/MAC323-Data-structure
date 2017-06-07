/****************************************************************************
  * Desenvolvido por:
  * 	Alex Valerio Andriati  Nusp: 7994788
  *     Caio Vinicius Dadauto  Nusp: 7994808
  * 
  * Compilation: javac-algs4 navegacao.java
  * Execution:   java-algs4  navegacao Arquivo.xml G.adjlist
  * 
  * Dependencias:
  * 	Location.java
  * 	GeoInto.java
  * 	SymbolGeoEWDigraph.java
  * 	AllEdgesDFS.java
  * 	MapImage.java
  * 
  * Classe que utiliza dados de https://www.openstreetmap.org
  * 
  * Cliente para fazer a navegação após o carregamento do mapa nos seguintes
  * passos:
  * 
  * 1) Os arquivos XML podem ser exportados do site acima
  * 2) Produzir as listas de adjacências utilize por exemplo:
  * 
  * 	python xmltoadj.py Arquivo.xml G.adjlist
  *
  * Inicie a navegação digitando um ponto de partida válido com relação ao
  * mapa; Boa Viagem!
  * 
  * OBS: Minhas implementações não seguem estritamente a relação em
  *      SymbolDigraph pois houve uma alteração no modo de localização
  *      adcionando um identificador, as justificativas estão nestas
  *      classes auxiliares.
  *
  **************************************************************************/

public class navegacao {

	public static void menu(SymbolGeoEWDigraph map) {
		StdOut.println("\nLimites atuais visiveis: (longitude, latitude)");
		StdOut.println("minimo: " + map.MinLoc() + " maximo: " + map.MaxLoc());
		StdOut.println("\nMenu de Opcoes...");
		StdOut.println("\tDesenhar Mapa: D");
		StdOut.println("\tRedefinir regiao de visualizacao: R");
		StdOut.println("\tRetornar visualizacao original: O");
		StdOut.println("\tTracar Rota: TR");
		StdOut.println("\tLimpar rota: LR");
		StdOut.println("\tSalvar trajeto: ST");
		StdOut.println("\tImprime menu novamente: M");
		StdOut.println("\tLimites do mapa: LM");
		StdOut.println("\tEncerrar execucao (depois feche a janela): S");
	}

	public static void mapLimits(SymbolGeoEWDigraph map) {
		StdOut.println("\nLimites do mapa: (longitude, latitude)");
		StdOut.print("minimo: " + map.mapMinLoc());
		StdOut.println(" maximo: " + map.mapMaxLoc());
	}

	public static boolean isValid (Location smaller, Location greater) {
		if (smaller.lon() > greater.lon() || smaller.lat() > greater.lat())
			return false;
		return true;
	}

	public static void newViewLimits(SymbolGeoEWDigraph map) {
		StdOut.println("Entre com as coordenadas dos pontos");
		StdOut.print("\tLongitude minima: ");
		double lonMin = StdIn.readDouble();
		StdOut.print("\tLatitude minima:  ");
		double latMin = StdIn.readDouble();
		Location newMin = new Location(lonMin, latMin);

		StdOut.println();
		StdOut.print("\tLongitude maxima: ");
		double lonMax = StdIn.readDouble();
		StdOut.print("\tLatitude maxima:  ");
		double latMax = StdIn.readDouble();
		Location newMax = new Location(lonMax, latMax);
		if (isValid(newMin, newMax))
			map.ChangeViewLimits(new Location(lonMin, latMin), 
					new Location(lonMax, latMax));
		else {
			StdOut.print("Esntrada Invalida! ");
			StdOut.println("Limite(s) minimos maiores que maximos.");
		}
	}

	public static void newViewLimitsMouse(SymbolGeoEWDigraph map, 
			MapImage mapView) {
		StdOut.println("Clique no ponto minimo.");
		Location newMin = mapView.getMousePressedLoc();

		StdOut.println("Clique no ponto maximo.");
		Location newMax = mapView.getMousePressedLoc();

		if (isValid(newMin, newMax))
			map.ChangeViewLimits(newMin, newMax);
		else {
			StdOut.print("Esntrada Invalida! ");
			StdOut.println("Limite(s) minimos maiores que maximos.");
		}
	}

	public static void defaultViewLimits (SymbolGeoEWDigraph map) {
		map.ChangeViewLimits(map.mapMinLoc(), map.mapMaxLoc());
	}
	
	public static void newTrajectory (MapImage mapView) {
		StdOut.println("Entre com as coordenadas do ponto de partida: ");
		StdOut.print("\tLongitude partida: ");
		double lonFrom = StdIn.readDouble();
		StdOut.print("\tLatitude partida:  ");
		double latFrom = StdIn.readDouble();

		StdOut.println();
		StdOut.print("\tLongitude destino: ");
		double lonTo = StdIn.readDouble();
		StdOut.print("\tLatitude destino:  ");
		double latTo = StdIn.readDouble();
		mapView.SetTrajectory(new Location(lonFrom, latFrom), 
				new Location(lonTo, latTo));
	}

	public static void mouseTrajectory (MapImage mapView) {
		StdOut.println("Clique no inicio do trajeto. ");
		Location from = mapView.getMousePressedLoc();
		StdOut.println("Clique no destino.");
		Location to = mapView.getMousePressedLoc();

		mapView.SetTrajectory(from, to);
	}


	
	public static void main (String[] args) {
		StdOut.println("\nAguarde carregando mapa.");
		GeoInto nodeLoc        = new GeoInto(args[0]);
		SymbolGeoEWDigraph map = new SymbolGeoEWDigraph(args[1], " ", nodeLoc);
		MapImage mapView       = new MapImage(map);

		StdOut.println("\nLimites do mapa: (longitude, latitude)");
		StdOut.print("minimo: " + map.mapMinLoc());
		StdOut.println("\tmaximo: " + map.mapMaxLoc());

		menu(map);
		StdOut.print("\nAcao desejada: ");
		String action = StdIn.readString();
		String ans    = new String();
		while(!action.equals("S")) {
			switch (action) {
				case "D" :

					StdOut.print("Ver arestas ? [s/n] ");
					ans = StdIn.readString();
					if (ans.equals("s") || ans.equals("S")) 
						mapView.ShowEdges(true);
					else if (ans.equals("n") || ans.equals("N")) 
						mapView.ShowEdges(false);
					else {
						StdOut.println("Resposta invalida."); 
						break;
					}
					StdOut.println("Desenhando mapa, aguarde.");
					mapView.DrawMap();
					break;

				case "R" :

					StdOut.print("Entrada por mouse: [s/n] ");
					ans = StdIn.readString();
					if (ans.equals("s") || ans.equals("S"))
						newViewLimitsMouse(map, mapView);
					else if (ans.equals("n") || ans.equals("N"))
						newViewLimits(map);
					else {
						StdOut.println("Resposta invalida."); 
						break;
					}
					mapView.DrawMap();
					break;

				case "O" :
					defaultViewLimits(map);
					mapView.DrawMap();
					break;

				case "M" :

					menu(map);
					break;

				case "LM" :

					mapLimits(map);
					break;

				case "TR" :

					StdOut.print("Entrada por mouse: [s/n] ");
					ans = StdIn.readString();
					if (ans.equals("s") || ans.equals("S"))
						mouseTrajectory(mapView);
					else if (ans.equals("n") || ans.equals("N"))
						newTrajectory(mapView);
					else {
						StdOut.println("Resposta invalida."); 
						break;
					}
					break;

				case "LR" :

					mapView.ClearTrajectory();
					break;

				case "ST" :

					StdOut.print("\nDe um nome para o arquivo (.png): ");
					String filename = StdIn.readString();
					mapView.Save(filename);
					break;

			}
			StdOut.print("\nAcao desejada: ");
			action = StdIn.readString();
		}
	}
}
