package de.cau.informatik.algo.problems.evolution;

public class Rosenbrock implements ObjFunction{

    public Rosenbrock(){
    }

    @Override
    public double eval(double[] x){
        double t1 = x[1]-x[0]*x[0];
        double t2 = (1-x[0]);
        return 100*t1*t1+t2*t2;
    }

    @Override
    public int getArity(){
        return 2;
    }

    @Override
    public double[] getRange(int i){
        if (i == 0){
            return new double[]{-2.0,2.0};
        }
        else{
            return new double[]{-1.0,3.0};
        }
    }
}