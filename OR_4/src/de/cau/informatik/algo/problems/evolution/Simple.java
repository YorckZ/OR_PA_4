package de.cau.informatik.algo.problems.evolution;

public class Simple implements ObjFunction{
    double[] a;
    int n;
    double[] r;

    public Simple(double[] a, double[] r){
        this.a = a;
        n = a.length;
        this.r = r;
    }

    @Override
    public double eval(double[] x){
        double t = 0.0;
        for(int i = 0; i < n; i++ ){
            t = t + a[i]*Math.pow(x[0],i);
        }
        return t;
    }

    @Override
    public int getArity(){
        return 1;
    }

    @Override
    public double[] getRange(int i){
        return r;
        
    }
}