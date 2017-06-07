/****************************************************************************
  * Desenvolvido por:
  * 	Alex Valerio Andriati  Nusp: 7994788
  *     Caio Vinicius Dadauto  Nusp: 7994808
  * 
  * Compilation: javac-algs4 GeoInto.java
  * Dependencias:
  * 	Location.java
  * 
  * Estrutura para montar uma referência do mapa, a partir dos nós para os
  * locais geográficos que eles representam através de uma tabela de
  * simbolos; 
  * 
  * Também adciona um número inteiro a cada ponto tornando mais fácil a
  * referência para o vértice do grafo posteriormente.
  * 
  **************************************************************************/

public class GeoInto {
	private In XML;
	private ST<Long, Location> IDpoint;
	private Location min;
	private Location max;

	GeoInto (String filename) {
		XML = new In(filename);
		IDpoint  = new ST<Long, Location>();
		findBoundaries();
		mountST();
		XML.close();
	}

	// Encontra os limites do mapa
	private void findBoundaries () {
		String convertToLong = new String();
		while ( !XML.readString().equals("<bounds") ) {	}

		convertToLong = XML.readString().split("\"")[1];
		double minLat = Double.parseDouble(convertToLong);
		convertToLong = XML.readString().split("\"")[1];
		double minLon = Double.parseDouble(convertToLong);
		min = new Location(minLon, minLat);

		convertToLong = XML.readString().split("\"")[1];
		double maxLat = Double.parseDouble(convertToLong);
		convertToLong = XML.readString().split("\"")[1];
		double maxLon = Double.parseDouble(convertToLong);
		max = new Location(maxLon, maxLat);
	}

	private void mountST () {
		String convertToDouble = new String();

		while (!XML.isEmpty()) {
			String linha    = XML.readLine();
			String[] campos = linha.split(" ");
			
			// Inclui na tabela de simbolos se for um nó.
			if (campos.length > 1 && campos[1].equals("<node")) {
				// Acha o ID para a chave.
				convertToDouble = campos[2].split("\"")[1];
				long key        = Long.parseLong(convertToDouble);
				// Acha a Latitude.
				convertToDouble = campos[campos.length - 2].split("\"")[1];
				double lat      = Double.parseDouble(convertToDouble);
				// Acha a Longitude.
				convertToDouble = campos[campos.length - 1].split("\"")[1];
				double lon      = Double.parseDouble(convertToDouble);
				// Adiciona as tabelas de simbolos o Nó encontrado.
				IDpoint.put(key, new Location(lon, lat, IDpoint.size()));
			}
		}
	}

	// Para um dado nó retorna sua localização
	public Location getLoc (long nodeID) { return IDpoint.get(nodeID); }

	// Tamanho da arvore (numero de vertices/nós).
	public int getSize () { return IDpoint.size(); }

	// retorna os limites do mapa. (contornos)
	public Location getMinLoc () { return min; }
	public Location getMaxLoc () { return max; }
}
