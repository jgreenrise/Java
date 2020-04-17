package p_raceCondition;

import java.util.Date;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;

/**
 * We have three players in a game.
 * Lets say we have to send message to each of them at same time to avoid any discrimination.
 * We can use Cyclic Barrier.
 */
public class c_Cyclic_Barrier_example2 implements Runnable {

    private CyclicBarrier cyclicBarrier;
    private String name;

    public c_Cyclic_Barrier_example2(CyclicBarrier cyclicBarrier, String name){
        this.cyclicBarrier = cyclicBarrier;
        this.name = name;
    }

    public void run(){

        while(true) {
            try {
                sleep(ThreadLocalRandom.current().nextInt(1000, 2000));

                // Wait for sibling threads to complete
                cyclicBarrier.await();

                // Send Message to Player
                System.out.println(name +" sent message at time: "+ new Date());

            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }finally {
                break;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
        executorService.submit(new c_Cyclic_Barrier_example2(cyclicBarrier, "thread1"));
        executorService.submit(new c_Cyclic_Barrier_example2(cyclicBarrier, "thread2"));
        executorService.submit(new c_Cyclic_Barrier_example2(cyclicBarrier, "thread3"));

        System.out.println("\n Completed Sending Messages");

        executorService.shutdown();


    }

}
