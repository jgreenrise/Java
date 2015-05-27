package problems.philosophersDinningSynchronised;

import java.util.concurrent.TimeUnit;

public class Philosopher implements Runnable {

	private int n;
	private ChopStick cs[];
	private State state;

	public Philosopher(int n, ChopStick cs[]) {
		this.n = n;
		this.cs = cs;
		this.state = State.THINKING;
	}

	@Override
	public void run() {

		ChopStick left = cs[n % 5];
		ChopStick right = cs[(n - 1) % 5];

		while (true) {

			state = State.HUNGRY;
			System.out.printf("\nPhilosopher %d\tHUNGRY\t\t%d\t%d", n, n % 5,
					(n - 1) % 5);

			synchronized (left) {
				if (left.grab()) {
					synchronized (right) {
						if (right.grab()) {
							state = State.EATING;
							System.out.printf(
									"\n\nPhilosopher %d\tGOTCH\t\t%d\t%d", n,
									n % 5, (n - 1) % 5);

						} else {
							left.release();
						}
					}
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
				left.release();
				right.release();
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
