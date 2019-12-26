import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Locale;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class DownloadExample {

    static AtomicInteger count = new AtomicInteger(0);
    static Semaphore sem = new Semaphore(0);

    public static void main(String[] args){
        DownloadExample.sequentialDownload();
        DownloadExample.concurrentDownload();
        DownloadExample.concurrentDownloadv2();
        try {
            DownloadExample.concurrentDownloadv3();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // lista plików do pobrania
    static String [] toDownload = {
            "http://home.agh.edu.pl/pszwed/wyklad-c/01-jezyk-c-intro.pdf",
            "http://home.agh.edu.pl/~pszwed/wyklad-c/02-jezyk-c-podstawy-skladni.pdf",
            "http://home.agh.edu.pl/~pszwed/wyklad-c/03-jezyk-c-instrukcje.pdf",
            "http://home.agh.edu.pl/~pszwed/wyklad-c/04-jezyk-c-funkcje.pdf",
            "http://home.agh.edu.pl/~pszwed/wyklad-c/05-jezyk-c-deklaracje-typy.pdf",
            "http://home.agh.edu.pl/~pszwed/wyklad-c/06-jezyk-c-wskazniki.pdf",
            "http://home.agh.edu.pl/~pszwed/wyklad-c/07-jezyk-c-operatory.pdf",
            "http://home.agh.edu.pl/~pszwed/wyklad-c/08-jezyk-c-lancuchy-znakow.pdf",
            "http://home.agh.edu.pl/~pszwed/wyklad-c/09-jezyk-c-struktura-programow.pdf",
            "http://home.agh.edu.pl/~pszwed/wyklad-c/10-jezyk-c-dynamiczna-alokacja-pamieci.pdf",
            "http://home.agh.edu.pl/~pszwed/wyklad-c/11-jezyk-c-biblioteka-we-wy.pdf",
            "http://home.agh.edu.pl/~pszwed/wyklad-c/preprocesor-make-funkcje-biblioteczne.pdf",
    };

    static class Downloader implements Runnable {
        private final String url;

        Downloader(String url) {
            this.url = url;
        }

        public void run() {
            String fileName = url.substring(url.lastIndexOf("/")+1);

            try (InputStream in = new URL(url).openStream(); FileOutputStream out = new FileOutputStream(fileName)) {
                for (; ; ) {
                    int znak = in.read();
                    if (znak < 0)
                        break;
                    out.write(znak);
                    // czytaj znak z in
                    // jeśli <0 break
                    //zapisz znak do out
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Done:" + fileName);

            count.incrementAndGet();
            sem.release();
        }
    }


    static void sequentialDownload() {
        double t1 = System.nanoTime() / 1e6;
        for (String url : toDownload) {
            new Downloader(url).run();
        }
        double t2 = System.nanoTime() / 1e6;
        System.out.println("sequentialDownload");
        System.out.printf(Locale.US, "t2-t1=%f\n", t2 - t1);
    }

    static void concurrentDownload() {
        double t1 = System.nanoTime() / 1e6;
        for (String url : toDownload) {
            new Thread(new Downloader(url)).start();
        }
        double t2 = System.nanoTime() / 1e6;
        System.out.println("concurrentDownloadv1");
        System.out.printf(Locale.US, "t2-t1=%f\n", t2 - t1);
    }
    static void concurrentDownloadv2() {
        double t1 = System.nanoTime() / 1e6;
        for (String url : toDownload) {
            new Thread(new Downloader(url)).start();
        }
        while (count.get() != toDownload.length){
            Thread.yield();
        }
        double t2 = System.nanoTime() / 1e6;
        System.out.println("concurrentDownloadv2");
        System.out.printf(Locale.US, "t2-t1=%f\n", t2 - t1);
    }

    static void concurrentDownloadv3() throws InterruptedException{
        double t1 = System.nanoTime() / 1e6;
        for (String url : toDownload) {
            new Thread(new Downloader(url)).start();
        }
        sem.acquire(toDownload.length);

        double t2 = System.nanoTime() / 1e6;
        System.out.println("concurrentDownloadv3");
        System.out.printf(Locale.US, "t2-t1=%f\n", t2 - t1);
    }





}
