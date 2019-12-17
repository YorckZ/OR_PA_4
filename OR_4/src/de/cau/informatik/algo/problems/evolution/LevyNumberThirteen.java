package de.cau.informatik.algo.problems.evolution;

import static java.lang.Math.PI;
import static java.lang.Math.pow;
import static java.lang.Math.sin;

/**
 * Source: https://www.sfu.ca/~ssurjano/levy13.html
 */
public class LevyNumberThirteen implements ObjFunction {

    public LevyNumberThirteen() {
    }

    @Override
    public double eval(double[] x) {

        double x1 = x[0];
        double x2 = x[1];

        double t1 = pow(sin(3 * PI * x1), 2);
        double t2 = pow(x1 - 1, 2) * pow(1 + sin(3 * PI * x2), 2);
        double t3 = pow(x2 - 1, 2) * pow(1 + sin(2 * PI * x2), 2);

        return t1 + t2 + t3;
    }

    /**
     * Dimensions: 2
     */
    @Override
    public int getArity() {
        return 2;
    }

    /**
     * The function is usually evaluated on the square xi ∈ [-10, 10], for all i = 1, 2.
     */
    @Override
    public double[] getRange(int i) {
        return new double[]{-10, 10};
    }
}