/************************************************************************************************
 * Caio Vinicius Dadauto   7994808             Estrutura de Dados 2                             *
 *                                             EP2                                              *
 *                                                                                              *
 * Compilacao:  javac-algs4 GeoIPlookupRedBlackBST.java                                         *
 * Descricao:   O progrma recebe o nome do arquivo contendo o inderesso                         *
 *              IP por cidade como argumento. O arquivo deve ter o                              *
 *              formado indicado em "dbip-city-2015-05h.csv". Assim,                            *
 *              programa receberá uma lista de IP's na entrada padrao                           *
 *              retornará as informacoes vinculadas aos IP's fornecidos.                        *
 *              Pode-se, ainda, forncer um segundo argumento na linha                           *
 *              de comando. Caso isso seja feito, o programa deve                               *
 *              receber, na entrada padrao, um system.log com formato                           *
 *              indicado em "ssh.log". Assim, ele retornará as                                  *
 *              informacoes vinculadas a cada inderesso de IP fornecido                         *
 *              e a frequncia com que este inderesso foi encontrado em                          *
 *              ordem crescente.                                                                *
 * Execucao:    java-algs4 -Xmx2g GeoIPlookupRedBlackBST dbip-city-2015-05h.csv < IPs           *
 *                  -> Tempo de execucao e´ de aproximadamente ~ 37 segundos                    *
 *                                                                                              *
 *              java-algs4 -Xmx2g GeoIPlookupRedBlackBST dbip-city-2015-05h.csv - < ssh.log     *
 *                  -> Tempo de execucao e´ de aproximadamente ~ 37 segundos                    *
 *                                                                                              *
 ************************************************************************************************/

public class GeoIPlookupRedBlackBST{
    private static final String comma = ", ";

    public static void main(String[] args) {
        String[]
            line;
        String
            ip,
            locale,
            inLine,
            stdLine,
            ipMin,
            ipMax,
            country,
            state,
            city;
        Integer
            numAccess;
        Long
            ipNum,
            requestIP;

        RedBlackBST<Long, String> dataBST           = new RedBlackBST<Long, String>();
        RedBlackBST<String, Integer> accessBST      = new RedBlackBST<String, Integer>();
        RedBlackBST<Integer, String> accessOrderBST = new RedBlackBST<Integer, String>();
        In dataBase                                 = new In(args[0]);
        Stack<String> inverse                       = new Stack<String>();

        /**
         * Leitura do arquivo .csv de forma a armazenando as informacoes
         * em uma arvore rubro-negra com chave Long e valor String,
         * onde a String e´ uma concatenacao de cidade, estado e pais.
        **/
        Stopwatch timer = new Stopwatch();
        while(dataBase.hasNextLine()){
            inLine = dataBase.readLine();
            line = inLine.split("\"", 11);
            ipMin   = line[1];
            ipMax   = line[3];
            country = line[5];
            state   = line[7];
            city    = line[9];
            
            // Corrige leituras vazias do arquivo .csv
            if(country.compareTo("") == 0)   country = "Pais nao definido";
            if(state.compareTo("") == 0)     state   = "Estado nao definido";
            if(city.compareTo("") == 0)      city    = "Cidade nao definida";
            
            // Gera a Sting concatenada
            locale = "";
            locale += city;
            locale += comma;
            locale += state;
            locale += comma;
            locale += country;
            dataBST.put(IPparse.toNum(ipMin), locale);
            dataBST.put(IPparse.toNum(ipMax), locale);
        } 

        /**
         * Le a entrada padrao de acordo com o numero de argumentos
         * Caso este numero seja 1, le apenas um conjunto de ip's
         * Caso este numero seja diferente disso, le um arquivo .log
         * e imprime na saida padrao as tentativas de acesso com as
         * respectivas localidades.
        **/
        if(args.length == 1) {
            while(!StdIn.isEmpty()){
                ip        = StdIn.readLine();
                ipNum     = IPparse.toNum(ip);
                requestIP = dataBST.ceiling(ipNum);
                if(requestIP == null)
                    StdOut.println("IP " + ip + " nao encontrado.");  
                else
                    StdOut.println(dataBST.get(requestIP));  
            }
        }
        else {
            while(!StdIn.isEmpty()){
                stdLine   = StdIn.readLine();
                line      = stdLine.split(" ");
                ip        = line[line.length - 1];
                ipNum     = IPparse.toNum(ip);
                requestIP = dataBST.ceiling(ipNum);
                if(requestIP == null){
                    StdOut.println("IP " + ip + " nao encontrado.");  
                    continue;
                }
                locale = dataBST.get(requestIP);

                // Armazena em uma arvore rubro negra com chave String e valor Integer
                // onde integer e´ o numero de acesso para uma dada localidade
                numAccess = accessBST.get(locale);
                if(numAccess == null)
                    accessBST.put(locale, 1);
                else
                    accessBST.put(locale, numAccess + 1);
            }
            // Rearmazena os dados em outra arvore, porem com chave inteira e valor sendo a localidade
            for(String key : accessBST.keys())
                accessOrderBST.put(accessBST.get(key), key);
            
            // Itera a arvore do minimo ao maximo e aloca a String concatenada na pilha
            for(Integer key : accessOrderBST.keys())
                inverse.push(key + "\t" + accessOrderBST.get(key));

            // Desempilha em ordem inversa e imprime na saida padrao
            while(!inverse.isEmpty())
                StdOut.println(inverse.pop());
        }
        // Estima o tempo decorrido para a execussao do programa
        StdOut.println("\nTempo decorrido = " + timer.elapsedTime() + "s");
    }
}