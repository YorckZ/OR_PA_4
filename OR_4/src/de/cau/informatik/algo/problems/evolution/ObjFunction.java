package de.cau.informatik.algo.problems.evolution;

public interface ObjFunction{

    /**
     * Evaluate the function on the given point x
     * @param x the argument of the function
     * @return The evaluation f(x)
     */
    public double eval(double[] x);

    /**
     * Return the arity of the function, i.e. the length of the input.
     * More formally, if f: \mathbb{R}^n -> \mathbb{R}, this returns n.
     * @return The arity of f
     */
    public int getArity();

    /**
     * Return the range of dimension i.
     * More formally, if the feasible set is
     * [a_1,b_1] x [a_2,b_2] x ... x [a_n,b_n], this returns [a_i,b_i].
     * @param The dimension i
     * @return An array containing [a_i,b_i]
     */
    public double[] getRange(int i);


}
