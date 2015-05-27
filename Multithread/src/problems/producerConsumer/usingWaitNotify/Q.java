package problems.producerConsumer.usingWaitNotify;

public class Q {

	int n;

	private boolean lock = false;

	public synchronized void put(int n) {
		if (lock)
			try {
				wait();
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		
		this.n = n;
		System.out.println("Put: " + n);
		lock = true;
		notify();
	}

	public synchronized int get() {
		if (!lock)
			try {
				wait();
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}

		lock = false;
		System.out.println("Got: " + n);
		notify();
		return n;

	}

}
