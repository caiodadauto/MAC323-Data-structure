public class P extends Diophantine implements Comparable<Diophantine>{
    long
        a,
        b;

    public P(long a, long b){
        this.a   = a;
        this.b   = b;
        super.sum = a + 2*b*b;
    }

    public String toString(){
        return a + " + 2 * " + b + "^2";
    }

    public int compareTo(Diophantine that){
        if(super.sum < that.sum)
            return -1;
        else if(super.sum > that.sum)
            return 1;
        else
            return 0;
    }
}
