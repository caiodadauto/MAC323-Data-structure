/*************************************************************************
 * Caio Vinicius Dadauto   7994808             Estrutura de Dados 2      *
 *                                             EP2                       *
 * Tabela de simbolos com arvore rubo negra esquerdista                  *
 * Programa baseado em:                                                  *
 *  http://algs4.cs.princeton.edu/33balanced/RedBlackBST.java.html       *
 *                                                                       *
 *************************************************************************/

import java.util.NoSuchElementException;

public class RedBlackBST<Key extends Comparable<Key>, Value> {
    private static final boolean RED   = true;
    private static final boolean BLACK = false;

    private Node root;

    private class Node {
        private Key key;           // Chave associada
        private Value val;         // Valor associado
        private Node left, right;  // Links para os no´s a esquerda e direita
        private boolean color;     // Cor do link pertencente ao no´ pai
        private int N;             // Numero de no´s na subarvore

        public Node(Key key, Value val, boolean color, int N) {
            this.key   = key;
            this.val   = val;
            this.color = color;
            this.N     = N;
        }
    }

   /*************************************************************************
    *  Metodos que auxiliam a manipulacao de no´s                           *
    *************************************************************************/

    // Verifica se o link que aponta para o no´ x e´ vermelho
    private boolean isRed(Node x) {
        if(x == null)
            return false;

        return (x.color == RED);
    }

    // Retorna o numero de no´s na subarvore com x como raiz
    private int size(Node x) {
        if(x == null)
            return 0;

        return x.N;
    } 


   /*************************************************************************
    *  Metodos que auxiliam na determinacao do tamanho da arvore            *
    *************************************************************************/

    // Retorna o numero de pares chave-valor contido na tabela de simbolos
    public int size() {
        return size(root);
    }

    // Verifica se a tabela esta vazia
    public boolean isEmpty() {
        return root == null;
    }

   /*************************************************************************
    *  Metodos padroes de tabelas BST
    *************************************************************************/

    // Retorna o valor associado a chave key
    public Value get(Key key) {
        return get(root, key);
    }

    // Retorna valor associado a chave key na arvore com raiz x
    private Value get(Node x, Key key) {
        while(x != null) {
            int cmp = key.compareTo(x.key);
            if     (cmp < 0) x = x.left;
            else if(cmp > 0) x = x.right;
            else             return x.val;
        }

        return null;
    }

    // Verifica se a chave key dada esta´ na arvore
    public boolean contains(Key key) {
        return get(key) != null;
    }

   /*************************************************************************
    *  Insercao Rubo negra
    *************************************************************************/

    // Insere o par chave-valor
    public void put(Key key, Value val) {
        root       = put(root, key, val);
        root.color = BLACK;
    }

    // Funcao recursiva para auxiliar put
    private Node put(Node h, Key key, Value val) { 
        if(h == null)
            return new Node(key, val, RED, 1);

        int cmp = key.compareTo(h.key);
        if     (cmp < 0) h.left  = put(h.left,  key, val); 
        else if(cmp > 0) h.right = put(h.right, key, val); 
        else             h.val   = val;

        // Mantem a arvore esquerdista, ou seja, elimina os links vemelhos a direita
        //a ordem das condicoes sao de suma importancia para o funcionamento do algoritimo
        if(isRed(h.right) && !isRed(h.left))      h = rotateLeft(h);
        if(isRed(h.left)  &&  isRed(h.left.left)) h = rotateRight(h);
        if(isRed(h.left)  &&  isRed(h.right))     flipColors(h);

        h.N = size(h.left) + size(h.right) + 1;

        return h;
    }

   /*************************************************************************
    * Remocao em arvores Rubo negras
    *************************************************************************/

