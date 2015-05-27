package b_ImplementsRunnable;

class MyRunnable implements Runnable{
	Thread t;
	
	MyRunnable() {
		t = new Thread(this, "Demo Thread");
		System.out.println("Child thread: " + t);
		t.start(); // Start the thread
	}
	
	//	This is the entry point for the second thread.
	public void run(){
		try{
			for(int i = 5; i > 0; i--){
				System.out.println("Child Thread: " + i);
				t.sleep(500);
			}
		} catch (InterruptedException e) {
			System.out.println("Child interrupted.");
		}
		System.out.println("Exiting child thread.");
	}
}