package f_Priority;

//Demonstrate thread priorities.
class clicker implements Runnable {
	int click = 0;
	Thread t;
	private volatile boolean running1 = true;
	public clicker(int p) {
		t = new Thread(this);
		t.setPriority(p);
	}
	public void run() {
		while (running1) {
			click++;
		}
	}
	public void stop() {
		running1 = false;
	}
	public void start() {
		t.start();
	}
}
