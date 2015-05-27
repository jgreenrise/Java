package j_ProducerConsumer_Correct;

class PC {
	public static void main(String args[]) {
		Q q = new Q();
		new Producer(q);
		new Consumer(q);
		System.out.println("Press Control-Cc to stop.");
	}
}
