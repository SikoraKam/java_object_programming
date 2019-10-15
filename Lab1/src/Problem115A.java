import java.util.Scanner;

public class Problem115A {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        System.out.println("podaj ilosc pracownkow: ");
        int n = scan.nextInt();
        int[] managers = new int[n];

    //------------------------ przypisanie szefow
        for (int i=0; i<n; i++){
            int s = scan.nextInt();
            managers[i] = s;
        }

        int maxDepth = -1;
        for(int i=0; i<n; i++){
            int depth = 1;
            int actualNode = i;
            while(managers[actualNode] != -1){
                actualNode = managers[actualNode] - 1; // -1 poniewaz z tresci zadania numerujemy pracownikow od 1, wiec potzrebujemy idneks o 1 mniejszy
                depth++;
            }
            maxDepth = Math.max(maxDepth,depth);
        }
        System.out.println(maxDepth);
    }
}
