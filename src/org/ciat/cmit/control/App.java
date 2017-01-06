package org.ciat.cmit.control;

import java.io.File;

public class App {

	public static void main(String[] args) {


		ModelRunManager mgr;
		File config = new File("config.txt");
		RunConfig rc = new RunConfig();

		if (config.exists()) {
			mgr = rc.getModelRunManager(config);

		} else {
			mgr = rc.getModelRunManagerMaize();
		}
		mgr.work();
	}

}
