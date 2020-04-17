package p_raceCondition;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class d_Buyers_CountDownLatch extends Thread {

    public static int basketOfChips = 1;
    private static Lock person = new ReentrantLock();
    private static int TOTAL_NUMBER_OF_BUYERS = 10;

    /**
     *  new CountDownLatch(TOTAL_NUMBER_OF_BUYERS/2)
     *  Since we want to execute 5 Alpha threads before 5 beta threads
     */
    //private static CyclicBarrier barrier = new CyclicBarrier(10);
    private static CountDownLatch countDownLatch = new CountDownLatch(TOTAL_NUMBER_OF_BUYERS/2);

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
                //DECREMENT count latch by 1

                /**
                 * Once countdown latches moves from new CountDownLatch(TOTAL_NUMBER_OF_BUYERS/2) to 0, other BETA threads can resume
                 */
                countDownLatch.countDown();
            } finally {
                System.out.println("\t\t"+this.getName() + " Latch Count: "+ countDownLatch.getCount());
            }
        }else{
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            person.lock();
            try{
                basketOfChips = basketOfChips * 2;
                System.out.println(""+this.getName() + " doubled bags at time: "+new Date());
            }finally {
                person.unlock();
            }

        }
    }

    public static void main(String[] args) throws InterruptedException {
        d_Buyers_CountDownLatch[] buyers = new d_Buyers_CountDownLatch[TOTAL_NUMBER_OF_BUYERS];
        for (int i = 0; i < buyers.length/2; i++) {
            buyers[2*i] = new d_Buyers_CountDownLatch("APLHA-"+i);
            buyers[2*i+1] = new d_Buyers_CountDownLatch("BETA-"+(i+1));
        }

        for (d_Buyers_CountDownLatch buyer: buyers) {
            buyer.start();
        }

        for (d_Buyers_CountDownLatch buyer: buyers) {
            buyer.join();
        }

        System.out.println("We need to buy: "+ d_Buyers_CountDownLatch.basketOfChips+ " bags of chips");
    }

    public d_Buyers_CountDownLatch(String name){
        this.setName(name);
    }

}
