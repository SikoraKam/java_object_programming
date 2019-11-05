import java.util.Locale;

public class Main {


    //TEST 1
    static void buildAndPrint() {
        Variable x = new Variable("x");
        Node exp = new Sum()
                .add(2.1, new Power(x, 3))
                .add(new Power(x, 2))
                .add(-2, x)
                .add(7);
        System.out.println(exp.toString());
    }

    //TEST 2
    static void buildAndEvaluate() {
        Variable x = new Variable("x");
        Node exp = new Sum()
                .add(new Power(x, 3))
                .add(-2, new Power(x, 2))
                .add(-1, x)
                .add(2);
        for (double v = -5; v < 5; v += 0.1) {
            x.setValue(v);
            System.out.printf(Locale.US, "f(%f)=%f\n", v, exp.evaluate());
        }
    }

    //TEST 3
    static void defineCircle(){
        Variable x = new Variable("x");
        Variable y = new Variable("y");


        Node circle = new Sum()
                .add(new Power(x,2))
                .add(new Power(y,2))
                .add(8,x)
                .add(4,y)
                .add(16);
        System.out.println(circle.toString());

        //drugi sposob stworzenia koła, tak aby móc się dostać do value w Constant i Variable
        Constant a = new Constant(16);
        Constant xC = new Constant(8);
        Constant yC = new Constant(4);
        Node circle2 = new Sum()
                .add(new Power(x,2))
                .add(new Power(y,2))
                .add(xC,x)
                .add(yC,y)
                .add(16);
        System.out.println(circle2.toString());

        double xv = 100*(Math.random()-.5);
        double yv = 100*(Math.random()-.5);
        x.setValue(xv);
        y.setValue(yv);
        double fv = circle2.evaluate();
        System.out.print(String.format("Punkt (%f,%f) leży %s koła %s",xv,yv,(fv<0?"wewnątrz":"na zewnątrz"),circle2.toString()));

        //Znajdź i wypisz 100 punktów leżących wewnątrz okręgu

        //(x- x1)^2 + (y-y1)^2 = r^2
        double x1 = -(xC.getConstantValue()/2);
        double y1 = -(yC.getConstantValue()/2);

        double r = Math.sqrt(Math.pow(x1,2) + Math.pow(y1,2) - a.getConstantValue());


    }

    public static void main(String [] args){

        buildAndPrint();
        System.out.println();
        buildAndEvaluate();
        System.out.println();
        defineCircle();
    }
}
