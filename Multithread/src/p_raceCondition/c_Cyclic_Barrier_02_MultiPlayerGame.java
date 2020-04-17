package p_raceCondition;

import java.util.Date;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;

/**
 * We have three players in a game.
 * Lets say we have to send message to each of them at same time to avoid any discrimination.
 * We can use Cyclic Barrier.
 */
public class c_Cyclic_Barrier_02_MultiPlayerGame {

    public static void main(String[] args)  {

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(4);

        for (int i = 0; i < 3; i++) {
            executorService.submit(new InnerClass(cyclicBarrier, "thread1"));
            executorService.submit(new InnerClass2(cyclicBarrier, "thread2"));
            executorService.submit(new InnerClass3(cyclicBarrier, "thread3"));

            try {
                cyclicBarrier.await();
            } catch (BrokenBarrierException | InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("\n Completed Sending Messages\n");

        }

        executorService.shutdown();
    }

    private static class InnerClass implements Runnable {
        private CyclicBarrier cyclicBarrier;
        private String name;

        public InnerClass(CyclicBarrier cyclicBarrier, String name) {
            this.cyclicBarrier = cyclicBarrier;
            this.name = name;
        }

        public void run() {
            try {
                sleep(ThreadLocalRandom.current().nextInt(1000, 2000));
                // Wait for sibling threads to complete
                cyclicBarrier.await();
                // Send Message to Player
                System.out.println(name + " sent message at time: " + new Date());
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    private static class InnerClass2 implements Runnable {
        private CyclicBarrier cyclicBarrier;
        private String name;

        public InnerClass2(CyclicBarrier cyclicBarrier, String name) {
            this.cyclicBarrier = cyclicBarrier;
            this.name = name;
        }

        public void run() {
            try {
                sleep(ThreadLocalRandom.current().nextInt(1000, 2000));
                // Wait for sibling threads to complete
                cyclicBarrier.await();
                // Send Message to Player
                System.out.println(name + " sent message at time: " + new Date());
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    private static class InnerClass3 implements Runnable {
        private CyclicBarrier cyclicBarrier;
        private String name;

        public InnerClass3(CyclicBarrier cyclicBarrier, String name) {
            this.cyclicBarrier = cyclicBarrier;
            this.name = name;
        }

        public void run() {
            try {
                sleep(ThreadLocalRandom.current().nextInt(1000, 2000));
                // Wait for sibling threads to complete
                cyclicBarrier.await();
                // Send Message to Player
                System.out.println(name + " sent message at time: " + new Date());
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
