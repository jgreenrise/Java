package problems.producerConsumer.usingBlockingQueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Client {

	public static void main(String args[]) {

		BlockingQueue sharedQ = new LinkedBlockingQueue();

		Producer p = new Producer(sharedQ);
		Consumer c = new Consumer(sharedQ);
	}

}
