package de.cau.informatik.algo.problems.evolution;


class Evolution{
	private double mutability;
	private int lambda;
	private int mu;
	private ObjFunction ob;
	private long seed;
	// array der mutabilit�ten
	
	
    /**
     * Create an Evolution object to perform the evolutionary algorithm.
     *
     * @param f The objective function to minimize
     * @param mutability The initial mutability
     * @param lambda The number of children to be produced
     * @param mu The size of the population
     * @param seed The seed to the random number generator
     */
    public Evolution(ObjFunction f, long seed){
    	this.setOb(f); // wird dem Konstruktor �bergeben
    	this.setSeed(seed); // wird dem Konstruktor �bergeben
    	
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
    	// struktur f�r startpolutaion deklarieren
    	// get arity = this.getOb().getArity().
    	
    	// schleife �ber mu
    		// create_individual (arity) -> eigene methode
    		// diese der leeren population hinzuf�gen => startpopulation fertig
    	// ende schleife
    	
    	
    	this.setInitialMutabilities();
    	
    	
    	for (int i=0; i<steps; i++) {
    		// kopiere startpopulation als Q
    		// kopiere array der mutabilit�ten als M
    		// schleife �ber lambda:
    			// w�hle zuf�lliges individuum aus mu gleichverteilt
    			// berechne zufallsvektor normalverteilt mit l�nge arity
    			// berechne zufallszahl z log-normal-verteilt aus [0,2]
    			// mutiere damit die mutabilit�t des zuf�llig gew�hlten Elters [i] multiplikativ mit z
    			// addiere mutierte mutabilit�t mal z zum elter => speichere als nachkomme
    			// f�ge nachkomme zur population Q hinzu
    			// f�ge mutabilit�t des nachkommens zu M hinzu
    		// ende schleife �ber lambda
    		
    		
    		
    		
    		
    		
    		
    		
    		
    		
    	}
    	
    	return getReturnIndividual();
		//return null;
    }
    


	private double[] create_individual (int arity) {
    	// l�nge des returnarrays = arity
    	// for schleife 0 bis arity-1
    		// jede komponente random innerhalb this.getOb().getRange() der entsprechenden Komponente;
    		// returnarray[i] setzen
    	// ende schleife
    	return null;
    }
    
    private double getRandomDouble (double[] limits) {
    	// lese limits[0] als untergrenze und limits[1] als obergrenze
    	// erzeuge random double zwischen den grenzen
    	return 0.0;
    }
    
    private void setInitialMutabilities() {
		// setze mutabilit�ten initial (diese sollen sich sp�ter �ndern k�nnen)	
	}
    
    private double[] getReturnIndividual() {
    	// nimm die aktuelle population
    	// iteriere dr�ber und berechne funktionswerte
    	// w�hle das individuum mit dem kleinsten funktionswert
    	// returne das individuum
    	return new double[] {0};
    }
    
}