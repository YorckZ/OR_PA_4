package de.cau.informatik.algo.problems.evolution;


class Evolution{
	private double mutability;
	private int lambda;
	private int mu;
	private ObjFunction ob;
	private long seed;
	// array der mutabilitäten mu
	// array der mutabilitäten lambda
	private double[] P;
	private double[] Q; // entspriche Skript P Dach
	
	
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
    public double[] compute(int steps){ // Aaron
    	// struktur für startpolutaion deklarieren
    	// get arity = this.getOb().getArity().
    	
    	// schleife über mu
    		// create_ancestor(arity) -> eigene methode
    		// diese der leeren population hinzufügen => startpopulation fertig
    	// ende schleife
    	
    	
    	this.setInitialMutabilities();
    	
    	
    	for (int i=0; i<steps; i++) { // Yorck

    		// Schritt 0:
    		// kopiere startpopulation als Q
    		// kopiere array der mutabilitäten als M

    		// Schritt 1:
    		// schleife über lambda:
    			// create_successor();
    		// ende schleife über lambda
    		
    		
    		// Problem: wir müssen wissen, an welchen Positionen in Q unsere neuen P stehen.

    		
    		// Schritt 2:
    		// Bilde Array der Funktionswerte in der Reihenfolge von Q
    		// sortiere Q in Reihe ihrer Funktionswerte aufsteigend
    		// sortiere f(x)[] entsprechend
    		// übernimm die ersten mu Individuen nach P
    		
    		// Schritt 3:
    		// übernimm die mutabilitäten der auswahl aus Q, die es nach P schafft
    	}
    	
    	return getReturnIndividual();
		//return null;
    }
    
    private void create_successor() { // Yorck
		// wähle zufälliges individuum aus mu gleichverteilt
		// berechne zufallsvektor normalverteilt mit länge arity
		// berechne zufallszahl z log-normal-verteilt aus [0,2]
		// mutiere damit die mutabilität des zufällig gewählten Elters [i] multiplikativ mit z
		// addiere mutierte mutabilität mal z zum elter => speichere als nachkomme
		// füge nachkomme zur population Q hinzu
		// füge mutabilität des nachkommens zu M hinzu
    }


	private double[] create_ancestor (int arity) { // Aaron
    	// länge des returnarrays = arity
    	// for schleife 0 bis arity-1
    		// jede komponente random innerhalb this.getOb().getRange() der entsprechenden Komponente;
    		// returnarray[i] setzen
    	// ende schleife
    	return null;
    }
    
    private double getRandomDouble (double[] limits) { // Aaron + NV + LNV
    	// lese limits[0] als untergrenze und limits[1] als obergrenze
    	// erzeuge random double zwischen den grenzen
    	return 0.0;
    }
    
    private void setInitialMutabilities() { // Yorck
		// setze mutabilitäten initial (diese sollen sich später ändern können)	
	}
    
    private double[] getReturnIndividual() { // Yorck
    	// nimm die aktuelle population
    	// iteriere drüber und berechne funktionswerte
    	// wähle das individuum mit dem kleinsten funktionswert
    	// returne das individuum
    	return new double[] {0};
    }
    
}