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
    public String toString(){
        StringBuilder b = new StringBuilder();
        if(sign<0)
            b.append("-(");


    }
}
