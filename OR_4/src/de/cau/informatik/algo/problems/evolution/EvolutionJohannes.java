package de.cau.informatik.algo.problems.evolution;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * NOTE:
 * Parts of this code were created in cooperation with Aaron Schaper & Yorck Zisgen. Especially their approach to
 * use the {@link Random} class for the different types of randomization. They also helped me understanding and
 * breaking the algorithm into bits.
 */
class EvolutionJohannes {

    /**
     * The initial mutability
     */
    private double mutability;

    /**
     * The number of children to be produced in a new generation
     */
    private int lambda;

    /**
     * The size of a population of one generation
     */
    private int mu;

    /**
     * The objective function to use the evolution algorithm on
     */
    private ObjFunction function;

    /**
     * List holding each {@link PopulationElement} of a generation. This can include ancestors and children at certain points.
     */
    private List<PopulationElement> population = new ArrayList();

    /**
     * Generator to get uniformly and (log-)normal distribution of random numbers.
     */
    private Random random;

    /**
     * {@link EvolutionJohannes#EvolutionJohannes(ObjFunction, double, int, int, long)} with fixed parameters
     */
    public EvolutionJohannes(ObjFunction function, long seed) {
        this(function,
                function.getArity() * 10,
                function.getArity() * 3000,
                function.getArity() * 450, seed);
    }

    /**
     * Create an Evolution object to perform the evolutionary algorithm.
     *
     * @param function   The objective function to minimize
     * @param mutability The initial mutability
     * @param lambda     The number of children to be produced
     * @param mu         The size of the population
     * @param seed       The seed to the random number generator
     */
    public EvolutionJohannes(ObjFunction function, double mutability, int lambda, int mu, long seed) {
        this.function = function;
        this.mutability = mutability;
        this.lambda = lambda;
        this.mu = mu;

        // Uses the given seed to initialize a new random number generator.
        this.random = new Random(seed);
    }

    /**
     * Repeat the evolutionary algorithm for a certain number of steps
     *
     * @param steps The number of iterations performed
     * @return The best value discovered during the algorithm
     */
    public double[] compute(int steps) {
        addToPopulation(mu, this::initialAncestor);

        IntStream.range(0, steps)
                .forEach(step -> {
                    addToPopulation(lambda, this::newChild);
                    sortAndLimitPopulation();
                });

        return getParameterOfFirstElementOfPopulation();
    }

    /**
     * Executes a given number of times the passed method and adds the result to {@link #population}
     *
     * @param numberOfIterations the number of times to repeat action
     * @param supplier           the method to be executed
     */
    private void addToPopulation(int numberOfIterations, Supplier<PopulationElement> supplier) {
        IntStream.range(0, numberOfIterations)
                .mapToObj(iteration -> supplier.get())
                .forEach(population::add);
    }

    /**
     * Creates a {@link PopulationElement} with randomly selected parameters for each dimension and the initially set mutability.
     * The dimensions and the range for the parameters are given by the function.
     *
     * @return the created {@link PopulationElement}
     */
    private PopulationElement initialAncestor() {
        double[] parameters = new double[function.getArity()];

        IntStream.range(0, function.getArity()).forEach(i -> parameters[i] = getRandomDouble(function.getRange(i)));

        return new PopulationElement(parameters, mutability);
    }

    /**
     * Creates a {@link PopulationElement} that uses a randomly selected parent to generate new parameters for each dimension and a new mutability.
     * The dimensions and the range for the parameters are given by the function.
     *
     * @return the created {@link PopulationElement}
     */
    private PopulationElement newChild() {
        PopulationElement randomParent = population.get(getRandomIndexForPopulation()); // Applicable since population not sorted
        double[] randomZ = getRandomZ();

        double childMutability = randomParent.getMutability() * getRandomXi();
        double[] childParameters = new double[function.getArity()];

        IntStream.range(0, function.getArity()).forEach(i -> {
            double childParameter = randomParent.getParameter()[i] + childMutability * randomZ[i];
            childParameters[i] = verifyParameter(function.getRange(i), childParameter);
        });

        return new PopulationElement(childParameters, childMutability);
    }

