package org.ciat.cmit.control;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.ciat.cmit.model.CoefficientDomain;
import org.ciat.cmit.model.CropModel;
import org.ciat.cmit.model.CropModelRun;

public class App {

	public static void main(String[] args) {


		ModelRunGenerator mgr;
		File config = new File("config.txt");
		RunConfig rc = new RunConfig();

		if (config.exists()) {

			mgr = rc.getModelRunGenerator(config);
			mgr.work();

		} else {
			mgr = rc.getModelRunGeneratorMaize();
		}
		mgr.work();
	}

}
