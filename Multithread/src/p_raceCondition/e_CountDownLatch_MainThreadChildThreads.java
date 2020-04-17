package p_raceCondition;

import java.util.Date;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;

/**
 * Child threads should finish before main thread starts
 */
public class e_CountDownLatch_MainThreadChildThreads implements Runnable {

    private CountDownLatch countDownLatch;
    private String name;

    public e_CountDownLatch_MainThreadChildThreads(CountDownLatch countDownLatch, String name){
        this.countDownLatch = countDownLatch;
        this.name = name;
    }

    public void run(){

        while(true) {
            try {
                sleep(ThreadLocalRandom.current().nextInt(1000, 2000));

                // I have Finished processing My task. Reducing the latch value
                countDownLatch.countDown();

                // Send Message to Player
                System.out.printf("%s \t %s \t\t Reduced the CountDown latch to : %d \n", new Date().toString(), name, countDownLatch.getCount());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                break;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        CountDownLatch latch = new CountDownLatch(3);
        executorService.submit(new e_CountDownLatch_MainThreadChildThreads(latch, "thread-1"));
        executorService.submit(new e_CountDownLatch_MainThreadChildThreads(latch, "thread-2"));
        executorService.submit(new e_CountDownLatch_MainThreadChildThreads(latch, "thread-3"));

        latch.await();

        System.out.println("\n"+new Date()+ "\t Thread-Main \t Started ");

        executorService.shutdown();
    }

}
