
abstract public class Node {
    int sign = 1;

    Node minus(){
        this.sign = -1;
        return this;
    }
    Node plus(){
        this.sign = 1;
        return this;
    }

    int getSign() {
        return this.sign;
    }

    //oblicza wartosci wyrazen dla danych wartosci zmiennych wystepujacych w wyrazeniu
    abstract double evaluate();

    //zwraca tekstowa reprezentacje wyrazenia
    public String toString(){
        return "";
    }

    //zwraca liczbę argumenetow wezla
    int getArgumentsCount() {
        return 0;
    }

    abstract Node diff(Variable var);
    abstract  boolean isZero();
}