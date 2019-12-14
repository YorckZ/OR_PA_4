package de.cau.informatik.algo.problems.evolution;

public class OwnTestYorck {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	       ObjFunction f = new Simple(new double[] {2.0, 3.0}, new double[]{-100.0,200.0});
	       
	       double[] in = new double[] {1.0};
	       f.eval(in);
	       
	       
//	        test(f,f.getRange(0)[0], 3000);
	}

}
