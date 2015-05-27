/**
 * 
 */

/**
 * @author j3 d patel
 *
 */
public class Scheduling_1 {

	public static class Thread1 extends Thread {
		public void run() {
			System.out.println("A");
			System.out.println("B");
		}
	}
	
	public static class Thread2 extends Thread {
		public void run() {
			System.out.println("1");
			System.out.println("2");
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Thread1().start();
		new Thread2().start();
	}
}
