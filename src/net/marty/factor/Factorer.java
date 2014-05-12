package net.marty.factor;

import java.io.StringReader;
import java.util.List;

/*
 * This program was requested by a friend to show the general form of factors for an n-nomial expansion.
 * I offered to extend this to solve the problem for x given the coefficients, but all he needed was 
 * the textual representation, so here it is. Just specify the order of the equation and let it rip!
 */
public class Factorer {
	private int numFactors;

	public Factorer(int numFactors) {
		this.numFactors = numFactors;
	}
	
	private String factor() {
		StringBuffer results = new StringBuffer();
		results.append("x^"+numFactors + " + ");
		for (int i=1; i <= numFactors ;i++) {
	        Combiner combiner = new Combiner(numFactors, i);
	        List<String> factors = combiner.getCombinations();
	        results.append(combiner.formatFactors(factors));
	        if (i < numFactors) {
	        	results.append("x^" + String.valueOf(numFactors-i));
	        	results.append(" + ");

	        }
	    }
		return results.toString();
	}


	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage is factorer <n> where n is the number of factors (1-26).");
		} else  {
			int numFactors = Integer.valueOf(args[0]);
			if (numFactors < 1 || numFactors >  26) {
				System.out.println("Number of factors must be between 1 and 26");
			} else {
				Factorer factorer = new Factorer(numFactors);
				String results = factorer.factor();
				System.out.println(results);
			}
		}
	}

}
