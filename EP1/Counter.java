/*************************************************************************
 *      Exercicio de Programa 1
 *
 *      Nome: Caio Vinicius Dadauto
 *      Nusp: 7994808
 *
 *  Classe para tipo abstrato Counter, baseada em;
 *     http://algs4.cs.princeton.edu/12oop/Counter.java.html 
 *
/************************************************************************/

public class Counter implements Comparable<Counter>{
    private final String 
        name;
    private int 
        count = 0;

    public Counter(String id){
        this.name  = id;
    } 
    
    public void set(int count){
        this.count = count;
    } 

    public void increment(){
        this.count++;
    } 

    public void decrement(){
        this.count--;
    }

    public int tally(){
        return this.count;
    } 

    public String toString(){
        return this.count + " " + this.name;
    } 

    public int compareTo(Counter that){
        if(this.count < that.count)
            return -1;
        else if(this.count > that.count)
            return +1;
        else
            return  0;
    }
}
