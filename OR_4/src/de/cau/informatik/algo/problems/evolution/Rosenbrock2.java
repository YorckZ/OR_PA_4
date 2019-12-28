package de.cau.informatik.algo.problems.evolution;

public class Rosenbrock2 implements ObjFunction{

    public Rosenbrock2(){
    }

    @Override
    public double eval(double[] x){
//    	f(x1, x2, x3, x4) = 150 * (x1-x3) + 5x4^2 + (3-x3)^4 + (x2-4x1)^4 - 150
//    	f(3, 12, 3, 0) = -150 // globales Minimum
    	double t1 = 150 * (x[0]-x[2]);
    	double t2 = Math.pow(x[3],2) * 5;
		double t3 = Math.pow(3-x[2], 4); 
    	double t4 = Math.pow(x[1]- 4*x[0], 4); 
    	return t1 + t2 + t3 + t4 - 150;
    }

    @Override
    public int getArity(){
        return 4;
    }

    @Override
    public double[] getRange(int i){
            return new double[]{-100, 100};
    }
}