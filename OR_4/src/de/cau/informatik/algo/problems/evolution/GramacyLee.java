package de.cau.informatik.algo.problems.evolution;

import static java.lang.Math.PI;
import static java.lang.Math.pow;
import static java.lang.Math.sin;

/**
 * This is a simple one-dimensional test function.
 * <p>
 * Source: https://www.sfu.ca/~ssurjano/grlee12.html
 */
public class GramacyLee implements ObjFunction {

    public GramacyLee() {
    }

    @Override
    public double eval(double[] x) {

        double x1 = x[0];

        double t1 = sin(10 * PI * x1) / (2 * x1);
        double t2 = pow(x1 - 1, 4);

        return t1 + t2;
    }

    /**
     * Dimensions: 1
     */
    @Override
    public int getArity() {
        return 1;
    }

    /**
     * This function is usually evaluated on the x ∈ [0.5, 2.5].
     */
    @Override
    public double[] getRange(int i) {
        return new double[]{0.5, 2.5};
    }
}