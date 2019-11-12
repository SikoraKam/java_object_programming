import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Constant extends Node{
    double value;

    Constant(double value){
        this.sign = value<0 ? -1 : 1; //if value<0 then sign=-1 else sign=1
        this.value = value<0 ? -value : value;
    }
    //wlasne
    double getConstantValue(){
        return this.value;
    }

    @Override
    double evaluate(){
        return sign*value;
    }

    @Override
    public String toString(){
        String sgn = sign<0 ? "(-" : "";
        // Stosując DecimalFormat pozbędziemy się niepotrzebnych zer na końcu wartości double.
        // Inaczej mozna zaastosowac return sgn+Double.toString(value);
        DecimalFormat format = new DecimalFormat("0.#####",new DecimalFormatSymbols(Locale.US));
        if(sign<0)
            return sgn + format.format(value) + ")";
        else
            return sgn + format.format(value);
    }

    @Override
    Node diff(Variable var){
        return new Constant(0);
    }
    boolean isZero(){
        return true;
    }
}