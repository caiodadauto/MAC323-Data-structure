/****************************************************************************
  * Desenvolvido por:
  * 	Alex Valerio Andriati  Nusp: 7994788
  *     Caio Vinicius Dadauto  Nusp: 7994808
  * 
  * Compilation: javac-algs4 Location.java
  * Execution:   java-algs4  Location lon1 lat1 lon2 lat2
  * 
  * Estrutura de dados para trabalhar com pontos em 2 Dimensões cuja as
  * coordenadas são Reais e podem ser identificados por uma numeração;
  * 
  * A nomeclatura refere-se especialmente a localizações geográficas e, a
  * possibilidade de adcionar um número inteiro de identificador ajuda ao
  * montar o grafo de um mapa.
  *
  **************************************************************************/

import java.lang.Math;

public class Location {
	private final double lon;
	private final double lat;
	private final int    N; // Identificador.

	public Location(double lon, double lat, int N) {
		this.lon = lon;
		this.lat = lat;
		this.N   = N;
	}
	
	public Location(double lon, double lat) {
		this.lon = lon;
		this.lat = lat;
		this.N   = 0;
	}

	// Distância entre dois pontos.
	public double distTo(Location that) {
		double dlon = this.lon - that.lon();
		double dlat = this.lat - that.lat();
		return Math.sqrt( dlon * dlon + dlat * dlat );
	}

	// Ponto Médio entre eles.
	public Location pontoMedio(Location that) {
		double lonMedio = (this.lon + that.lon()) / 2;
		double latMedio = (this.lat + that.lat()) / 2;
		return new Location(lonMedio, latMedio);
	}

	// Acessar as coordenadas.
	public double lon() { return this.lon; }
	public double lat() { return this.lat; }

	// Identificador.
	public int ID() { return this.N; }

	public String toString() { return "(" + lon + "," + lat + ")" ; }

	public static void main(String[] args) {
		double lon1 = Double.parseDouble(args[0]);
		double lat1 = Double.parseDouble(args[1]);
		double lon2 = Double.parseDouble(args[2]);
		double lat2 = Double.parseDouble(args[3]);
		Location P1 = new Location(lon1, lat1);
		Location P2 = new Location(lon2, lat2);
		StdOut.println("Distancia:   " + P1.distTo(P2));
		StdOut.println("Ponto Medio: " + P1.pontoMedio(P2));
	}
}
