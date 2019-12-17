package de.cau.informatik.algo.problems.evolution;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

class Evolution {
	private double mutability; // Wird im Konstruktor gesetzt
	private int lambda; // Wird im Konstruktor gesetzt
	private int mu; // Wird im Konstruktor gesetzt
	private ObjFunction ob; // Wird dem Algorithmus beim Aufruf übergeben
	private long seed; // Wird dem Algorithmus beim Aufruf übergeben
	//	private double[] mutab_mu; // Array der Mutabilitäten von Mu
	//	private double[] mutab_lambda; // Array der Mutabilitäten von Lambda
	//	private double[][] P; // Population P
	//	private double[][] Q; // entspricht Skript Population "P Dach"
	private List<PopulationElement> listP;
	private List<PopulationElement> listQ;
	private Random zufallszahl;

	/**
	 * Create an Evolution object to perform the evolutionary algorithm.
	 *
	 * @param f          The objective function to minimize
	 * @param mutability The initial mutability
	 * @param lambda     The number of children to be produced
	 * @param mu         The size of the population
	 * @param seed       The seed to the random number generator
	 */
	public Evolution(ObjFunction f, long seed) {
		this.setOb(f); // wird dem Konstruktor übergeben
		this.setSeed(seed); // wird dem Konstruktor übergeben

		// Selbst festgelegte Parameter (eigener Ermessensspielraum)
		this.setMutability(1.0);
		this.setLambda(50);
		this.setMu(50);
		this.setZufallszahl(new Random(seed));// Erstellt ein Objekt der Klasse Random mit dem gegebenen Seed, auf dem die Methoden zur Zufallszahl ausgeführt werden können; wichtig dafür: import java.util.*
	}

	/**
	 * Repeat the evolutionary algorithm for a certain number of steps
	 *
	 * @param steps The number of iterations performed
	 * @return The best value discovered during the algorithm
	 */
	// soweit fertig
	public double[] compute(int steps) { // Aaron
		// Struktur für Startpopulation deklarieren
		// get arity = getOb().getArity().

		// Schleife über Mu
		// create_ancestor(arity) -> eigene Methode
		// Diese der leeren Population hinzufügen => Startpopulation fertig
		// Ende Schleife

		double[][] Startpopulation = new double[getMu()][getOb().getArity()];            // Struktur für Startpolutaion: Startpop. besteht aus mu Individuen, mit Werten der Anzahl an Parametern entsprechend

		for (int i = 0; i < getMu(); i++) {                                                // schleife über mu
//			Startpopulation[i] = create_ancestor();                                            // create_ancestor() erstellt ein Individuum, welches dann der Startpopulation hinzugefügt wird
			getListP().add(create_ancestor());
		}

//		setP(Startpopulation); // Startpopulation merken

		setInitialMutabilities();

		for (int i = 0; i < steps; i++) { // Yorck

			// Schritt 0:
			// Kopiere Startpopulation als Q
			// Kopiere Array der Mutabilitäten als M
//			setQ(getP());
			setListQ(getListP());
//			setMutab_lambda(getMutab_Mu());


			// Schritt 1:
			// schleife über lambda:
			// create_successor();
			// ende schleife über lambda
			for (int j = 0; j < getLambda(); j++) {
				create_successor();
			}


			// Schritt 2: // Johannes
			// Bilde Array der Funktionswerte in der Reihenfolge von Q
			// sortiere Q in Reihe ihrer Funktionswerte aufsteigend
			// sortiere f(x)[] entsprechend
			// übernimm die ersten mu Individuen nach P
			setListP(sortAndLimitList(getListQ()));

			// Schritt 3: // Johannes
			// übernimm die mutabilitäten der auswahl aus Q, die es nach P schafft
		}

//    	return getReturnIndividual(); TODO
		return null;
	}

	// 0 %
	private void create_successor() { // Yorck
		// Wähle zufälliges Individuum aus Mu gleichverteilt
		// Berechne Zufallsvektor normalverteilt mit Länge Arity
		// Berechne getZufallszahl() z log-normal-verteilt aus [0,2]
		// Mutiere damit die Mutabilität des zufällig gewählten Elters [i] multiplikativ mit z
		// Addiere mutierte Mutabilität mal z zum Elter => Speichere als Nachkomme
		// Füge Nachkomme zur Population Q hinzu
		// Füge Mutabilität des Nachkommens zu M hinzu
	}

