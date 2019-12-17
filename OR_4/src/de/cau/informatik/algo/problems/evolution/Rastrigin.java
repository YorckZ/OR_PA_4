package de.cau.informatik.algo.problems.evolution;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.pow;

/**
 * The Rastrigin function has several local minima. It is highly multimodal, but locations of the minima are regularly distributed.
 * <p>
 * Source: https://www.sfu.ca/~ssurjano/rastr.html
 */
public class Rastrigin implements ObjFunction {

    /**
     * Number of Dimensions
     */
    int d;

    /**
     * @param d Number of Dimensions
     */
    public Rastrigin(int d) {
        this.d = d;
    }

    @Override
    public double eval(double[] x) {

        double t1 = 0;

        for (int i = 0; i < d; i++) {
            double xi = x[i];
            double t2 = pow(xi, 2);
            double t3 = 10 * cos(2 * PI * xi);
            t1 = t1 + (t2 - t3);
        }

        return 10 * d + t1;
    }

    /**
     * Dimensions: d
     */
    @Override
    public int getArity() {
        return d;
    }

    /**
     * The function is usually evaluated on the hypercube xi ∈ [-5.12, 5.12], for all i = 1, …, d.
     */
    @Override
    public double[] getRange(int i) {
        return new double[]{-5.12, 5.12};
    }
}