package org.ciat.cmit.control;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.ciat.cmit.model.CoefficientDomain;
import org.ciat.cmit.model.CropModel;
import org.ciat.cmit.model.CropModelRun;

public class App {

	public static void main(String[] args) {

		/* palvarez test cases */
		// TODO read this from some input
		ArrayList<CoefficientDomain> domains = new ArrayList<CoefficientDomain>();
		domains.add(new CoefficientDomain(17.0, 20.0, 0.15, new DecimalFormat("#0.00")));
		domains.add(new CoefficientDomain(2.5, 7.0, 0.3, new DecimalFormat("##0.0")));
		domains.add(new CoefficientDomain(9.0, 19.0, 0.3, new DecimalFormat("##0.0")));
		domains.add(new CoefficientDomain(11.0, 25.0, 0.3, new DecimalFormat("#0.00")));

		CropModel beanModel = new CropModel("CRGRO046", new File("sample\\CCLA1302.BNA"), new File("sample\\CCLA1302.BNT"), new File("sample\\CCLA1303_head.BNX"), new File("sample\\CCLA1303_tail.BNX"), new File("BNGRO046_head.CUL"));

		ModelRunGenerator mrg = new ModelRunGenerator(new CropModelRun(domains, beanModel, 100000));

		mrg.work();
	}

}
