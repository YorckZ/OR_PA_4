package de.cau.informatik.algo.problems.evolution;


class Evolution_Aaron{
	private double mutability;
	private int lambda;
	private int mu;
	private ObjFunction ob;
	private long seed;

	
    /**
     * Create an Evolution object to perform the evolutionary algorithm.
     *
     * @param f The objective function to minimize
     * @param mutability The initial mutability
     * @param lambda The number of children to be produced
     * @param mu The size of the population
     * @param seed The seed to the random number generator
     */
    public Evolution_Aaron(ObjFunction f, long seed){
    	this.setOb(f); // wird dem Konstruktor übergeben
    	this.setSeed(seed); // wird dem Konstruktor übergeben
    	
    	// Selbst festgelegte Parameter (eigener Ermessensspielraum)
    	this.setMutability(1.0);
    	this.setLambda(50);
    	this.setMu(50);
    }
    
    public void setMutability(double m) {
    	this.mutability = m;
    }
    
    public void setLambda(int l) {
    	this.lambda = l;
    }
    
    public void setMu(int m) {
    	this.mu = m;
    }
    
    public void setOb(ObjFunction f) {
    	this.ob = f;
    }
    
    public void setSeed (long s) {
    	this.seed = s;
    }
    
    public double getMutability(){
    	return this.mutability;
    }
    
    public int getLambda() {
    	return this.lambda;
    }
    
    public int getMu() {
    	return this.mu;
    }

    public ObjFunction getOb() {
    	return this.ob;
    }
    
    public long getSeed() {
    	return this.seed;
    }
    
    /**
     * Repeat the evolutionary algorithm for a certain number of steps
     *
     * @param steps The number of iterations performed
     * @return The best value discovered during the algorithm
     */
    public double[] compute(int steps){
		return null;
    }
}