    // Remove o minimo
    public void deleteMin() {
        if(isEmpty())
            throw new NoSuchElementException("BST underflow");

        // Se ambos os filhos da raiz sao negros, torna-se a raiz vermelha
        if(!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = deleteMin(root);
        if(!isEmpty())
            root.color = BLACK;
    }

    // Remove o minimo com raiz h dada
    private Node deleteMin(Node h) { 
        if(h.left == null)
            return null;

        if(!isRed(h.left) && !isRed(h.left.left))
            h = moveRedLeft(h);

        h.left = deleteMin(h.left);
        return balance(h);
    }


    // Remove o maximo
    public void deleteMax() {
        if(isEmpty()) throw new NoSuchElementException("BST underflow");

        // Se ambos os filhos da raiz sao negros, torna-se a raiz vermelha
        if(!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = deleteMax(root);
        if(!isEmpty())
            root.color = BLACK;
    }

    // Remove o maximo com raiz h dada
    private Node deleteMax(Node h) { 
        if(isRed(h.left))
            h = rotateRight(h);

        if(h.right == null)
            return null;

        if(!isRed(h.right) && !isRed(h.right.left))
            h = moveRedRight(h);

        h.right = deleteMax(h.right);
        return balance(h);
    }

    // Remove a chave key dada
    public void delete(Key key) { 
        if(!contains(key)) {
            System.err.println("Tabela de simbolos nao contem a chave " + key);
            return;
        }

        // Se ambos os filhos da raiz sao negros, torna-se a raiz vermelha
        if(!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = delete(root, key);
        if(!isEmpty())
            root.color = BLACK;
    }

    // Remove a chave key dada na arvore com raiz h dada
    private Node delete(Node h, Key key) { 
        if(key.compareTo(h.key) < 0)  {
            if(!isRed(h.left) && !isRed(h.left.left))
                h = moveRedLeft(h);
            h.left = delete(h.left, key);
        }
        else {
            if(isRed(h.left))
                h = rotateRight(h);
            if(key.compareTo(h.key) == 0 && (h.right == null))
                return null;
            if(!isRed(h.right) && !isRed(h.right.left))
                h = moveRedRight(h);
            if(key.compareTo(h.key) == 0) {
                Node x  = min(h.right);
                h.key   = x.key;
                h.val   = x.val;
                h.right = deleteMin(h.right);
            }
            else h.right = delete(h.right, key);
        }
        return balance(h);
    }

   /*************************************************************************
    *  Funcoes para auxilio em arvores rubo negras
    *************************************************************************/

    // Rotaciona o no´s h para direita, corrige no´s com 4 links temporarios onde h possui chave com maior valor
    private Node rotateRight(Node h) {
        Node x        = h.left;
        h.left        = x.right;
        x.right       = h;
        x.color       = x.right.color;
        x.right.color = RED;
        x.N           = h.N;
        h.N           = 1 + size(h.left) + size(h.right);
        return x;
    }

    // Rotaciona o no´s h para esquerda, corrige no´s vermelhos temporarios a direita
    private Node rotateLeft(Node h) {
        Node x       = h.right;
        h.right      = x.left;
        x.left       = h;
        x.color      = x.left.color;
        x.left.color = RED;
        x.N          = h.N;
        h.N          = 1 + size(h.left) + size(h.right);
        return x;
    }

    // Torna os links dos filhos negros e o do pai vermelho, corrige no´s com 4 links perfeitamente balanceados
    private void flipColors(Node h) {
        h.color       = !h.color;
        h.left.color  = !h.left.color;
        h.right.color = !h.right.color;
    }

    // Assumindo que h é vermelhor e ambos (h.left e h.left.left)
    //sao negros, entao torna h.left ou um dos filhos de h vermelho
    private Node moveRedLeft(Node h) {
        flipColors(h);
        if(isRed(h.right.left)) { 
            h.right = rotateRight(h.right);
            h       = rotateLeft(h);
            flipColors(h);
        }
        return h;
    }

    // Assumindo que h é vermelhor e ambos (h.right e h.right.left)
    //sao negros, entao torna h.right ou um dos filhos de h vermelho
    private Node moveRedRight(Node h) {
        flipColors(h);
        if(isRed(h.left.left)) { 
            h = rotateRight(h);
            flipColors(h);
        }
        return h;
    }

    // Restaura a invariante de uma arvore rubo negra
    private Node balance(Node h) {
        if(isRed(h.right))                      h = rotateLeft(h);
        if(isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if(isRed(h.left) && isRed(h.right))     flipColors(h);

        h.N = 1 + size(h.left) + size(h.right);
        return h;
    }


   /*************************************************************************
    *  Funcoes uteis
    *************************************************************************/

    // Altura da arvore
    public int height() { return height(root); }
    private int height(Node x) {
        if(x == null)
            return -1;
        return 1 + Math.max(height(x.left), height(x.right));
    }

   /*************************************************************************
    *  Metodos referentes a tabelas de simbolos ordenadas.
    *************************************************************************/

    // Retorna a menor chave
    public Key min() {
        if(isEmpty())
            return null;
        return min(root).key;
    } 

    // Retorna a menor chave para na subarvore com raiz x dada
    private Node min(Node x) { 
        if(x.left == null)
            return x; 
        else
            return min(x.left); 
    }

    // Retorna a maior chave
    public Key max() {
        if(isEmpty())
            return null;

        return max(root).key;
    } 

    // Retorna a maior chave para na subarvore com raiz x dada
    private Node max(Node x) { 
        if(x.right == null)
            return x; 
        else
            return max(x.right); 
    } 

    // Retorna o chao da chave dada key
    public Key floor(Key key) {
        Node x = floor(root, key);
        if (x == null)
            return null;
        else
            return x.key;
    }    

    // Retorna o chao da chave dada key na subarvore com raiz x
    private Node floor(Node x, Key key) {
        if (x == null)
            return null;

        int cmp = key.compareTo(x.key);
        if(cmp == 0) return x;
        if(cmp < 0)  return floor(x.left, key);

        Node t = floor(x.right, key);
        if (t != null) return t; 
        else           return x;
    }

    // Retorna o teto da chave dada key
    public Key ceiling(Key key) {  
        Node x = ceiling(root, key);
        if (x == null) return null;
        else           return x.key;  
    }

    // Retorna o teto da chave dada key na subarvore com raiz x
    private Node ceiling(Node x, Key key) {  
        if (x == null)
            return null;

        int cmp = key.compareTo(x.key);
        if(cmp == 0) return x;
        if(cmp > 0)  return ceiling(x.right, key);

        Node t = ceiling(x.left, key);
        if (t != null) return t; 
        else           return x;
    }


    // Retorna a chave de rank k
    public Key select(int k) {
        if (k < 0 || k >= size())
            return null;

        Node x = select(root, k);
        return x.key;
    }

    // Retorna a chave de rank k na subarvore de raiz x
    private Node select(Node x, int k) {
        int t = size(x.left);

        if     (t > k) return select(x.left,  k); 
        else if(t < k) return select(x.right, k - t - 1); 
        else           return x; 
    } 

    // Numeros de chaves menores que a chave dada (rank de key dada)
    public int rank(Key key) {
        return rank(key, root);
    } 

    // Rank de key dada na subarvore de raiz x
    private int rank(Key key, Node x) {
        if(x == null)
            return 0; 

        int cmp = key.compareTo(x.key); 
        if     (cmp < 0) return rank(key, x.left); 
        else if(cmp > 0) return 1 + size(x.left) + rank(key, x.right); 
        else             return size(x.left); 
    } 

   /***********************************************************************
    *  Iterable
    ***********************************************************************/

    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        Queue<Key> queue = new Queue<Key>();
        keys(root, queue, lo, hi);
        return queue;
    } 

    private void keys(Node x, Queue<Key> queue, Key lo, Key hi) { 
        if(x == null)
            return;

        int cmplo = lo.compareTo(x.key); 
        int cmphi = hi.compareTo(x.key);

        if(cmplo < 0)
            keys(x.left, queue, lo, hi); 
        if(cmplo <= 0 && cmphi >= 0)
            queue.enqueue(x.key); 
        if(cmphi > 0)
            keys(x.right, queue, lo, hi); 
    } 

    public int size(Key lo, Key hi) {
        if(lo.compareTo(hi) > 0) return 0;
        if(contains(hi))         return rank(hi) - rank(lo) + 1;
        else                     return rank(hi) - rank(lo);
    }


   /*************************************************************************
    *  Verifica a coerencia da estrutura rubo negra
    *************************************************************************/
    private boolean check() {
        if(!isBST())            StdOut.println("Nao esta em ordenada");
        if(!isSizeConsistent()) StdOut.println("A contagem na subarvore esta inconsistente");
        if(!isRankConsistent()) StdOut.println("Ranks inconsistentes");
        if(!is23())             StdOut.println("Nao e´ uma arvore 2-3");
        if(!isBalanced())       StdOut.println("Nao balanceado");
        return isBST() && isSizeConsistent() && isRankConsistent() && is23() && isBalanced();
    }

    private boolean isBST() {
        return isBST(root, null, null);
    }

    private boolean isBST(Node x, Key min, Key max) {
        if(x == null)                                return true;
        if(min != null && x.key.compareTo(min) <= 0) return false;
        if(max != null && x.key.compareTo(max) >= 0) return false;

        return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
    } 

    private boolean isSizeConsistent() {
        return isSizeConsistent(root);
    }

    private boolean isSizeConsistent(Node x) {
        if(x == null)
            return true;

        if(x.N != size(x.left) + size(x.right) + 1)
            return false;

        return isSizeConsistent(x.left) && isSizeConsistent(x.right);
    } 

    private boolean isRankConsistent() {
        for(int i = 0; i < size(); i++)
            if(i != rank(select(i)))
                return false;

        for(Key key : keys())
            if(key.compareTo(select(rank(key))) != 0)
                return false;

        return true;
    }

    private boolean is23() {
        return is23(root);
    }

    private boolean is23(Node x) {
        if(x == null)
            return true;
        if(isRed(x.right))
            return false;
        if(x != root && isRed(x) && isRed(x.left))
            return false;

        return is23(x.left) && is23(x.right);
    } 

    private boolean isBalanced() { 
        int black = 0;
        Node x    = root;
        while(x != null) {
            if(!isRed(x)) black++;
            x = x.left;
        }
        return isBalanced(root, black);
    }

    private boolean isBalanced(Node x, int black) {
        if (x == null)
            return black == 0;

        if (!isRed(x))
            black--;

        return isBalanced(x.left, black) && isBalanced(x.right, black);
    }
}
