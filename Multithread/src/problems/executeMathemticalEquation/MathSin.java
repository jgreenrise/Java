package problems.executeMathemticalEquation;

public class MathSin extends Thread {
	public double deg;
	public double res;

	public MathSin(int degree) {
		deg = degree;
	}

	public void run() {
		System.out.println("Executing sin of " + deg);
		double Deg2Rad = Math.toRadians(deg);
		res = Math.sin(Deg2Rad);
		System.out.println("Exit from MathSin. Res = " + res);
		
		try {
			this.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
