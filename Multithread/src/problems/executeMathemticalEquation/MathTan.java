package problems.executeMathemticalEquation;

public class MathTan extends Thread {
	public double deg;
	public double res;

	public MathTan(int degree) {
		deg = degree;
	}

	public void run() {
		System.out.println("Executing sin of " + deg);
		double Deg2Rad = Math.toRadians(deg);
		res = Math.tan(Deg2Rad);
		System.out.println("Exit from MathSin. Res = " + res);
		
		try {
			this.sleep(2500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
