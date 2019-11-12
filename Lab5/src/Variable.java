public class Variable extends Node{
    String name;
    Double value;

    Variable(String name){
        this.name = name;
    }

    void setValue(double d){
        this.value = d;
    }

    @Override
    double evaluate(){
        return sign*value;
    }

    @Override
    public String toString(){
        String sgn = sign<0 ? "-" : "";
        return sgn + name;
    }
    @Override
    Node diff(Variable var){
        if(var.name.equals(this.name))
            return new Constant(sign);
        else
            return new Constant(0);
    }
    boolean isZero(){
        return this.value==0;
    }
}