    /**
     * Verifies that a chosen parameter parameter lies within a given range. If not it will return the applicable value of the boundary.
     *
     * @param range     the two-dimensional array with range boundaries for parameters
     * @param parameter the parameter to verify
     * @return the given parameter or the next boundary
     */
    private double verifyParameter(double[] range, double parameter) {
        double minRange = range[0];
        double maxRange = range[1];

        if (parameter <= minRange) {
            return minRange;
        } else if (maxRange <= parameter) {
            return maxRange;
        } else {
            return parameter;
        }
    }

    /**
     * Sorts the {@link #population} and limits its size to {@link #mu} elements with the lowest {@link PopulationElement#functionValue}
     */
    private void sortAndLimitPopulation() {
        population = population.stream()
                .sorted(Comparator.comparing(PopulationElement::getFunctionValue))
                .limit(mu)
                .collect(Collectors.toList());
    }

    /**
     * @return the parameters of the first element in the population
     */
    private double[] getParameterOfFirstElementOfPopulation() {
        return population.get(0).getParameter();
    }

    /**
     * @param range the two-dimensional array with range boundaries for parameters
     * @return a new uniformly distributed random number within the range
     */
    private double getRandomDouble(double[] range) {
        double minRange = range[0];
        double maxRange = range[1];
        return (minRange + (maxRange - minRange) * random.nextDouble());
    }

    /**
     * @return a new uniformly distributed index for {@link #population}
     */
    private int getRandomIndexForPopulation() {
        return random.nextInt(mu);
    }

    /**
     * @return a new z vector with normally distributed random numbers between [-1, 1]
     */
    private double[] getRandomZ() {
        double[] z = new double[function.getArity()];

        IntStream.range(0, function.getArity()).forEach(dimension ->
                z[dimension] = getRandomNumberInRange(() -> random.nextGaussian(), -1, 1));

        return z;
    }

    /**
     * @return a new log-normally distributed random number between [0, 2]
     */
    private double getRandomXi() {
        return getRandomNumberInRange(() -> Math.exp(random.nextGaussian()), 0, 2);
    }

    /**
     * Retrieves a result from supplier as long as it is not within the given range
     *
     * @param supplier the method to get a result from
     * @param minRange the minimal boundary of the range
     * @param maxRange the maximal boundary of the range
     * @return the result that is within the range
     */
    private double getRandomNumberInRange(Supplier<Double> supplier, int minRange, int maxRange) {
        double number = supplier.get();

        if (number < minRange || maxRange < number) {
            return getRandomNumberInRange(supplier, minRange, maxRange);
        }

        return number;
    }

    /**
     * Inner class to represent one individual within a population. It was decided that there is no need to have
     * setters for the variables as the values should not be changed after initially setting them.
     */
    private class PopulationElement {

        /**
         * The value that the {@link #function} returns for the given {@link #parameter}
         */
        private double functionValue;

        /**
         * The parameters for a point on the {@link #function}
         */
        private double[] parameter;

        /**
         * The mutability of this individual
         */
        private double mutability;

        /**
         * Only constructor for this element as both parameters are obligatory. This constructor will initialize
         * {@link #functionValue} by applying the {@link #function} to the given parameters and store it as well.
         *
         * @param parameter  the {@link #parameter} for the individual
         * @param mutability the {@link #mutability} for the individual
         */
        public PopulationElement(double[] parameter, double mutability) {
            this.parameter = parameter;
            this.mutability = mutability;
            this.functionValue = function.eval(parameter);
        }

        /**
         * @return {@link #functionValue}
         */
        public double getFunctionValue() {
            return functionValue;
        }

        /**
         * @return {@link #parameter}
         */
        public double[] getParameter() {
            return parameter;
        }

        /**
         * @return {@link #mutability}
         */
        public double getMutability() {
            return mutability;
        }
    }
}
