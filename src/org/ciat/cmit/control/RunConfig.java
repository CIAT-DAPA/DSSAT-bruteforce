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
