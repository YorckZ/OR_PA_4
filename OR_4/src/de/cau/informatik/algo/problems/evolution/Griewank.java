package de.cau.informatik.algo.problems.evolution;

public class Griewank implements ObjFunction{
    int n;

    public Griewank(int n){
        this.n = n;
    }

    @Override
    public double eval(double[] x){
        double t1 = 0;
        for(int i = 0; i < n; i++){
            t1 = t1 + x[i]*x[i] / 4000;
        }

        double t2 = 1;
        for(int i = 0; i < n; i++){
            t2 = t2 * Math.cos(x[i]/Math.sqrt(i+1));
        }

        return t1-t2+1+42;
    }

    @Override
    public int getArity(){
        return n;
    }

    @Override
    public double[] getRange(int i){
        return new double[]{-200.0,300.0};
    }
}