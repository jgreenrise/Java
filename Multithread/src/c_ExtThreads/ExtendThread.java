package c_ExtThreads;

class ExtendThread {
	public static void main(String args[]) {
		new NewThread(); // create a new thread
		try {
			for(int i = 5; i > 0; i--) 
			{
				System.out.println("Main Thread: " + i);
				Thread.sleep(50000);
			}
		} 
		catch (InterruptedException e){
			System.out.println("Main thread interrupted.");
		}
		System.out.println("Main thread exiting.");
	}
}