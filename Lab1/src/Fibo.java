import java.util.Scanner;

public class Fibo {
    public static void main(String[] args) {

        //---------------------------------- wczytanie liczby
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        if (n < 1 && n > 45) {
            System.exit(0);
        }
        //---------------------------------- zapełnienie tablicy
        int[] tab = new int[n];

        tab[0] = 0;
        tab[1] = 1;
        for (int i = 2; i < n; i++) {
            tab[i] = tab[i - 1] + tab[i - 2];
        }
        //-------------------------------- wypisanie
        for (int i = 0; i < n; i++) {
            System.out.printf("%d ", tab[i]);
        }
    }
}
