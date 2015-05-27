package problems.philosophersDinning;

public class ChopStickSynchronizedMethod {

	private boolean state_chopstick = false;

	public synchronized boolean grab() {

		if (state_chopstick) {
			return false;
		}

		state_chopstick = true;
		return true;

	}

	public synchronized void release() {
		state_chopstick = false;
	}
}
