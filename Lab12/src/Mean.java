import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Mean {
    static BlockingQueue<Double> results = new ArrayBlockingQueue<Double>(100);
    static double [] array;
    static void initArray(int size){
//        array = new double[size];
//        for (int i=0; i<size; i++) {
//        array[i] = Math.random() * size / (i + 1);
//
//        }
        array = new Random().doubles(size,1,100).toArray();
    }
    public static void main(String [] args){
        initArray(128000000);
        try {
           // parallelMean(10);

            for(int cnt:new int[]{1,2,4,8,16,32,64,128}){
                parallelMeanV2(cnt);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class MeanCalc extends Thread{
        private final int start;
        private final int end;
        double mean = 0;

        MeanCalc(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public void run(){
            double sum = 0;

            for(int i=start; i<end; i++){
                sum+=array[i];
            }
            mean = sum / (end - start);
            System.out.printf(Locale.US,"%d-%d mean=%f\n",start,end,mean);

            try {
                results.put(mean);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * Oblicza średnią wartości elementów tablicy array uruchamiając równolegle działające wątki.
     * Wypisuje czasy operacji
     * @param cnt - liczba wątków
     */
    static void parallelMean(int cnt) throws  InterruptedException{
        // utwórz tablicę wątków
        MeanCalc [] threads = new MeanCalc[cnt];
        // utwórz wątki, podziel tablice na równe bloki i przekaż indeksy do wątków
        // załóż, że array.length dzieli się przez cnt)
        int len = array.length/cnt;
        int j=0;
        for(int i=0; i<array.length; i+=len){
            threads[j] = new MeanCalc(i,i+len);
            j++;
        }
        double t1 = System.nanoTime()/1e6;
        for (MeanCalc thread : threads){
            thread.start();
        }
        double t2 = System.nanoTime()/1e6;

        for (MeanCalc mc : threads){
            mc.join();
        }
        double sum = 0;
        for (MeanCalc mc : threads){
            sum += mc.mean;
        }
        double mean = sum/cnt;

        double t3 = System.nanoTime()/1e6;
        System.out.printf(Locale.US,"size = %d cnt=%d >  t2-t1=%f t3-t1=%f mean=%f\n",
                array.length,
                cnt,
                t2-t1,
                t3-t1,
                mean);
    }

    static void parallelMeanV2(int cnt) throws InterruptedException {
        // utwórz tablicę wątków
        MeanCalc [] threads = new MeanCalc[cnt];
        // utwórz wątki, podziel tablice na równe bloki i przekaż indeksy do wątków
        // załóż, że array.length dzieli się przez cnt)
        int len = array.length/cnt;
        int j=0;
        for(int i=0; i<array.length; i+=len){
            threads[j] = new MeanCalc(i,i+len);
            j++;
        }
        double t1 = System.nanoTime()/1e6;
        for (MeanCalc thread : threads){
            thread.start();
        }


        double mean = 0;
        for (MeanCalc mc : threads){
            mean += results.take();
        }
        double t2 = System.nanoTime()/1e6;

        mean = mean/cnt;
        double t3 = System.nanoTime()/1e6;
        System.out.printf(Locale.US,"size = %d cnt=%d >  t2-t1=%f t3-t1=%f mean=%f\n",
                array.length,
                cnt,
                t2-t1,
                t3-t1,
                mean);
    }
}
