package problems.producerConsumer.usingBlockingQueue;

import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {

	BlockingQueue sharedQueue;

	public Producer(BlockingQueue sharedQ) {
		sharedQueue = sharedQ;
		(new Thread(this, "Producer")).start();
	}

	@Override
	public void run() {
		// for (int i = 0; i < 10; i++) {
		int i = 0;
		while (true)
			try {
				System.out.println("Produced: " + i++);
				sharedQueue.put(i);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}

	}
}
