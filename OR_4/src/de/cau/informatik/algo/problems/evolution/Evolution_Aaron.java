package de.cau.informatik.algo.problems.evolution;

import java.util.*;

class Evolution_Aaron{
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
    
    public double[] compute(int steps){ // Aaron
    	
    	double[][] Startpopulation = new double[this.getMu()][this.getOb().getArity()];			// Struktur für Startpolutaion: Startpop. besteht aus mu Individuen, mit Werten der Anzahl an Parametern entsprechend
    																							// get arity = this.getOb().getArity().
    	
    	for (int i = 0; i < this.getMu(); i++) {												// schleife über mu
    		Startpopulation[i] = create_ancestor ();											// create_ancestor() erstellt ein Individuum, welches dann der Startpopulation hinzugefügt wird
    	}    	
    	
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
		// berechne zufallsvektor normalverteilt mit länge arity ---> fertig, getRandomZ()
		// berechne zufallszahl z log-normal-verteilt aus [0,2]
		// mutiere damit die mutabilität des zufällig gewählten Elters [i] multiplikativ mit z
		// addiere mutierte mutabilität mal z zum elter => speichere als nachkomme
		// füge nachkomme zur population Q hinzu
		// füge mutabilität des nachkommens zu M hinzu
    }


	private double[] create_ancestor () { // Aaron ---> fertig
		
    	double [] ancestor = new double [this.getOb().getArity()];		// Länge des Returnarrays = arity = Anzahl der Parameter
    		
    	for (int i = 0; i < this.getOb().getArity(); i++) {				// Schleife über die Dimensionen (Anzahl Parameter)
    		double MinValue = this.getOb().getRange(i)[0];				// ermittelt Mindestwert des Parameters
    		double MaxValue = this.getOb().getRange(i)[1];				// ermittelt Maximalwert des Parameters
    		ancestor[i] = getRandomDouble(MinValue, MaxValue);			// getRandomDouble ermittelt zufälligen Wert zwischen Min und Max, der als Parameterwert des Individuums gespeichert wird
    	}
    		
    	return ancestor;
    }
    
	
    // Methoden zur Erstellung der Zufallszahlen:
	
	Random Zufallszahl = new Random();									// Erstellt ein Objekt der Klasse Random, auf dem die Methoden zur Zufallszahl ausgeführt werden können; wichtig dafür: import java.util.*
	
	private double getRandomDouble(double min, double max){				// Methode zur Erstellung von Parameterwerten der Individuen. Empfängt min und max als Unter- bzw. Obergrenze
		
		return (min + (max - min) * Zufallszahl.nextDouble());			// gibt einen gleichverteilten Double zwischen Min und Max zurück
	}
	
	
	private int getRandomIndex(){ 										// Methode zur zufälligen Auswahl von Eltern
    	
    	return Zufallszahl.nextInt(this.getMu());						// gibt einen zufälligen Integer zwischen 0 und mu zurück									
    }
	
	
	private double[] getRandomZ(){										// Methode zur Ermittlung eines normalverteilten Zufallsvektors z zwischen -1 und 1 (für die Mutation eines Elters)
		
		double [] z = new double [this.getOb().getArity()];				// Erstellung eines leeren Vektors mit length arity
		
		for (int i = 0; i < this.getOb().getArity(); i++) {				// Schleife über die Dimensionen
			
			double NV = Zufallszahl.nextGaussian(); 					// Erstellung eines zufälligen Doubles mit Erwartungswert 0 und Standardabweichung 1					
			
			while(NV > 1 || NV < -1) {									// solange Bedinung "zwischen -1 und 1" nicht zutrifft, 
				NV = Zufallszahl.nextGaussian();						// wird eine neue Zufallszahl generiert.
			}
			
			z[i] = NV;													// zufälliger Double, der Bedingung erfüllt, wird in z übergeben.
		}
		
		return z;
	}
	
	
	private double getRandomKsi() {										// Methode zur Ermittlung einer log-normalverteilten Zufallszahl Ksi zwischen 0 und 2 (für die Mutation der Mutabilitä)
		
		double Ksi = Math.exp(Zufallszahl.nextGaussian());				// Erstellung eines zufälligen Doubles mit Erwartungswert 0 und Standardabweichung 1	
		
		while(Ksi > 2 || Ksi < 0) {										// solange Bedinung "zwischen -1 und 1" nicht zutrifft, 
			Ksi = Math.exp(Zufallszahl.nextGaussian());					// wird eine neue Zufallszahl generiert.
		}
		
		return Ksi;
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