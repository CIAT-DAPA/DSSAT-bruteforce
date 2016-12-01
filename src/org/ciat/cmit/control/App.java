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

		int run = 2;
		
		if (run == 1) {

			domains.add(new CoefficientDomain(12.17, 12.17, 100000, new DecimalFormat("#0.00"), "%1$5s"));
			domains.add(new CoefficientDomain(0.050, 0.050, 100000, new DecimalFormat("0.000"), "%1$5s"));
			domains.add(new CoefficientDomain(17.0, 20.0, 0.15, new DecimalFormat("#0.00"), "%1$5s"));
			domains.add(new CoefficientDomain(2.5, 7.0, 0.3, new DecimalFormat("##0.0"), "%1$5s"));
			domains.add(new CoefficientDomain(9.0, 19.0, 0.3, new DecimalFormat("##0.0"), "%1$5s"));
			domains.add(new CoefficientDomain(11.0, 25.0, 0.3, new DecimalFormat("#0.00"), "%1$5s"));
			domains.add(new CoefficientDomain(18.00, 18.00, 100000, new DecimalFormat("#0.00"), "%1$5s"));
			domains.add(new CoefficientDomain(0.98, 0.98, 100000, new DecimalFormat("#0.00"), "%1$5s"));
			domains.add(new CoefficientDomain(320., 320., 100000, new DecimalFormat("##0.#"), "%1$5s"));
			domains.add(new CoefficientDomain(133.0, 133.0, 100000, new DecimalFormat("##0.0"), "%1$5s"));
			domains.add(new CoefficientDomain(1.00, 1.00, 100000, new DecimalFormat("#0.00"), "%1$5s"));
			domains.add(new CoefficientDomain(0.600, 0.600, 100000, new DecimalFormat("0.000"), "%1$5s"));
			domains.add(new CoefficientDomain(15.0, 15.0, 100000, new DecimalFormat("##0.0"), "%1$5s"));
			domains.add(new CoefficientDomain(3.50, 3.50, 100000, new DecimalFormat("#0.00"), "%1$5s"));
			domains.add(new CoefficientDomain(10.0, 10.0, 100000, new DecimalFormat("##0.0"), "%1$5s"));
			domains.add(new CoefficientDomain(78.0, 78.0, 100000, new DecimalFormat("##0.0"), "%1$5s"));
			domains.add(new CoefficientDomain(.235, .235, 100000, new DecimalFormat("#.000"), "%1$5s"));
			domains.add(new CoefficientDomain(.030, .030, 100000, new DecimalFormat("#.000"), "%1$5s"));

			CropModel beanModel = new CropModel("CRGRO046", "BN", new File("sample\\CCLA1302.BNA"), new File("sample\\CCLA1302.BNT"), new File("sample\\CCLA1302_head.BNX"), new File("sample\\CCLA1302_tail.BNX"), new File("sample\\BNGRO046_head.CUL"));

			ModelRunGenerator mrg = new ModelRunGenerator(new CropModelRun(domains, beanModel, 100000, "CALIMA               .", "ANDIND"));

			mrg.work();
		}

		if (run == 2) {

			domains.add(new CoefficientDomain(220, 320, 4, new DecimalFormat("##0.0"), "%1$5s"));
			domains.add(new CoefficientDomain(0.500, 0.500, 100000, new DecimalFormat("0.000"), "%1$5s"));
			domains.add(new CoefficientDomain(850, 1050, 4, new DecimalFormat("###0"), "%1$5s"));
			domains.add(new CoefficientDomain(820, 820, 100000, new DecimalFormat("##0.0"), "%1$5s"));
			domains.add(new CoefficientDomain(8.40, 8.40, 100000, new DecimalFormat("#0.00"), "%1$5s"));
			domains.add(new CoefficientDomain(40, 60, 0.5, new DecimalFormat("#0.00"), "%1$5s"));

			CropModel beanModel = new CropModel("CALB1501MZ", "MZ", new File("sample\\CALB1502.MZA"), new File("sample\\CALB1502.MZT"), new File("sample\\CALB1502_head.MZX"), new File("sample\\CALB1502_tail.MZX"), new File("sample\\MZCER046_head.CUL"));

			ModelRunGenerator mrg = new ModelRunGenerator(new CropModelRun(domains, beanModel, 100000, "PIO 30F35HRB_        .", "IB0001"));

			mrg.work();

		}

	}

}
