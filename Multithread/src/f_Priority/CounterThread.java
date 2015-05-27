package f_Priority;

public class CounterThread  extends Thread {
	String name;
	public CounterThread(String name) {
		super();
		this.name = name;

	}

	public void run() {
		int count = 0;
		while (true) {
			try {
				sleep(1000);
			} catch (InterruptedException e) {
			}
			if (count == 500)
				count = 0;
			System.out.println(name+":" + count++);
		}
	}
}
