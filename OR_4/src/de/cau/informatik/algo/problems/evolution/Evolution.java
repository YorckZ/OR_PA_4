package de.cau.informatik.algo.problems.evolution;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

class Evolution{
	private double mutability; // Wird im Konstruktor gesetzt
	private int lambda; // Wird im Konstruktor gesetzt
	private int mu; // Wird im Konstruktor gesetzt
	private ObjFunction ob; // Wird dem Algorithmus beim Aufruf �bergeben
	private long seed; // Wird dem Algorithmus beim Aufruf �bergeben
//	private double[] mutab_mu; // Array der Mutabilit�ten von Mu
//	private double[] mutab_lambda; // Array der Mutabilit�ten von Lambda
//	private double[][] P; // Population P
//	private double[][] Q; // entspricht Skript Population "P Dach"
	private List<PopulationElement> ListP;
	private List<PopulationElement> ListQ;
	
	// bislang ohne Getter/Setter/SLoA
	private Random Zufallszahl = new Random();									// Erstellt ein Objekt der Klasse Random, auf dem die Methoden zur Zufallszahl ausgef�hrt werden k�nnen; wichtig daf�r: import java.util.*
	
	
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
    
    
    // Getter + Setter
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
	// soweit fertig
	public double[] compute(int steps) { // Aaron
		// Struktur f�r Startpopulation deklarieren
		// get arity = this.getOb().getArity().

		// Schleife �ber Mu
		// create_ancestor(arity) -> eigene Methode
		// Diese der leeren Population hinzuf�gen => Startpopulation fertig
		// Ende Schleife

		double[][] Startpopulation = new double[this.getMu()][this.getOb().getArity()];            // Struktur f�r Startpolutaion: Startpop. besteht aus mu Individuen, mit Werten der Anzahl an Parametern entsprechend

		for (int i = 0; i < this.getMu(); i++) {                                                // schleife �ber mu
//			Startpopulation[i] = create_ancestor();                                            // create_ancestor() erstellt ein Individuum, welches dann der Startpopulation hinzugef�gt wird
			this.getListP().add( create_ancestor() );
		}
		
//		this.setP(Startpopulation); // Startpopulation merken

		this.setInitialMutabilities();

		for (int i = 0; i < steps; i++) { // Yorck

			// Schritt 0:
			// Kopiere Startpopulation als Q
			// Kopiere Array der Mutabilit�ten als M
//			this.setQ(this.getP());
			this.setListQ(this.getListP());
//			this.setMutab_lambda(this.getMutab_Mu());


			// Schritt 1:
			// schleife �ber lambda:
			// create_successor();
			// ende schleife �ber lambda
			for (int j = 0; j < this.getLambda(); j++) {
				create_successor();
			}


			// Schritt 2: // Johannes
			// Bilde Array der Funktionswerte in der Reihenfolge von Q
			// sortiere Q in Reihe ihrer Funktionswerte aufsteigend
			// sortiere f(x)[] entsprechend
    		// �bernimm die ersten mu Individuen nach P
			setListP(sortAndLimitList(getListQ()));
    		
    		// Schritt 3: // Johannes
    		// �bernimm die mutabilit�ten der auswahl aus Q, die es nach P schafft
    	}
    	
//    	return getReturnIndividual(); TODO
		return null;
    }

    // 0 %
    private void create_successor() { // Yorck
		// W�hle zuf�lliges Individuum aus Mu gleichverteilt
		// Berechne Zufallsvektor normalverteilt mit L�nge Arity
		// Berechne Zufallszahl z log-normal-verteilt aus [0,2]
		// Mutiere damit die Mutabilit�t des zuf�llig gew�hlten Elters [i] multiplikativ mit z
		// Addiere mutierte Mutabilit�t mal z zum Elter => Speichere als Nachkomme
		// F�ge Nachkomme zur Population Q hinzu
		// F�ge Mutabilit�t des Nachkommens zu M hinzu
    }

