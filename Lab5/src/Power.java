import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Power extends Node{
    double p; //wykładnik
    Node arg; // aby móc wywołać np Power(Sum(x,2),-2)

    Power(Node n, double p){
        this.arg = n;
        this.p = p;
    }

    @Override
    double evaluate(){
        double argVal = arg.evaluate();
        return Math.pow(argVal,p);
    }

    int getArgumentsCount(){
        return 1;
    }

    @Override
    public String toString(){
        StringBuilder b = new StringBuilder();
        DecimalFormat format = new DecimalFormat("0.#####",new DecimalFormatSymbols(Locale.US));
        if(sign < 0)
            b.append("-");
        int argSign = getSign();
        int cnt = arg.getArgumentsCount();

        boolean useBracket = false;
        if(argSign < 0 || cnt > 1)
            useBracket = true;
        String argString = arg.toString();

        if(useBracket)
            b.append("(");
        b.append(argString);
        if(useBracket)
            b.append(")");

        b.append("^");
        b.append(format.format(p));
        return b.toString();
    }
}