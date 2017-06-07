public class G extends Diophantine implements Comparable<Diophantine>{
    long
        c,
        d;

    public G(long c, long d){
        this.c   = c;
        this.d   = d;
        super.sum = 3*c*c*c + 4*d*d*d*d;
    }

    public String toString(){
        return "3 * " + c + "^3 + " + "4 * " + d + "^4";
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
