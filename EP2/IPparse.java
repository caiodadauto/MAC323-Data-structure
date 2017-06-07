/********************************************************************************
 * Caio Vinicius Dadauto   7994808             Estrutura de Dados 2             *
 *                                             EP2                              *
 *                                                                              *
 * Compilacao:  javac-algs4 IPparse.java                                        *
 * Descricao:   Classe responsavel por converter enderecos IP's em numeros      *
 *              inteiros.                                                       *
 *                                                                              *
 ********************************************************************************/

public class IPparse {
    public static Long toNum(String ip) {
        Long     ipNum;
        String[] ipDig;

        ipDig = ip.split("\\.");
        ipNum = 16777216 * Long.parseLong(ipDig[0]) 
                + 65536 * Long.parseLong(ipDig[1]) 
                + 256 * Long.parseLong(ipDig[2]) 
                + Long.parseLong(ipDig[3]);
        
        return ipNum;
    }

    public static String toIP(Long ipNum) {
        String  ip = "";
        Integer o1, o2, o3, o4;

        o1 = (int)((ipNum / 16777216) % 256);
        o2 = (int)((ipNum / 65536   ) % 256);
        o3 = (int)((ipNum / 256     ) % 256);
        o4 = (int)((ipNum           ) % 256); 

        ip = ip.concat(o1.toString());
        ip = ip.concat("\\.");
        ip = ip.concat(o2.toString());
        ip = ip.concat("\\.");
        ip = ip.concat(o3.toString());
        ip = ip.concat("\\.");
        ip = ip.concat(o4.toString());

        return ip;
    }
}
