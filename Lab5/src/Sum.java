import java.util.ArrayList;
import java.util.List;

public class Sum extends Node {
    List<Node> args = new ArrayList<>();

    Sum(){}

    Sum(Node n1, Node n2){
        args.add(n1);
        args.add(n2);
    }

    Sum add(Node n){
        args.add(n);
        return this;
    }

    Sum add(double c){
        args.add(new Constant(c));
        return this;
    }

    Sum add(double c, Node n){
        Node mul = new Prod(c,n);
        args.add(mul);
        return this;
    }
    //wlasne
    Sum add(Node n1, Node n2){
        Node mul = new Prod(n1,n2);
        args.add(mul);
        return this;
    }

    @Override
    double evaluate(){
        double result = 0;

        for(Node element : args){
            result += element.evaluate();
        }
        return sign*result;
    }

    int getArgumentsCount(){
        return args.size();
    }

    @Override
    public String toString(){  //dokonczyc
        StringBuilder b = new StringBuilder();
        if(sign<0)
            b.append("-(");
        for(Node element : args) {
            b.append(element.toString());
            if(element != args.get(args.size()-1)) {
                if (element.sign < 0)
                    b.append(" - ");
                else if (element.sign > 0)
                    b.append(" + ");
            }
        }
        if(sign<0)
            b.append(")");
        return b.toString();
    }
}