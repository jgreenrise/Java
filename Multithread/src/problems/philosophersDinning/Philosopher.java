package problems.philosophersDinning;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class Philosopher implements Runnable {

	private int n;
	private Chopstick cs[];
	private State state;

	public Philosopher(int n, Chopstick cs[]) {
		this.n = n;
		this.cs = cs;
		this.state = State.THINKING;
	}

	@Override
	public void run() {

		Lock lock1 = cs[n % 5].getCsLock();
		Lock lock2 = cs[(n - 1) % 5].getCsLock();

		while (true) {

			state = State.HUNGRY;
			System.out.printf("\nPhilosopher %d\tHUNGRY\t\t%d\t%d", n, n % 5,
					(n - 1) % 5);

			if (lock1.tryLock()) {
				if (lock2.tryLock()) {
					state = State.EATING;
					System.out.printf("\n\nPhilosopher %d\tGOTCH\t\t%d\t%d", n, n % 5,
							(n - 1) % 5);

				} else {
					lock1.unlock();
				}
			}

			if (state == State.HUNGRY) {
				try {
					System.out.printf("\nPhilosopher %d\tWAITG\t\t%d\t%d", n,
							n % 5, (n - 1) % 5);
					TimeUnit.SECONDS.sleep((long) (Math.random() * 10));
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}

			if (state == State.EATING) {
				eat();
				lock1.unlock();
				lock2.unlock();
				state = State.THINKING;
				think();
			}

		}
	}

	private void think() {

		try {
			System.out.printf("\nPhilosopher %d\tTHINK\t\t%d\t%d", n, n % 5,
					(n - 1) % 5);
			TimeUnit.SECONDS.sleep((long) (Math.random() * 10));
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}

	}

	private void eat() {
		try {
			System.out.printf("\nPhilosopher %d\tEATNG\t\t%d\t%d", n, n % 5,
					(n - 1) % 5);
			TimeUnit.SECONDS.sleep((long) (Math.random() * 10));
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}

	}
}
