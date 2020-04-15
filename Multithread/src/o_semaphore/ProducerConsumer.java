package o_semaphore;

import n_producer_consumer_arrayBlockingQueue.a_slowConsumerFastProducer.ProducerConsumerDemo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class ProducerConsumer  {

    private static final int BUFFER_SIZE = 3;
    private static Semaphore fillCount = new Semaphore(0);
    private static Semaphore emptyCount = new Semaphore(BUFFER_SIZE);

    private static class Producer extends Thread {
        BlockingQueue<String> queue;
        public Producer(BlockingQueue<String> queue) { this.queue = queue; }

        public void run(){
            for (int i = 0; i < 5; i++) {
                try{
                    emptyCount.acquire();
                    queue.add("Bowl: "+i);
                    System.out.format("Served Bowl: %d \t\t Remaining capacity: %d \t Remaining permits: %d\n", i, queue.remainingCapacity(), emptyCount.availablePermits());
                    //Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 2000));
                    Thread.sleep(1000);
                }catch (Exception exception){
                    exception.printStackTrace();
                }finally {
                    fillCount.release();
                }
            }
        }
    }

    private static class Consumer extends Thread {
        BlockingQueue<String> queue;
        public Consumer(BlockingQueue<String> queue) { this.queue = queue; }

        public void run(){
            while(true){
                try{
                    fillCount.acquire();
                    String bowl = queue.take();
                    System.out.format("Ate Bowl: %s \t Remaining capacity: %d \t Remaining permits: %d\n", bowl, queue.remainingCapacity(), fillCount.availablePermits());
                    Thread.sleep(4000);
                }catch (Exception exception){
                    exception.printStackTrace();
                }finally {
                    emptyCount.release();
                }
            }
        }
    }

    public static void main(String [] args){

        BlockingQueue<String> queue = new ArrayBlockingQueue(BUFFER_SIZE);
        new Consumer(queue).start();
        new Producer(queue).start();

    }
}
