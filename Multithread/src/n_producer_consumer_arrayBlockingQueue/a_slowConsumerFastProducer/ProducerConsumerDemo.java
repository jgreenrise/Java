package n_producer_consumer_arrayBlockingQueue.a_slowConsumerFastProducer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumerDemo {

    public static void main(String[] args){

        BlockingQueue<String> queue = new ArrayBlockingQueue(5);
        new Consumer(queue).start();
        new Producer(queue).start();

    }

    private static class Producer extends Thread {
        BlockingQueue<String> queue;
        public Producer(BlockingQueue<String> queue) { this.queue = queue; }

        public void run(){
            for (int i = 0; i < 20; i++) {
                try{
                    queue.add("Bowl: "+i);
                    System.out.format("Served Bowl: %d - remaining capacity: %d\n", i, queue.remainingCapacity());
                    Thread.sleep(200);
                }catch (Exception exception){
                    exception.printStackTrace();
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
                    String bowl = (String) queue.take();
                    System.out.format("Ate bowl: %s\n",bowl);
                    Thread.sleep(300);
                }catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        }
    }
}
