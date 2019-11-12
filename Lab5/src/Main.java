import java.util.ArrayList;
import java.util.List;
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
        //potrzebne do uniwersalengo znjadywania 100 punktow lezacych wew okregu
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
        //wspolrzedne srodka
        double x1 = -(xC.getConstantValue()/2);
        double y1 = -(yC.getConstantValue()/2);
        //promien
        double r = Math.sqrt(Math.pow(x1,2) + Math.pow(y1,2) - a.getConstantValue());

        //losowanie punktow wewnatz kola i zapisywanie do listy
        List <Double> xArray = new ArrayList<Double>();
        List <Double> yArray = new ArrayList<Double>();

        int maxX = (int)(x1+r);
        int minX = (int)(x1-r);
        int maxY = (int)(y1+r);
        int minY = (int)(y1-r);
        double rangeX = maxX - minX;
        double rangeY = maxY - minY ;
        for(int i=0; i <= 100; i++){
            double xr = Math.random() * rangeX + minX;
            double yr = Math.random() * rangeY + minY;
            //warunek odleglosci punktu od srodka
            while(Math.sqrt(Math.pow((x1-xr),2) + Math.pow((y1-yr),2)) > r)
                yr = Math.random() * rangeY + minY;
            xArray.add(xr);
            yArray.add(yr);
        }

        //sprawdzenie czy naleza do wewnatrz kola
        boolean flag = true;
        for(int i=0; i<xArray.size(); i++){
            x.setValue(xArray.get(i));
            y.setValue(yArray.get(i));
            fv = circle2.evaluate();
            if(fv>0){
                flag = false;
                throw new RuntimeException(String.format("Punkt (%f ; %f) nie nalezy do wnetrza kola", xArray.get(i),yArray.get(i)));
            }
            if (flag == false)
                System.out.println("Coś nie tak");
        }

        //wyswietlenie tych punktow - opcjonalne
        System.out.println("\n100 pkt wewnatrz koła");
        for(int i=0; i<xArray.size(); i++){
            System.out.printf("%f - %f \n",xArray.get(i),yArray.get(i));
        }
    }

    public static void main(String [] args){

        buildAndPrint();
        System.out.println();
        buildAndEvaluate();
        System.out.println();
        try {
            defineCircle();
        }catch (RuntimeException e){
            return;
        }

    }
}
