package f_Priority;

public class MainClass {
	public static void main(String[] args) {
		CounterThread thread1 = new CounterThread("thread1");
		thread1.setPriority(10);
		CounterThread thread2 = new CounterThread("thread2");
		thread2.setPriority(1);
		thread2.start();
		thread1.start();
	}


}
