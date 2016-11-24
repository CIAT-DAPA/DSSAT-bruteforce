package org.ciat.cmit.control;

import java.util.ArrayList;

import org.ciat.cmit.model.CoefficientDomain;

public class App {

	public static void main(String[] args) {
		ArrayList<String> combinations = new ArrayList<String>();

		CoefficientDomain[] c = new CoefficientDomain[2];
		c[0] = new CoefficientDomain(100, 200, 10);
		c[1] = new CoefficientDomain(500.5, 550.5, 0.5);
		
		for (int i = 0; i < c.length; i++) {	
			combinations=getCombinations(c[i], combinations);
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
	
	public static void printCombinations(ArrayList<String> combinations){
		for (String combination : combinations) {
			System.out.println(combination);
			}
	}

}
