package de.cau.informatik.algo.problems.evolution;

import static java.lang.Math.pow;

/**
 * Source: https://www.sfu.ca/~ssurjano/stybtang.html
 */
public class StyblinskiTang implements ObjFunction {

    /**
     * Number of Dimensions
     */
    int d;

    /**
     * @param d Number of Dimensions
     */
    public StyblinskiTang(int d) {
        this.d = d;
    }

    @Override
    public double eval(double[] x) {

        double t1 = 0;

        for (int i = 0; i < d; i++) {
            double xi = x[i];
            double t2 = pow(xi, 4);
            double t3 = 16 * pow(xi, 2);
            double t4 = 5 * xi;
            t1 = t1 + t2 - t3 + t4;
        }

        return t1 / 2;
    }

    /**
     * Dimensions: d
     */
    @Override
    public int getArity() {
        return d;
    }

    /**
     * The function is usually evaluated on the hypercube xi ∈ [-5, 5], for all i = 1, …, d.
     */
    @Override
    public double[] getRange(int i) {
        return new double[]{-5, 5};
    }
}