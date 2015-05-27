package problems.producerConsumer.usingWaitNotify;

public class Client {

	public static void main(String args[]) {

		Q q = new Q();

		Producer p = new Producer(q);
		Consumer c = new Consumer(q);
	}

}
