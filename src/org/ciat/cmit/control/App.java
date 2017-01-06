package org.ciat.cmit.control;

import java.io.File;

public class App {

	public static void main(String[] args) {


		ModelRunGenerator mgr;
		File config = new File("config.txt");
		RunConfig rc = new RunConfig();

		if (config.exists()) {
			mgr = rc.getModelRunGenerator(config);

		} else {
			mgr = rc.getModelRunGeneratorMaize();
		}
		mgr.work();
	}

}
