package de.cau.informatik.algo.problems.evolution;

import java.util.*;

class Evolution_Aaron{
	private double mutability;
	private int lambda;
	private int mu;
	private ObjFunction ob;
	private long seed;
	// array der mutabilit�ten mu
	// array der mutabilit�ten lambda
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
    
    public double[] compute(int steps){ // Aaron
    	
    	double[][] Startpopulation = new double[this.getMu()][this.getOb().getArity()];			// Struktur f�r Startpolutaion: Startpop. besteht aus mu Individuen, mit Werten der Anzahl an Parametern entsprechend
    																							// get arity = this.getOb().getArity().
    	
    	for (int i = 0; i < this.getMu(); i++) {												// schleife �ber mu
    		Startpopulation[i] = create_ancestor ();											// create_ancestor() erstellt ein Individuum, welches dann der Startpopulation hinzugef�gt wird
    	}    	
    	
    	this.setInitialMutabilities();
    	
    	
    	for (int i=0; i<steps; i++) { // Yorck

    		// Schritt 0:
    		// kopiere startpopulation als Q
    		// kopiere array der mutabilit�ten als M

    		// Schritt 1:
    		// schleife �ber lambda:
    			// create_successor();
    		// ende schleife �ber lambda
    		
    		
    		// Problem: wir m�ssen wissen, an welchen Positionen in Q unsere neuen P stehen.

    		
    		// Schritt 2:
    		// Bilde Array der Funktionswerte in der Reihenfolge von Q
    		// sortiere Q in Reihe ihrer Funktionswerte aufsteigend
    		// sortiere f(x)[] entsprechend
    		// �bernimm die ersten mu Individuen nach P
    		
    		// Schritt 3:
    		// �bernimm die mutabilit�ten der auswahl aus Q, die es nach P schafft
    	}
    	
    	return getReturnIndividual();
		//return null;
    }
    
    private void create_successor() { // Yorck
		// w�hle zuf�lliges individuum aus mu gleichverteilt
		// berechne zufallsvektor normalverteilt mit l�nge arity ---> fertig, getRandomZ()
		// berechne zufallszahl z log-normal-verteilt aus [0,2]
		// mutiere damit die mutabilit�t des zuf�llig gew�hlten Elters [i] multiplikativ mit z
		// addiere mutierte mutabilit�t mal z zum elter => speichere als nachkomme
		// f�ge nachkomme zur population Q hinzu
		// f�ge mutabilit�t des nachkommens zu M hinzu
    }


	private double[] create_ancestor () { // Aaron ---> fertig
		
    	double [] ancestor = new double [this.getOb().getArity()];		// L�nge des Returnarrays = arity = Anzahl der Parameter
    		
    	for (int i = 0; i < this.getOb().getArity(); i++) {				// Schleife �ber die Dimensionen (Anzahl Parameter)
    		double MinValue = this.getOb().getRange(i)[0];				// ermittelt Mindestwert des Parameters
    		double MaxValue = this.getOb().getRange(i)[1];				// ermittelt Maximalwert des Parameters
    		ancestor[i] = getRandomDouble(MinValue, MaxValue);			// getRandomDouble ermittelt zuf�lligen Wert zwischen Min und Max, der als Parameterwert des Individuums gespeichert wird
    	}
    		
    	return ancestor;
    }
    
	
    // Methoden zur Erstellung der Zufallszahlen:
	
	Random Zufallszahl = new Random();									// Erstellt ein Objekt der Klasse Random, auf dem die Methoden zur Zufallszahl ausgef�hrt werden k�nnen; wichtig daf�r: import java.util.*
	
	private double getRandomDouble(double min, double max){				// Methode zur Erstellung von Parameterwerten der Individuen. Empf�ngt min und max als Unter- bzw. Obergrenze
		
		return (min + (max - min) * Zufallszahl.nextDouble());			// gibt einen gleichverteilten Double zwischen Min und Max zur�ck
	}
	
	
	private int getRandomIndex(){ 										// Methode zur zuf�lligen Auswahl von Eltern
    	
    	return Zufallszahl.nextInt(this.getMu());						// gibt einen zuf�lligen Integer zwischen 0 und mu zur�ck									
    }
	
	
	private double[] getRandomZ(){										// Methode zur Ermittlung eines normalverteilten Zufallsvektors z zwischen -1 und 1 (f�r die Mutation eines Elters)
		
		double [] z = new double [this.getOb().getArity()];				// Erstellung eines leeren Vektors mit length arity
		
		for (int i = 0; i < this.getOb().getArity(); i++) {				// Schleife �ber die Dimensionen
			
			double NV = Zufallszahl.nextGaussian(); 					// Erstellung eines zuf�lligen Doubles mit Erwartungswert 0 und Standardabweichung 1					
			
			while(NV > 1 || NV < -1) {									// solange Bedinung "zwischen -1 und 1" nicht zutrifft, 
				NV = Zufallszahl.nextGaussian();						// wird eine neue Zufallszahl generiert.
			}
			
			z[i] = NV;													// zuf�lliger Double, der Bedingung erf�llt, wird in z �bergeben.
		}
		
		return z;
	}
	
	
	private double getRandomKsi() {										// Methode zur Ermittlung einer log-normalverteilten Zufallszahl Ksi zwischen 0 und 2 (f�r die Mutation der Mutabilit�)
		
		double Ksi = Math.exp(Zufallszahl.nextGaussian());				// Erstellung eines zuf�lligen Doubles mit Erwartungswert 0 und Standardabweichung 1	
		
		while(Ksi > 2 || Ksi < 0) {										// solange Bedinung "zwischen -1 und 1" nicht zutrifft, 
			Ksi = Math.exp(Zufallszahl.nextGaussian());					// wird eine neue Zufallszahl generiert.
		}
		
		return Ksi;
	}
	
	
	
    
	
	
	
	
	
	
    private void setInitialMutabilities() { // Yorck
		// setze mutabilit�ten initial (diese sollen sich sp�ter �ndern k�nnen)	
	}
    
    private double[] getReturnIndividual() { // Yorck
    	// nimm die aktuelle population
    	// iteriere dr�ber und berechne funktionswerte
    	// w�hle das individuum mit dem kleinsten funktionswert
    	// returne das individuum
    	return new double[] {0};
    }
    
}