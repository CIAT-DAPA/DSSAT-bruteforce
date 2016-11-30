package org.ciat.cmit.control;


import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.ciat.cmit.model.CoefficientDomain;
import org.ciat.cmit.model.CropModel;
import org.ciat.cmit.model.CropModelRun;
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
		
		
		CropModel beanModel = new CropModel("CRGRO046",new File(""),new File(""),new File(""),new File(""), new File(""));
		
		ModelRunGenerator mrg=new ModelRunGenerator(new CropModelRun(domains, beanModel, 100000));
		
		combinations = mrg.getCombinationsToPrint(combinations);

		ArrayList<CultivarRun> cultivars = mrg.writeCultivars(combinations);
		
		mrg.writeFileX(cultivars);

		mrg.writeBatch(cultivars);

		mrg.writeBats(cultivars);

		// RunningFilesGenerator.writeMasterBat(cultivars);
		
		mrg.writeMasterBatPerFolder(cultivars);
	}

}