    // muss wahrscheinlich neu - Johannes
	private PopulationElement create_ancestor () { // Aaron
    	// l�nge des returnarrays = arity
    	// for schleife 0 bis arity-1
    		// jede komponente random innerhalb this.getOb().getRange() der entsprechenden Komponente;
    		// returnarray[i] setzen
    	// ende schleife
		
		
    	double [] ancestor = new double [this.getOb().getArity()];		// L�nge des Returnarrays = arity = Anzahl der Parameter
    		
    	for (int i = 0; i < this.getOb().getArity(); i++) {				// Schleife �ber die Dimensionen (Anzahl Parameter)
//    		double MinValue = this.getOb().getRange(i)[0];				// ermittelt Mindestwert des Parameters
//    		double MaxValue = this.getOb().getRange(i)[1];				// ermittelt Maximalwert des Parameters
//    		ancestor[i] = getRandomDouble(MinValue, MaxValue);			// getRandomDouble ermittelt zuf�lligen Wert zwischen Min und Max, der als Parameterwert des Individuums gespeichert wird
    		ancestor[i] = getRandomDouble(this.getOb().getRange(i)[0], this.getOb().getRange(i)[1]);
    	}
    		
//    	return ancestor;
    	return new PopulationElement();
    }

	// fertig
    private void setInitialMutabilities() { // Yorck
		// setze mutabilit�ten initial (diese sollen sich sp�ter �ndern k�nnen)
    	for (int i=0; i<this.getMu(); i++) {
//    		this.mutab_mu[i] = this.getMutability(); TODO
    	}
	}
    
    // fertig
    private PopulationElement getReturnIndividual() { // Yorck
    	// nimm die aktuelle population
    	// iteriere dr�ber und berechne funktionswerte
    	// w�hle das individuum mit dem kleinsten funktionswert
    	// returne das individuum
//    	return new double[] {0};
    	return this.getListP().get(0);
    }

    
    // Methoden zur Erstellung der Zufallszahlen:
    
    // fertig
	private double getRandomDouble(double min, double max){				// Methode zur Erstellung von Parameterwerten der Individuen. Empf�ngt min und max als Unter- bzw. Obergrenze
		
		return (min + (max - min) * Zufallszahl.nextDouble());			// gibt einen gleichverteilten Double zwischen Min und Max zur�ck
	}
	
	// fertig
	private int getRandomIndex(){ 										// Methode zur zuf�lligen Auswahl von Eltern
    	
    	return Zufallszahl.nextInt(this.getMu());						// gibt einen zuf�lligen Integer zwischen 0 und mu zur�ck									
    }
	
	// fertig
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

	// fertig
	private double getRandomKsi() {                                        // Methode zur Ermittlung einer log-normalverteilten Zufallszahl Ksi zwischen 0 und 2 (f�r die Mutation der Mutabilit�)

		double Ksi = Math.exp(Zufallszahl.nextGaussian());                // Erstellung eines zuf�lligen Doubles mit Erwartungswert 0 und Standardabweichung 1

		while (Ksi > 2 || Ksi < 0) {                                        // solange Bedinung "zwischen -1 und 1" nicht zutrifft,
			Ksi = Math.exp(Zufallszahl.nextGaussian());                    // wird eine neue Zufallszahl generiert.
		}

		return Ksi;
	}


	private List<PopulationElement> sortAndLimitList(List<PopulationElement> list) {
		return list.stream()
				.sorted(Comparator.comparing(PopulationElement::getFunctionValue))
				.limit(getMu())
				.collect(Collectors.toList());
	}

	public List<PopulationElement> getListP() {
		return ListP;
	}


	public void setListP(List<PopulationElement> listP) {
		ListP = listP;
	}


	public List<PopulationElement> getListQ() {
		return ListQ;
	}


	public void setListQ(List<PopulationElement> listQ) {
		ListQ = listQ;
	}

	private class PopulationElement {

		private double functionValue;
		private double[] parameter;
		private double mutability;

		public double getFunctionValue() {
			return functionValue;
		}

		public void setFunctionValue(double functionValue) {
			this.functionValue = functionValue;
		}

		public double[] getParameter() {
			return parameter;
		}

		public void setParameter(double[] parameter) {
			this.parameter = parameter;
		}

		public double getMutability() {
			return mutability;
		}

		public void setMutability(double mutability) {
			this.mutability = mutability;
		}
	}

}