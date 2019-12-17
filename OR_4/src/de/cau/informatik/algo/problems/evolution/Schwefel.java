package de.cau.informatik.algo.problems.evolution;

import static java.lang.Math.abs;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

/**
 * The Schwefel function is complex, with many local minima. The plot shows the two-dimensional form of the function.
 * <p>
 * Source: https://www.sfu.ca/~ssurjano/schwef.html
 */
public class Schwefel implements ObjFunction {

    /**
     * Number of Dimensions
     */
    int d;

    /**
     * @param d Number of Dimensions
     */
    public Schwefel(int d) {
        this.d = d;
    }

    @Override
    public double eval(double[] x) {

        double t1 = 0;

        for (int i = 0; i < d; i++) {
            double xi = x[i];
            t1 = t1 + xi * sin(sqrt(abs(xi)));
        }

        return 418.9829 * d + t1;
    }

    /**
     * Dimensions: d
     */
    @Override
    public int getArity() {
        return d;
    }

    /**
     * The function is usually evaluated on the hypercube xi ∈ [-500, 500], for all i = 1, …, d.
     */
    @Override
    public double[] getRange(int i) {
        return new double[]{-500, 500};
    }
}