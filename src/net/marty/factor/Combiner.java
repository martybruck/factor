package net.marty.factor;

import java.util.ArrayList;
import java.util.List;

public class Combiner {
	private List<String> originalFactors = new ArrayList<String>();
	private final int numFactors;
	private int itemsPerGroup;

	public Combiner(int numFactors, int itemsPerGroup) {
		this.numFactors = numFactors;
		this.itemsPerGroup = itemsPerGroup;
		for (int i = 0; i < this.numFactors; i++) {
			originalFactors.add(Character.toString((char) ((int) 'a' + i)));
		}
	}

	private List<String> getCombinations(List<String> factors) {
		// check for terminal case where factor size matches the requested
		// itemsPerGroup
		if (this.itemsPerGroup == 1) {
			return factors;
		}
		// figure out what the new size of will be if all of the items are combined.
		// if it is the target size (i.e. itemsPerGroup), do the combination
		// Otherwise combine the first and second elements of the array
		// and recursively call getCombinations until it finds the right size
		List<String> updatedEntries = new ArrayList<String>();
		String firstFactor = factors.get(0);
		int newSize = firstFactor.length() + 1;
		if (newSize == this.itemsPerGroup) {
			updatedEntries.addAll(combineAllEntries(factors));
		} else {
			// combine the first two elements and call recursively
			List<String> newEntries = new ArrayList<String>();
			newEntries.add(firstFactor + factors.get(1));
			newEntries.addAll(factors.subList(2, factors.size()));
			updatedEntries = getCombinations(newEntries);
		}

		// repeat for all of the subsequent elements of the array.
		// do all of the elements until there aren't enough to create an
		// element big enough to create a full factor. For example,
		// if there are 4 elements (a,b,c,d), and you want to choose 3,
		// you can only start with a or b since all you could create starting with
		// c is cd, which won't be big enough.
		if (factors.size() > this.itemsPerGroup) {
			updatedEntries.addAll(getCombinations(factors.subList(1, factors.size())));
		}
		return updatedEntries;
	}

	// take the first element, and combine it with each of the subsequent
	// elements in the array.
	private List<String> combineAllEntries(List<String> factors) {
		List<String> newFactors = new ArrayList<String>();
		String firstFactor = factors.get(0);
		for (int i = 1; i < factors.size(); i++) {
			newFactors.add(firstFactor + factors.get(i));
		}
		return newFactors;
	}

	String formatFactors(List<String> updatedFactors) {
		StringBuffer out = new StringBuffer();
		out.append("(");
		for (int i = 0; i < updatedFactors.size(); i++) {
			out.append(updatedFactors.get(i));
			if (i < updatedFactors.size() - 1) {
				out.append(" + ");
			}
		}
		out.append(")");
		return out.toString();

	}

	List<String> getCombinations() {
		return getCombinations(originalFactors);
	}

	// For testing only - the actual main is in Factorer
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Usage: <arg1> choose <arg2>.");
		}
		Combiner combiner = new Combiner(4, 2);
		List<String> updatedFactors = combiner.getCombinations();
		System.out.println(combiner.formatFactors(updatedFactors));
	}
}
