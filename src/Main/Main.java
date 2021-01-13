/**
 * @author : Hamza Jeljeli
 * @Description : This sample demo is for displaying the importance of Parallel processing to gain time using Executors
 *                when running 100 file download task.
 * @Sample_Output :
 * Using newSingleThreadExecutor ...
 * Task executed for : 17 seconds .
 * Using newWorkStealingPool ...
 * Task executed for : 1 seconds .
 * Using newCachedThreadPool ...
 * Task executed for : 0 seconds .
 */

package Main;

import java.io.InputStreamReader;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        System.out.println("Using newSingleThreadExecutor ...");
        runSampleTask(Executors.newSingleThreadExecutor());
        System.out.println("Using newWorkStealingPool ...");
        runSampleTask(Executors.newWorkStealingPool());
        System.out.println("Using newCachedThreadPool ...");
        runSampleTask(Executors.newCachedThreadPool());
    }

    public static void awaitTerminationAfterShutdown(ExecutorService threadPool) {
        threadPool.shutdown();
        try {
            if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
            }
        } catch (InterruptedException ex) {
            threadPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public static void runSampleTask(ExecutorService executorService) {
        ExecutorService executor = executorService;
        Instant start = Instant.now();
        for (int i = 0; i < 100; i++) {
            executor.submit(() -> {
                try {
                    URL url = new URL("https://raw.githubusercontent.com/hamzajeljeli95/citedeculture/master/intro.json");
                    InputStreamReader reader = new InputStreamReader(url.openStream());
                } catch (Exception e) {
                }
            });
        }
        awaitTerminationAfterShutdown(executor);
        Instant finish = Instant.now();
        System.out.println("Task executed for : " + Duration.between(start, finish).toMillis() / 1000 + " seconds .");
    }
}
