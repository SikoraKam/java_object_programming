import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Max {
    static BlockingQueue<Integer> results = new ArrayBlockingQueue<Integer>(100);
    static int [] array;
    static void initArray(int size){
        array = new Random().ints(size,1,100000000).toArray();
    }


    static class MaxCalc extends Thread{
        private final int start;
        private final int end;
        int max = 0;

        MaxCalc(int start,int end){
            this.start = start;
            this.end = end;
        }

        public void run(){

            for(int i=start;i<end;i++){
                if(array[i] > max)
                    max = array[i];
            }
            System.out.printf(Locale.US,"%d-%d max=%d\n",start,end,max);
            try {
                results.put(max);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static void parallelMax(int cnt) throws InterruptedException {
        MaxCalc [] threads = new MaxCalc[cnt];

        int len = array.length / cnt;
        int j=0;
        for(int i=0; i<array.length; i+=len){
            threads[j] = new MaxCalc(i,i+len);
            j++;
        }
        double t1 = System.nanoTime()/1e6;
        for (MaxCalc elem : threads){
            elem.start();
        }

        int maxOfAll = 0;
        int tempMax = 0;
        for(MaxCalc elem : threads){
            tempMax = results.take();
            if(maxOfAll < tempMax)
                maxOfAll = tempMax;
        }
        double t2 = System.nanoTime()/1e6;

        double t3 = System.nanoTime()/1e6;
        System.out.printf(Locale.US,"size = %d cnt=%d >  t2-t1=%f t3-t1=%f max=%d\n",
                array.length,
                cnt,
                t2-t1,
                t3-t1,
                maxOfAll);

    }


    public static void main(String [] args){
        initArray(100000000);
        try {
            parallelMax(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
