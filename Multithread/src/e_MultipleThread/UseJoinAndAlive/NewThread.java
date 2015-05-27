package e_MultipleThread.UseJoinAndAlive;
public class NewThread implements Runnable 
{
	String name;
	Thread t;	
	NewThread (String name)
	{
		this.name = name;
		t = new Thread(this, name);
		System.out.println("New thread         :"+t.getName());
		t.start();
	}
	
	public void run() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 5; i++) {
				System.out.println("Thread 			:"+t.getName()+"    Currently holding value"+i);
			try {
				t.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Exiting Thread        :"+t);
		
	}

}
