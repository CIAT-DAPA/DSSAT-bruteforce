package jCULcreator;

import java.util.ArrayList;

public class App {
	private static ArrayList<String> combinations = new ArrayList<String>();

	public static void main(String[] args) {

		CoefficientDomain[] c = new CoefficientDomain[2];
		c[0] = new CoefficientDomain(100, 200, 10);
		c[1] = new CoefficientDomain(500.5, 550.5, 0.5);
		
		for (int i = 0; i < c.length; i++) {	
			combinations=getCombinations(c[i], combinations);
			System.out.println("iter"+i);
		}

	}

	public static ArrayList<String> getCombinations(CoefficientDomain c, ArrayList<String> combinations) {
		String temp;
		ArrayList<String> newCombinations = new ArrayList<String>();

		if (combinations.size() == 0) {
			for (double j = c.getMinValue(); j <= c.getMaxValue(); j += c.getPeriod()) {  // for the first coefficient
				temp=j+"";
				newCombinations.add(temp);
			}
		} else {
			for (String combination : combinations) {
				for (double j = c.getMinValue(); j <= c.getMaxValue(); j += c.getPeriod()) {
					temp=combination+" "+j;
					newCombinations.add(temp);
				}
			}


		}
		return newCombinations;
	}

}
