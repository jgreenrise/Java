package d_MultipleThread;

public class MutliThreadDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new NewThread ("One");
		new NewThread ("Two");
		new NewThread ("Three");
		Thread t = Thread.currentThread();
		try {
			for(int i = 5; i > 0; i--)
			{				
				System.out.println("Thread :"+t.getName()+" Current value	"+i);
				//System.out.println("Main Thread: " + i);
				t.sleep(1000);
			}
			t.sleep(10000);
		} 
		catch (InterruptedException e){
			System.out.println("Main thread interrupted.");
		}
		System.out.println("Main thread exiting.");
		}

}
