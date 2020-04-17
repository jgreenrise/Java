package p_raceCondition;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class b_Buyers_Barrier extends Thread {

    public static int basketOfChips = 1;
    private static Lock person = new ReentrantLock();

    // Add operations occurs before Multiple operation

    /**
     * 2 ALPHA, 2 BETA workers = 4 total
     * @constructor: number of threads to wait on, before the barrier releases
     *
     * We want all the worker threads to arrive at barrier together. Only
     * once all threads reach, barrier is removed and threads can resume their work.
     */
    private static CyclicBarrier barrier = new CyclicBarrier(10);

    public void run(){
        if(this.getName().contains("APLHA")){
            person.lock();
            try{
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 2000));
                basketOfChips = basketOfChips + 3;
                System.out.println(this.getName() + " added 3 bags");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                person.unlock();
            }
            try {
                /**
                 * Only when all threads reach Await, then each thread can resume execution.
                 * For example, if 2 out of 3 threads have completed processing, they have to enter into wait stage
                 * They have to wait until Thread 3 has also reached await stage.
                 */
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }else{
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }

            person.lock();
            try{
                basketOfChips = basketOfChips * 2;
                System.out.println("\t"+this.getName() + " doubled bags");
            }finally {
                person.unlock();
            }

        }
    }

    public static void main(String[] args) throws InterruptedException {
        b_Buyers_Barrier[] buyers = new b_Buyers_Barrier[10];
        for (int i = 0; i < buyers.length/2; i++) {
            buyers[2*i] = new b_Buyers_Barrier("APLHA-"+i);
            buyers[2*i+1] = new b_Buyers_Barrier("BETA-"+(i+1));
        }

        for (b_Buyers_Barrier buyer: buyers) {
            buyer.start();
        }

        for (b_Buyers_Barrier buyer: buyers) {
            buyer.join();
        }

        System.out.println("We need to buy: "+ b_Buyers_Barrier.basketOfChips+ " bags of chips");
    }

    public b_Buyers_Barrier(String name){
        this.setName(name);
    }

}
