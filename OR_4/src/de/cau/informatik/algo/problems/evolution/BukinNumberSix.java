package de.cau.informatik.algo.problems.evolution;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * The sixth Bukin function has many local minima, all of which lie in a ridge
 * <p>
 * Source: https://www.sfu.ca/~ssurjano/bukin6.html
 */
public class BukinNumberSix implements ObjFunction {

    public BukinNumberSix() {
    }

    @Override
    public double eval(double[] x) {

        double x1 = x[0];
        double x2 = x[1];

        double t1 = 100 * sqrt(abs(x2 - 0.01 * pow(x1, 2)));
        double t2 = 0.01 * abs(x1 + 10);

        return t1 + t2;
    }

    /**
     * Dimensions: 2
     */
    @Override
    public int getArity() {
        return 2;
    }

    /**
     * The function is usually evaluated on the rectangle x1 ∈ [-15, -5], x2 ∈ [-3, 3].
     */
    @Override
    public double[] getRange(int i) {
        if (i == 0) {
            return new double[]{-15, -5};
        } else {
            return new double[]{-3, 3};
        }
    }
}