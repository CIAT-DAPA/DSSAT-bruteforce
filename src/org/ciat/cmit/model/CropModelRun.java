package org.ciat.cmit.model;

import java.util.ArrayList;
import java.util.List;

public class CropModelRun {

	private List<CoefficientDomain> domains;
	private List<CultivarRun> cultivars;
	private List<String> combinations;
	private List<Integer> cultivarTreatments;
	private CropModel model;
	private int maxFiles;
	private String vrName;
	private String eco;

	public CropModelRun(List<CoefficientDomain> domains, CropModel model, int maxFiles, String vrname, String eco, List<Integer> cultivarTreatments) {
		super();
		this.domains = domains;
		this.model = model;

		if (maxFiles < 0 || maxFiles > 100000) { // set maxFiles no less than zero and no bigger than 100000
			this.maxFiles = 100000;
		} else {
			this.maxFiles = maxFiles;
		}

		this.cultivars = new ArrayList<>();
		this.combinations = new ArrayList<>();
		this.setVrName(vrname);
		this.setEco(eco);
		this.setCultivarTreatments(cultivarTreatments);
	}

	public List<CoefficientDomain> getDomains() {
		return domains;
	}

	public void setDomains(ArrayList<CoefficientDomain> domains) {
		this.domains = domains;
	}

	public List<CultivarRun> getCultivars() {
		return cultivars;
	}

	public void setCultivars(ArrayList<CultivarRun> cultivars) {
		this.cultivars = cultivars;
	}

	public List<String> obtainCombinations() {
		if (combinations == null || combinations.size() == 0)
			for (CoefficientDomain domain : domains) {
				combinations = addCombination(domain, combinations);
			}

		return combinations;
	}

	private List<String> addCombination(CoefficientDomain c, List<String> combinations) {
		String temp;
		ArrayList<String> newCombinations = new ArrayList<String>();

		if (combinations == null || combinations.size() == 0) {
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

	public void setCombinations(List<String> combinations) {
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

	public String getVrName() {
		return vrName;
	}

	public void setVrName(String vrname) {
		this.vrName = vrname;
	}

	public String getEco() {
		return eco;
	}

	public void setEco(String eco) {
		this.eco = eco;
	}

	public List<Integer> getCultivarTreatments() {
		return cultivarTreatments;
	}

	public void setCultivarTreatments(List<Integer> cultivarTreatments) {
		this.cultivarTreatments = cultivarTreatments;
	}
}
