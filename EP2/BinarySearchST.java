/*************************************************************************
 * Caio Vinicius Dadauto   7994808             Estrutura de Dados 2      *
 *                                                                       *
 * Tabela de simbolos com chaves ordenadas e busca binaria               *
 * Programa baseado em:                                                  *
 *  http://algs4.cs.princeton.edu/31elementary/BinarySearchST.java.html  *
 *                                                                       *
 *************************************************************************/

import java.util.NoSuchElementException;

public class BinarySearchST<Key extends Comparable<Key>, Value> {
    private Key[]
        keys;
    private Value[]
        vals;
    private int
        N = 0;
    private static final int
        INIT_CAPACITY = 2;

    // Cria tabela de simbolos vazia com capacidade inicial
    public BinarySearchST() {
        this(INIT_CAPACITY);
    }

    // Cria tabela de simbolos vazia com capacidade inicial arbitraria
    public BinarySearchST(int capacity) { 
        keys = (Key[]) new Comparable[capacity]; 
        vals = (Value[]) new Object[capacity]; 
    }   

    // Redimensiona os vetores
    private void resize(int capacity) {
        assert capacity >= N;
        Key[]   tempk = (Key[])   new Comparable[capacity];
        Value[] tempv = (Value[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            tempk[i] = keys[i];
            tempv[i] = vals[i];
        }
        vals = tempv;
        keys = tempk;
    }

    // Determina se uma chave esta ou nao na tabela
    public boolean contains(Key key) {
        return get(key) != null;
    }

    // Numero de pares de chave-valor contidos na tabela
    public int size() {
        return N;
    }

    // Determina se a tabela de simbolos esta vazia ou nao
    public boolean isEmpty() {
        return size() == 0;
    }

    // Retorna o valor associado a uma chave arbitraria, retorna null caso a chave nao exista
    public Value get(Key key) {
        if(isEmpty())
            return null;

        int i = rank(key);
        if(i < N && keys[i].compareTo(key) == 0)
            return vals[i];
        return null;
    } 

    // Retorna o numero de chaves menores que uma chave dada, faz uso da busca binaria
    public int rank(Key key) {
        int
            lo = 0,
            hi = N-1;

        while(lo <= hi) { 
            int m   = lo + (hi - lo) / 2; 
            int cmp = key.compareTo(keys[m]);

            if     (cmp < 0) hi = m - 1; 
            else if(cmp > 0) lo = m + 1; 
            else return m; 
        } 
        return lo;
    } 


    // Busca uma chave dada, se encontrada atualiza o valor, caso contrario insere na tabela 
    public void put(Key key, Value val) {
        if(val == null) {
            delete(key);
            return;
        }

        int i = rank(key);

        // Se estiver na tabela
        if(i < N && keys[i].compareTo(key) == 0) {
            vals[i] = val;
            return;
        }

        // Insere novo par chave-valor
        if(N == keys.length)
            resize(2 * keys.length);

        for(int j = N; j > i; j--)  {
            keys[j] = keys[j - 1];
            vals[j] = vals[j - 1];
        }
        keys[i] = key;
        vals[i] = val;
        N++;

        assert check();
    } 


    // Remove o par chave-valor, se presente
    public void delete(Key key)  {
        if(isEmpty())
            return;

        int i = rank(key);

        // Se a chave nao estiver na tabela
        if(i == N || keys[i].compareTo(key) != 0)
            return;

        for(int j = i; j < N - 1; j++)  {
            keys[j] = keys[j + 1];
            vals[j] = vals[j + 1];
        }

        N--;
        keys[N] = null;
        vals[N] = null;

        // Redimensiona se estiver 1/4 cheio
        if(N > 0 && N == keys.length / 4)
            resize(keys.length / 2);

        assert check();
    } 

    // Deleta o minimo e seu valor associado
    public void deleteMin() {
        if(isEmpty())
            throw new NoSuchElementException("Tabela de simbolo vazia.");
        delete(min());
    }

    // Deleta o maximo e seu valor associado
    public void deleteMax() {
        if(isEmpty()) throw new NoSuchElementException("Symbol table underflow error");
        delete(max());
    }

    public Key min() {
        if(isEmpty())
            return null;
        return keys[0]; 
    }

    public Key max() {
        if(isEmpty())
            return null;
        return keys[N-1];
    }

    public Key select(int k) {
        if(k < 0 || k >= N)
            return null;
        return keys[k];
    }

    public Key floor(Key key) {
        int i = rank(key);
        if(i < N && key.compareTo(keys[i]) == 0)
            return keys[i];
        if(i == 0)
            return null;
        else
            return keys[i-1];
    }

    public Key ceiling(Key key) {
        int i = rank(key);
        if (i == N)
            return null; 
        else
            return keys[i];
    }

    public int size(Key lo, Key hi) {
        if(lo.compareTo(hi) > 0)
            return 0;
        if(contains(hi))
            return rank(hi) - rank(lo) + 1;
        else
            return rank(hi) - rank(lo);
    }

    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        Queue<Key>
            queue = new Queue<Key>();

        if(lo == null && hi == null)
            return queue;
        if(lo == null)
            throw new NullPointerException("lo e´ null em keys()");
        if(hi == null)
            throw new NullPointerException("hi is null in keys()");
        if(lo.compareTo(hi) > 0)
            return queue;
        for(int i = rank(lo); i < rank(hi); i++) 
            queue.enqueue(keys[i]);
        if(contains(hi))
            queue.enqueue(keys[rank(hi)]);
        return queue; 
    }


   /*****************************************************************************
    *  Check internal invariants
    *****************************************************************************/

    private boolean check() {
        return isSorted() && rankCheck();
    }

    // Verifica se as chaves estao em ordem crescente
    private boolean isSorted() {
        for(int i = 1; i < size(); i++)
            if(keys[i].compareTo(keys[i - 1]) < 0)
                return false;
        return true;
    }

    // Verifica se rank(select(i)) = i
    private boolean rankCheck() {
        for(int i = 0; i < size(); i++)
            if(i != rank(select(i)))
                return false;
        for(int i = 0; i < size(); i++)
            if (keys[i].compareTo(select(rank(keys[i]))) != 0)
                return false;
        return true;
    }
}