	// muss wahrscheinlich neu - Johannes
	private PopulationElement create_ancestor() { // Aaron
		// länge des returnarrays = arity
		// for schleife 0 bis arity-1
		// jede komponente random innerhalb getOb().getRange() der entsprechenden Komponente;
		// returnarray[i] setzen
		// ende schleife


		double[] ancestor = new double[getOb().getArity()];        // Länge des Returnarrays = arity = Anzahl der Parameter

		for (int i = 0; i < getOb().getArity(); i++) {                // Schleife über die Dimensionen (Anzahl Parameter)
//    		double MinValue = getOb().getRange(i)[0];				// ermittelt Mindestwert des Parameters
//    		double MaxValue = getOb().getRange(i)[1];				// ermittelt Maximalwert des Parameters
//    		ancestor[i] = getRandomDouble(MinValue, MaxValue);			// getRandomDouble ermittelt zufälligen Wert zwischen Min und Max, der als Parameterwert des Individuums gespeichert wird
			ancestor[i] = getRandomDouble(getOb().getRange(i)[0], getOb().getRange(i)[1]);
		}

//    	return ancestor;
		return new PopulationElement(new double[]{0}, 1);
	}

	// fertig
	private void setInitialMutabilities() { // Yorck
		// setze mutabilitäten initial (diese sollen sich später ändern können)
		for (int i = 0; i < getMu(); i++) {
//    		mutab_mu[i] = getMutability(); TODO
		}
	}

	// fertig
	private PopulationElement getReturnIndividual() { // Yorck
		// nimm die aktuelle population
		// iteriere drüber und berechne funktionswerte
		// wähle das individuum mit dem kleinsten funktionswert
		// returne das individuum
//    	return new double[] {0};
		return getListP().get(0);
	}


	// Methoden zur Erstellung der getZufallszahl()en:

	// fertig
	private double getRandomDouble(double min, double max) {                // Methode zur Erstellung von Parameterwerten der Individuen. Empfängt min und max als Unter- bzw. Obergrenze

		return (min + (max - min) * getZufallszahl().nextDouble());            // gibt einen gleichverteilten Double zwischen Min und Max zurück
	}

	// fertig
	private int getRandomIndex() {                                        // Methode zur zufälligen Auswahl von Eltern

		return getZufallszahl().nextInt(getMu());                        // gibt einen zufälligen Integer zwischen 0 und mu zurück
	}

	// fertig
	private double[] getRandomZ() {                                        // Methode zur Ermittlung eines normalverteilten Zufallsvektors z zwischen -1 und 1 (für die Mutation eines Elters)

		double[] z = new double[getOb().getArity()];                // Erstellung eines leeren Vektors mit length arity

		for (int i = 0; i < getOb().getArity(); i++) {                // Schleife über die Dimensionen

			double NV = getZufallszahl().nextGaussian();                    // Erstellung eines zufälligen Doubles mit Erwartungswert 0 und Standardabweichung 1

			while (NV > 1 || NV < -1) {                                    // solange Bedinung "zwischen -1 und 1" nicht zutrifft,
				NV = getZufallszahl().nextGaussian();                        // wird eine neue getZufallszahl() generiert.
			}

			z[i] = NV;                                                    // zufälliger Double, der Bedingung erfüllt, wird in z übergeben.
		}

		return z;
	}

	// fertig
	private double getRandomKsi() {                                        // Methode zur Ermittlung einer log-normalverteilten getZufallszahl() Ksi zwischen 0 und 2 (für die Mutation der Mutabilitä)

		double Ksi = Math.exp(getZufallszahl().nextGaussian());                // Erstellung eines zufälligen Doubles mit Erwartungswert 0 und Standardabweichung 1

		while (Ksi > 2 || Ksi < 0) {                                        // solange Bedinung "zwischen -1 und 1" nicht zutrifft,
			Ksi = Math.exp(getZufallszahl().nextGaussian());                    // wird eine neue getZufallszahl() generiert.
		}

		return Ksi;
	}

	private List<PopulationElement> sortAndLimitList(List<PopulationElement> list) {
		return list.stream()
				.sorted(Comparator.comparing(PopulationElement::getFunctionValue))
				.limit(getMu())
				.collect(Collectors.toList());
	}

	private class PopulationElement {

		public PopulationElement(double[] parameter, double mutability) {
			this.parameter = parameter;
			this.mutability = mutability;

			this.functionValue = getOb().eval(parameter);
		}

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

	public void setSeed(long s) {
		this.seed = s;
	}

	public double getMutability() {
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

	public List<PopulationElement> getListP() {
		return listP;
	}

	public void setListP(List<PopulationElement> listP) {
		this.listP = listP;
	}

	public List<PopulationElement> getListQ() {
		return listQ;
	}

	public void setListQ(List<PopulationElement> listQ) {
		this.listQ = listQ;
	}

	public Random getZufallszahl() {
		return zufallszahl;
	}

	public void setZufallszahl(Random zufallszahl) {
		this.zufallszahl = zufallszahl;
	}

}