package org.ciat.cmit.control;


import java.text.DecimalFormat;
import java.util.ArrayList;

import org.ciat.cmit.model.CoefficientDomain;
import org.ciat.cmit.model.CultivarRun;

public class App {

	public static void main(String[] args) {
		ArrayList<String> combinations = new ArrayList<String>();

		/* palvarez test cases */
		ArrayList<CoefficientDomain> domains = new ArrayList<CoefficientDomain>();
		domains.add(new CoefficientDomain(17.0, 20.0, 0.15, new DecimalFormat("#0.00")));
		domains.add(new CoefficientDomain(2.5, 7.0, 0.3, new DecimalFormat("##0.0")));
		domains.add(new CoefficientDomain(9.0, 19.0, 0.3, new DecimalFormat("##0.0")));
		domains.add(new CoefficientDomain(11.0, 25.0, 0.3, new DecimalFormat("#0.00")));
		
		

		for (CoefficientDomain domain : domains) {
			combinations = RunningFilesGenerator.getCombinations(domain, combinations);
		}

		combinations = RunningFilesGenerator.addPrefixAndSufix(combinations);

		ArrayList<CultivarRun> cultivars = RunningFilesGenerator.writeCultivars(combinations);
		
		RunningFilesGenerator.writeFileX(cultivars);

		RunningFilesGenerator.writeBatch(cultivars);

		RunningFilesGenerator.writeBats(cultivars);

		// RunningFilesGenerator.writeMasterBat(cultivars);
		
		RunningFilesGenerator.writeMasterBatPerFolder(cultivars);
	}

}
