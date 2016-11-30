package org.ciat.cmit.model;

import java.util.ArrayList;

public class CropModelRun {

	private ArrayList<CoefficientDomain> domains;
	private ArrayList<CultivarRun> cultivars;
	private ArrayList<String> combinations;
	private CropModel model;
	private int maxFiles;

	public CropModelRun(ArrayList<CoefficientDomain> domains, CropModel model, int maxFiles) {
		super();
		this.domains = domains;
		this.model = model;
		this.maxFiles = maxFiles>100000?100000:maxFiles; // set maxFiles no bigger than 100000
	}

	public ArrayList<CoefficientDomain> getDomains() {
		return domains;
	}

	public void setDomains(ArrayList<CoefficientDomain> domains) {
		this.domains = domains;
	}

	public ArrayList<CultivarRun> getCultivars() {
		return cultivars;
	}

	public void setCultivars(ArrayList<CultivarRun> cultivars) {
		this.cultivars = cultivars;
	}

	public ArrayList<String> getCombinations() {
		if (combinations.size() == 0)
			for (CoefficientDomain domain : domains) {
				combinations = addCombination(domain, combinations);
			}

		return combinations;
	}
	
	private ArrayList<String> addCombination(CoefficientDomain c, ArrayList<String> combinations) {
		String temp;
		ArrayList<String> newCombinations = new ArrayList<String>();

		if (combinations.size() == 0) {
			for (double j = c.getMinValue(); j <= c.getMaxValue(); j += c.getPeriod()) { /* for the first coefficient */
				temp = String.format(c.getStringFormat(), c.getNumberFormat().format(j) + "");
				newCombinations.add(temp);
			}
		} else {
			for (String combination : combinations) {
				for (double j = c.getMinValue(); j <= c.getMaxValue(); j += c.getPeriod()) {
					temp = combination + " " + String.format(c.getStringFormat(), c.getNumberFormat().format(j) + "");
					newCombinations.add(temp);

				}
			}

		}
		return newCombinations;
	}


	public void setCombinations(ArrayList<String> combinations) {
		this.combinations = combinations;
	}

	public CropModel getModel() {
		return model;
	}

	public void setModel(CropModel model) {
		this.model = model;
	}

	public int getMaxFiles() {
		return maxFiles;
	}
}
