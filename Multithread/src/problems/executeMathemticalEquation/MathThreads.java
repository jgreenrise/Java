package problems.executeMathemticalEquation;

/**
 * p = sin (x) + cos (y) + tan (z)
 */
public class MathThreads {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		MathSin st = new MathSin(45);
		MathCos ct = new MathCos(60);
		MathTan tt = new MathTan(30);

		st.start();
		ct.start();
		tt.start();

		try {
			st.join();
			ct.join();
			tt.join();
			double z = st.res + ct.res + tt.res;
			System.out.println("Final output of  sin (x) + cos (y) + tan (z) "+z);
		} catch (InterruptedException exception) {

		}

	}
}
	