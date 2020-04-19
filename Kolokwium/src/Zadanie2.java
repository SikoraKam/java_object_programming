import java.util.Locale;
import java.util.Random;

import java.util.concurrent.Semaphore;

public class Zadanie2 {
    static Semaphore sem = new Semaphore(0);
    static int [] array;
    static void initArray(int size){
        array = new int[size];
        for(int i=0;i<size;i++){
            array[i]= i+1;
        }
    }
    static class Calc extends Thread{
        private final int start;
        private final int end;

        Calc(int start, int end){
            this.start = start;
            this.end=end;
        }

        public void run(){
            for(int i = 0; i < (end-start)/2; i++) {

                int buff = array[start + i];
                array[start + i] = array[end - i - 1];
                array[end - i-1] = buff;
            }
            sem.release();
        }
    }


    static void parallel(int cnt) throws InterruptedException {
        Calc threads[] = new Calc[cnt];

        int len = array.length / cnt;
        int j=0;
        for(int i=0; i<array.length; i+=len){
            threads[j] = new Calc(i,i+len);
            j++;
        }

        for(Calc elem : threads){
            elem.start();
        }
        sem.acquire();
    }
    static void wypisz(){
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + ", ");
        }
    }

    public static void main(String[] args){
        initArray(1000000);
        wypisz();
        System.out.println();
        double t1 = System.nanoTime() / 1e6;
        try {
            parallel(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        double t2 = System.nanoTime() / 1e6;
        System.out.printf(Locale.US, "t2-t1=%f\n", t2 - t1);
        wypisz();
    }
}
