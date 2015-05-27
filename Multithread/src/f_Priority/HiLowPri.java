package f_Priority;

class HiLowPri {
	public static void main(String args[]) {
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		clicker hi = new clicker(Thread.NORM_PRIORITY + 2);
		clicker lo = new clicker(Thread.NORM_PRIORITY - 2);
		lo.start();
		hi.start();
		Integer value = new Integer("1001");
		int a=10;
		int b= 25;

		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			System.out.println("Main thread interrupted.");
		}
		lo.stop();
		hi.stop();
		// Wait for child threads to terminate.
		try {
			hi.t.join();
			lo.t.join();
		} catch (InterruptedException e) {
			System.out.println("InterruptedException caught");
		}
		System.out.println("Low-priority thread:  " + lo.click);
		System.out.println("High-priority thread: " + hi.click);
	}
}
