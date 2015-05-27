package problems.philosophersDinning;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Chopstick {

	private Lock csLock = new ReentrantLock();

	public Lock getCsLock() {
		return csLock;
	}

}
