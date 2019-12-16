package de.cau.informatik.algo.problems.evolution;

import java.util.*;

public class OwnTestAaron {
	
public static void main(String[] args) {
		
		Random Zufallszahl = new Random();
				
		double NV = 2.0; 
		double LNV = 3.0;
		
		while(NV > 1 || NV < -1) {
			NV = Zufallszahl.nextGaussian();
		}
		
		while(LNV > 2 || LNV < 0) {
			LNV = Math.exp(Zufallszahl.nextGaussian());
		}	
		
		
		System.out.println(Zufallszahl.nextDouble());
		//System.out.println(Zufallszahl.nextInt(50));
		//System.out.println(NV);
		//System.out.println(LNV);	
	}
}