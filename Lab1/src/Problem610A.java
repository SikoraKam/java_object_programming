import java.util.Scanner;

public class Problem610A {
    public static void main(String [] args){
        Scanner scan = new Scanner(System.in);
        int length = scan.nextInt();

        if (length % 2 != 0){
            System.out.println(0);
        }
        int j = length/2 - 1;
        int amountIterations = 0;
        for(int i = 1; i < j; i++){
            amountIterations++;
            j--;
        }
        System.out.println(amountIterations);
    }

}
