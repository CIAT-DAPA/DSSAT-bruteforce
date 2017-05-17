package org.ciat.cmit.control;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.ciat.cmit.model.CoefficientDomain;
import org.ciat.cmit.model.CropModel;
import org.ciat.cmit.model.CropModelRun;

public class RunConfig {

	public ModelRunManager getModelRunManager(File config) {
		List<CoefficientDomain> domains = new ArrayList<CoefficientDomain>();

			

			String modelName = App.prop.getProperty("model.name");
			String modelShortName = App.prop.getProperty("crop.shortname");
			String fileA = App.prop.getProperty("fileA.location");
			String fileT = App.prop.getProperty("fileT.location");
			String fileXHead = App.prop.getProperty("fileX.head.location");
			String fileXTail = App.prop.getProperty("fileX.tail.location");
			String culHead = App.prop.getProperty("fileCUL.head.location");
			String maxFiles = App.prop.getProperty("subfolders.numer");
			String vrname = App.prop.getProperty("CUL.varname");
			String eco = App.prop.getProperty("CUL.ECO");
			int n=Integer.parseInt(App.prop.getProperty("variable.number"));
			String domi = "";
			for(int i=0;i<n;) {
				domi = App.prop.getProperty("variable"+ ++i);
				String param[] = domi.split(" ");
				domains.add(new CoefficientDomain(Double.parseDouble(param[0]), Double.parseDouble(param[1]), Double.parseDouble(param[2]), new DecimalFormat(param[3]), "%1$" + param[4] + "s"));
			}
			CropModel model = new CropModel(modelName, modelShortName, new File(fileA), new File(fileT), new File(fileXHead), new File(fileXTail), new File(culHead));
			CropModelRun mr = new CropModelRun(domains, model, Integer.parseInt(maxFiles), vrname, eco, getTreatments(new File(fileXHead)));
			ModelRunManager mrg = new ModelRunManager(mr);



			return mrg;

		

	}

	protected ModelRunManager getModelRunManagerBean() {
		ArrayList<CoefficientDomain> domains = new ArrayList<CoefficientDomain>();
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

		CropModel model = new CropModel("CRGRO046", "BN", new File("sample\\CCLA1302.BNA"), new File("sample\\CCLA1302.BNT"), new File("sample\\CCLA1302_head.BNX"), new File("sample\\CCLA1302_tail.BNX"), new File("sample\\BNGRO046_head.CUL"));

		ModelRunManager mrg = new ModelRunManager(new CropModelRun(domains, model, 100000, "CALIMA               .", "ANDIND",getTreatments(new File("sample\\CCLA1302_head.BNX"))));

		return mrg;
	}

	protected ModelRunManager getModelRunManagerMaize() {
		ArrayList<CoefficientDomain> domains = new ArrayList<CoefficientDomain>();
		domains.add(new CoefficientDomain(220, 320, 4, new DecimalFormat("##0.0"), "%1$5s"));
		domains.add(new CoefficientDomain(0.500, 0.500, 100000, new DecimalFormat("0.000"), "%1$5s"));
		domains.add(new CoefficientDomain(850, 1050, 4, new DecimalFormat("###0"), "%1$5s"));
		domains.add(new CoefficientDomain(820, 820, 100000, new DecimalFormat("##0.0"), "%1$5s"));
		domains.add(new CoefficientDomain(8.40, 8.40, 100000, new DecimalFormat("#0.00"), "%1$5s"));
		domains.add(new CoefficientDomain(40, 60, 0.5, new DecimalFormat("#0.00"), "%1$5s"));

		CropModel model = new CropModel("CALB1501MZ", "MZ", new File("sample\\CALB1502.MZA"), new File("sample\\CALB1502.MZT"), new File("sample\\CALB1502_head.MZX"), new File("sample\\CALB1502_tail.MZX"), new File("sample\\MZCER046_head.CUL"));

		ModelRunManager mrg = new ModelRunManager(new CropModelRun(domains, model, 100000, "PIO 30F35HRB_        .", "IB0001",getTreatments(new File("sample\\CALB1502_head.MZX"))));

		return mrg;
	}

	public enum readingStatus{LOOK,READ,STOP};
	
	private List<Integer> getTreatments(File fileXHead) {
		List<Integer> treatments = new ArrayList<Integer>();

		Scanner reader;
		String line="";
		readingStatus flag = readingStatus.LOOK;

		if (fileXHead.exists()) {

			try {
				reader = new Scanner(fileXHead);
				while (flag != readingStatus.STOP && reader.hasNextLine() ) {
					line = reader.nextLine();
					if (line.contains("*TREATMENTS")) {
						flag = readingStatus.READ;
					}
					if (line.contains("*CULTIVARS")) {
						flag = readingStatus.STOP;
					}
					if(flag==readingStatus.READ && !line.isEmpty() && !line.startsWith("@") && !line.startsWith("*")){
						//TODO validate the treatment is from the cultivar ID=1
						//TODO find a more elegant way to get the treatment ID 
						treatments.add(Integer.parseInt(line.substring(0, 2).trim())); 
					}
				}
				reader.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			
		}else{
			treatments.add(1);
		}
		return treatments;
	}
}
