package de.cau.informatik.algo.problems.evolution;

import org.junit.jupiter.api.Test;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EvolutionTest {

	@Test
    public void instance0() {
        ObjFunction f = new Simple(new double[] {2.0, 3.0}, new double[]{-100.0,200.0});
        test(f,f.getRange(0)[0], 3000);
    }

    @Test
    public void instance1() {
        ObjFunction f = new Simple(new double[] {4.0, -2.0}, new double[]{-100.0,200.0});
        test(f,f.getRange(0)[1], 3000);
    }

    @Test
    public void instance2() {
        ObjFunction f = new Simple(new double[] {-1.0,-6.0,5.0,5.0,-5.0,1.0}, new double[]{-1.0,3.0});
        test(f,-4.631432208448841, 5000);
    }

    @Test
    public void instance3() {
        ObjFunction f = new Rosenbrock();
        test(f,0, 4000);
    }
    
    @Test
    public void instance4() {
        ObjFunction f = new Griewank(3);
        test(f, 42, 5000, 0.05);
    }

    @Test
    public void instance5() {
        ObjFunction f = new Griewank(5);
        test(f, 42, 8000, 0.05);
    }

    /**
     * Global Minimum at f(3, 12, 3, 0) = -150
     */
    @Test // selbst geschriebener Testfall
    public void myOwnRosenbrock() {
        ObjFunction f = new Rosenbrock2();
        test(f, -150, 4000);
    }
   
    /**
     * Global Minimum at f(0.548563444114526) = -0.869011134989500
     */
    @Test
    public void myInstance00_GramacyLee() {
        ObjFunction f = new GramacyLee();
        test(f, -0.869011134989500, 8000, 0.001);
    }

    /**
     * Global Minimum at f(-10, 1) = 0
     */
    @Test
    public void myInstance01_BukinNumberSix() {
        ObjFunction f = new BukinNumberSix();
        test(f, 0, 8000, 0.001);
    }

    /**
     * Global Minimum at f(1, 1) = 0
     */
    @Test
    public void myInstance02_LevyNumberThirteen() {
        ObjFunction f = new LevyNumberThirteen();
        test(f, 0, 8000, 0.001);
    }

    /**
     * Global Minimum at f(0, 0) = 0
     */
    @Test
    public void myInstance1_Rastrigin_d2() {
        ObjFunction f = new Rastrigin(2);
        test(f, 0, 8000, 0.001);
    }

    /**
     * Global Minimum at f(0, ..., 0) = 0
     */
    @Test
    public void myInstance2_Rastrigin_d3() {
        ObjFunction f = new Rastrigin(3);
        test(f, 0, 8000, 0.001);
    }

    /**
     * Global Minimum at f(420.9687, 420.9687) = 0
     */
    @Test
    public void myInstance4_Schwefel_d2() {
        ObjFunction f = new Rastrigin(2);
        test(f, 0, 8000, 0.001);
    }

    /**
     * Global Minimum at f(420.9687, ..., 420.9687) = 0
     */
    @Test
    public void myInstance5_Schwefel_d3() {
        ObjFunction f = new Rastrigin(3);
        test(f, 0, 8000, 0.001);
    }

    /**
     * Global Minimum at f(-2.903534, ..., -2.903534) = -39.16599 * 2
     */
    @Test
    public void myInstance7_StyblinskiTang_d2() {
        int d = 2;
        ObjFunction f = new StyblinskiTang(d);
        double opt = -39.16599 * d;
        test(f, opt, 8000, 0.001);
    }

    /**
     * Global Minimum at f(-2.903534, ..., ..., -2.903534) = -39.16599 * 3
     */
    @Test
    public void myInstance8_StyblinskiTang_d3() {
        int d = 3;
        ObjFunction f = new StyblinskiTang(d);
        double opt = -39.16599 * d;
        test(f, opt, 8000, 0.001);
    }

    /**
     * Global Minimum at f(420.9687, ..., ..., 420.9687) = -39.16599 * 4
     */
    @Test
    public void myInstance9_StyblinskiTang_d4() {
        int d = 4;
        ObjFunction f = new StyblinskiTang(d);
        double opt = -39.16599 * d;
        test(f, opt, 8000, 0.001);
    }

    /**
     * Testet mit fest gesetztem timeout
     */
    public void test(ObjFunction f, double opt) {
        test(f, opt, 5000);
    }

    public void test(ObjFunction f, double opt, int timeout) {
        test(f, opt, timeout, 0.001);
    }

    /**
     * Testet Kram
     */
    public void test(ObjFunction f, double opt, int timeout, double prec) {
        assertTimeoutPreemptively(ofMillis(timeout), () -> {
            int n = f.getArity();
            double eps = 0.0000001;

            Evolution E = new Evolution(f, 1l);
            double[] x = E.compute(200);

            assertEquals(n, x.length, "Output has wrong dimension.");

            for (int i = 0; i < n; i++) {
                double[] r = f.getRange(i);
                assertTrue(x[i] >= r[0] - eps, "Your solution is out of range. In Dimension " + i + " you have value " + x[i] + ", but the range is [" + r[0] + "," + r[1] + "]");
                assertTrue(x[i] <= r[1] + eps, "Your solution is out of range. In Dimension " + i + " you have value " + x[i] + ", but the range is [" + r[0] + "," + r[1] + "]");
            }

            assertTrue(f.eval(x) <= opt + prec, "Your solution is not within " + prec + " of the optimum. Try harder!");


        }, () -> "Timeout exceeded!");
    }
}