package e_MultipleThread.UseJoinAndAlive;

class DemoJoin {
	public static void main(String args[]) {
		NewThread ob1 = new NewThread("One");
		NewThread ob2 = new NewThread("Two");
		NewThread ob3 = new NewThread("Three");
		System.out.println("Thread One is alive: " 				+ ob1.t.isAlive());
		System.out.println("Thread Two is alive: " 				+ ob2.t.isAlive());
		System.out.println("Thread Three is alive: " 				+ ob3.t.isAlive());
		
		try {
			System.out.println("Waiting for threads to finish.");
			// This method waits until the thread on which it is called terminates.
			ob1.t.join();
			System.out.println("Thread 1 terminated");
			ob2.t.join();	// Thread waits for thread 1 to suspend
			System.out.println("Thread 2 terminated");
			ob3.t.join();	// Thread waits for thread 2 to suspend
			System.out.println("Thread 3 terminated");
							// main is waiting for all the threads to suspend
		} catch (InterruptedException e) {
			System.out.println("Main thread Interrupted");
		}
		System.out.println("Thread One is alive: " 				+ ob1.t.isAlive());
		System.out.println("Thread Two is alive: " 				+ ob2.t.isAlive());
		System.out.println("Thread Three is alive: " 			+ ob3.t.isAlive());
		System.out.println("Main thread exiting."	);
	}
}