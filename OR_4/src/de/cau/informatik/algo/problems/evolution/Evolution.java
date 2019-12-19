package de.cau.informatik.algo.problems.evolution;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


class Evolution {

	
	/** Erkl�rung zur Zusammenarbeit
	 * Teile des Codes wurden in Zusammenarbeit mit Johannes Kollien erstellt. Insbesondere die Umsetzung der Idee,
	 * mit Listen statt HashMaps zu arbeiten und diese zu sortieren sind sein Werk.
	 * Ebenso greifen wir auf seinen Vorschlag, Testf�lle von https://www.sfu.ca zu verwenden, zur�ck.
	 **/
	


	private double mutability; // Wird im Konstruktor gesetzt
	private int lambda; // Wird im Konstruktor gesetzt
	private int mu; // Wird im Konstruktor gesetzt
	private ObjFunction ob; // Wird dem Algorithmus beim Aufruf �bergeben
	private long seed; // Wird dem Algorithmus beim Aufruf �bergeben
	private List<PopulationElement> listP = new ArrayList(); // Population "P" der Eltern, ihrer Funktionswerte und Mutabilit�ten 
	private List<PopulationElement> listQ = new ArrayList(); // Neue Population "P Dach" der Nachkommen, ihrer Funktionswerte und Mutabilit�ten
	private Random zufallszahl; // Referenz auf java.util.random

	
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
		this.setOb(f); // wird dem Konstruktor �bergeben
		this.setSeed(seed); // wird dem Konstruktor �bergeben
		this.setZufallszahl(new Random(seed));// Erstellt ein Objekt der Klasse Random mit dem gegebenen Seed, auf dem die Methoden zur Zufallszahl ausgef�hrt werden k�nnen; wichtig daf�r: import java.util.*
		
		
	     /** Zur Wahl der Parameter Delta, Lambda, Mu
		 * Begonnen haben wir mit den Werten (1, 50, 50). Dies half uns vor Allem in der Entwurfsphase, wann wir welche Lambdas und Mus betrachten.
		 * Sowie die Implementierung fertig war, erh�hten wir die Paramterwerte in den dreistelligen Bereich, ohne Erfolge zu verbuchen.
		 * Schlie�lich erh�hten wir sie drastisch, und fanden in der Konstellation (3, 10000, 3000) eine erste, die (bei gro�em Rechenaufwand)
		 * alle gestellten Probleme l�ste.
		 * 
		 * Durch ausprobieren verschiedener einzelner Wertreduzierungen konnten wir sehen, dass wir bei (2.5, 9750, 2275) gerade noch alle Probleme l�sen k�nnen,
		 * mit etwas vermindertem Rechenaufwand. Viel weiter kann keiner der drei Parameter reduziert werden, ohne dass erste Tests fehlschlagen. 
		 */
		
		
		// Selbst festgelegte Parameter (eigener Ermessensspielraum)
		this.setMutability(2.5); // Erstmals erfolgreich mit Wert 3
		this.setLambda(9750); // Erstmals erfolgreich mit Wert 10000
		this.setMu(2275); // Erstmals erfolgreich mit Wert 3000
	}

	/**
	 * Repeat the evolutionary algorithm for a certain number of steps
	 *
	 * @param steps The number of iterations performed
	 * @return The best value discovered during the algorithm
	 */
	public double[] compute(int steps) {
		// Struktur f�r Startpopulation deklarieren
		// get arity = getOb().getArity().

		// Schleife �ber Mu
			// create_ancestor(arity) -> eigene Methode
			// Diese der leeren Population hinzuf�gen => Startpopulation fertig
		// Ende Schleife
		// double[][] Startpopulation = new double[getMu()][getOb().getArity()];            // Struktur f�r Startpolutaion: Startpop. besteht aus mu Individuen, mit Werten der Anzahl an Parametern entsprechend

		for (int i = 0; i < getMu(); i++) {                                                	// schleife �ber mu
			getListP().add(create_ancestor());												// create_ancestor() erstellt ein Individuum, welches dann der Startpopulation hinzugef�gt wird
		}

		for (int i = 0; i < steps; i++) {

			// Schritt 0:
			// Kopiere Startpopulation als Q
			setListQ(new ArrayList(getListP()));

			// Schritt 1:
			// Schleife �ber lambda:
				// create_successor();
			// Ende schleife �ber lambda
			for (int j = 0; j < getLambda(); j++) {
				getListQ().add(create_successor());											// Es werden lambda Nachkommen erzeugt und sofort der Population Q (im Skript: P Dach) hinzugef�gt
			}

			// Schritt 2:
			// Bilde Liste der Funktionswerte in der Reihenfolge von Q
			// Sortiere Q in Reihe ihrer Funktionswerte aufsteigend
			// Sortiere f(x)[] entsprechend
			// �bernimm die ersten mu Individuen nach P
			setListP(sortAndLimitListQ());

			// Schritt 3:
			// �bernimm die Mutabilit�ten der Auswahl aus Q, die es nach P schafft.
			// Das geschieht in unserer L�sung automatisch, wenn die Objekte von Q nach P �bernommen werden.
		}

		// Jetzt noch das Individuum ausw�hlen, welches als Bestes am Ende aller Generationen verbleibt und an den Aufrufer zur�ck gegeben wird.
		// Dies ist aufgrund der sortierten Struktur der Population P zwangsl�ufig das erste Element.
		return getFirstElementOfListP().getParameter();
	}

	
	
	// ******** Hilfsmethoden f�r den Algorithmus
	private PopulationElement create_ancestor() {							// Wird ben�tigt, um die Startpopulation vor der ersten Iteration zu erstellen
		// L�nge des returnarrays = arity
		// For-Schleife 0 bis arity-1
			// Jede Komponente random innerhalb getOb().getRange() der entsprechenden Komponente;
			// Returnarray[i] setzen
		// Ende schleife

		double[] parameters = new double[getOb().getArity()];			// L�nge des Returnarrays = Arity = Anzahl der Parameter

		for (int i = 0; i < getOb().getArity(); i++) {                	// Schleife �ber die Dimensionen (Anzahl Parameter)
			parameters[i] = getRandomDouble(getOb().getRange(i));
		}

		// Erstelle mit den generierten Merkmalen ein Individuum f�r die Elter-Generation.
		return new PopulationElement(parameters, getMutability());
	}

	private PopulationElement create_successor() {							// W�hlt einen zuf�lligen Elter und generiert einen mutierten Nachkommen
		// W�hle zuf�lliges Individuum aus Mu gleichverteilt
		// Berechne Zufallsvektor normalverteilt mit L�nge Arity
		// Berechne Zufallszahl z log-normal-verteilt aus [0,2]
		// Mutiere damit die Mutabilit�t des zuf�llig gew�hlten Elters [i] multiplikativ mit z
		// Addiere mutierte Mutabilit�t mal z zum Elter => Speichere als Nachkomme
		// F�ge Nachkomme mit eigener Mutabilit�t zur Population Q hinzu

		PopulationElement parent = getListP().get(getRandomIndex()); //1
		double childMutability = parent.getMutability() * getRandomKsi(); //4

		double[] childParameters = new double[getOb().getArity()]; //2
		double[] randomZ = getRandomZ(); //3
		
		for (int i = 0; i < getOb().getArity(); i++) {
			double minRange = getOb().getRange(i)[0];
			double maxRange = getOb().getRange(i)[1];
			double childParameter = parent.getParameter()[i] + childMutability * randomZ[i]; //5

			// Liegt der Wert innerhalb des zul�ssigen Bereichs?
			if (childParameter <= minRange) {
				childParameters[i] = minRange;
			} else if (maxRange <= childParameter) {
				childParameters[i] = maxRange;
			} else {
				childParameters[i] = childParameter;
			}
		}

		return new PopulationElement(childParameters, childMutability); //6 und in aufrufender Methode
	}

	private PopulationElement getFirstElementOfListP() {					// Liefert das erste Element der Population P
		// Da unsere Listen (also die Populationen P und Q) nach Funktionswerten aufsteigend sortiert sind, ist das erste Element zugleich das Beste.
		// Hierzu: Liste Q wird sortiert, davon werden die ersten mu als neues P �bernommen, P ist also auch sortiert.
		return getListP().get(0);
	}

	private List<PopulationElement> sortAndLimitListQ() {					// Sortiert eine Population aufsteigend gem�� ihrer Funktionswerte
		return getListQ().stream()
				.sorted(Comparator.comparing(PopulationElement::getFunctionValue))
				.limit(getMu())
				.collect(Collectors.toList());
	}
	
	
	
	// ******** Die innere Klasse "PopulationElement" enth�lt nur Konstruktor, Getter und Setter. Sie bildet das Individuum mit Merkmalen, individueller geerbter Mutabilit�t und Funktionswert ab.
	private class PopulationElement {

		// Attribute
		private double functionValue;
		private double[] parameter;
		private double mutability;
		
		// Konstruktor
		public PopulationElement(double[] parameter, double mutability) {
			this.parameter = parameter;
			this.mutability = mutability;
			this.functionValue = getOb().eval(parameter);
		}

		// Getter + Setter
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

	
	
	// ******** Methoden zur Erstellung der getZufallszahl()en:
	private double getRandomDouble(double[] rangeVector) {                	// Methode zur Erstellung von Parameterwerten der Individuen. Empf�ngt min und max als Unter- bzw. Obergrenze
		double min = rangeVector[0];
		double max = rangeVector[1];
		return (min + (max - min) * getZufallszahl().nextDouble());            // gibt einen gleichverteilten Double zwischen Min und Max zurück
	}

	private int getRandomIndex() {                                        	// Methode zur zuf�lligen Auswahl von Eltern

		return getZufallszahl().nextInt(getMu());                        // gibt einen zuf�lligen Integer zwischen 0 und mu zur�ck
	}

	private double[] getRandomZ() {											// Methode zur Ermittlung eines normalverteilten Zufallsvektors z zwischen -1 und 1 (f�r die Mutation eines Elters)

		double[] z = new double[getOb().getArity()];                // Erstellung eines leeren Vektors mit length arity

		for (int i = 0; i < getOb().getArity(); i++) {                // Schleife �ber die Dimensionen

			double NV = getZufallszahl().nextGaussian();                    // Erstellung eines zuf�lligen Doubles mit Erwartungswert 0 und Standardabweichung 1

			while (NV > 1 || NV < -1) {                                    // solange Bedinung "zwischen -1 und 1" nicht zutrifft,
				NV = getZufallszahl().nextGaussian();                        // wird eine neue getZufallszahl() generiert.
			}

			z[i] = NV;                                                    // zuf�lliger Double, der Bedingung erf�llt, wird in z �bergeben.
		}

		return z;
	}

	private double getRandomKsi() {                                         // Methode zur Ermittlung einer log-normalverteilten getZufallszahl() Ksi zwischen 0 und 2 (für die Mutation der Mutabilitä)

		double Ksi = Math.exp(getZufallszahl().nextGaussian());                // Erstellung eines zufälligen Doubles mit Erwartungswert 0 und Standardabweichung 1

		while (Ksi > 2 || Ksi < 0) {                                        // solange Bedinung "zwischen -1 und 1" nicht zutrifft,
			Ksi = Math.exp(getZufallszahl().nextGaussian());                    // wird eine neue getZufallszahl() generiert.
		}

		return Ksi;
	}

	
	
	// ******** Getter + Setter zu 'Evolution'
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

	public void setListP(List<PopulationElement> listP) {
		this.listP = listP;
	}

	public void setListQ(List<PopulationElement> listQ) {
		this.listQ = listQ;
	}

	public void setZufallszahl(Random zufallszahl) {
		this.zufallszahl = zufallszahl;
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

	public List<PopulationElement> getListQ() {
		return listQ;
	}

	public Random getZufallszahl() {
		return zufallszahl;
	}
}