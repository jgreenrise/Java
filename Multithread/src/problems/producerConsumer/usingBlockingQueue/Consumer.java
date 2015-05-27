package problems.producerConsumer.usingBlockingQueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Consumer implements Runnable {

	BlockingQueue sharedQueue;

	public Consumer(BlockingQueue sharedQ) {
		sharedQueue = sharedQ;
		(new Thread(this, "Consumer")).start();
	}

	@Override
	public void run() {
		while (true)
			try {
				System.out.println("Consumed: " + sharedQueue.take());
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}

	}

}
