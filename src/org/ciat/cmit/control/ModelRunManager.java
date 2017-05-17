package org.ciat.cmit.control;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.HashMap;

import org.ciat.cmit.model.CropModelRun;
import org.ciat.cmit.model.CultivarRun;
import org.ciat.cmit.model.ProgressBar;

public class ModelRunManager {

	private CropModelRun run;

	public ModelRunManager(CropModelRun run) {
		super();
		this.run = run;
	}

	public void work() {
		writeCultivars();

		writeFileX();

		writeBatchs();

		writeBats();

		writeMasterBat();

		writeMasterBatPerFolder();

	}

	/**
	 * This method creates different .bat files to make the process per each "run.getMaxFiles()" candidate
	 * 
	 * @param cultivars
	 */
	private void writeMasterBatPerFolder() {

		try {
			/*
			 * defining the main output files, the key will be as the names of the folders that contains the files
			 */
			HashMap<String, File> masters = new HashMap<String, File>();
			for (int i = 0; i * run.getMaxFiles() < run.getCultivars().size(); i++) {
				masters.put(getCultivarDir(run.getMaxFiles() * i), new File("master_" + getCultivarDir(run.getMaxFiles() * i) + ".bat"));
			}

			/* Creating the writers to each file */
			HashMap<String, PrintWriter> writers = new HashMap<String, PrintWriter>();
			for (String key : masters.keySet()) {
				writers.put(key, new PrintWriter(masters.get(key)));
			}

			/* writers.get(tempKey) to get the respect writer to put the call in */
			String tempKey = "";
			for (CultivarRun cultivar : run.getCultivars()) {
				tempKey = getCultivarDir(cultivar.getIndex());
				writers.get(tempKey).println("cd \"" + cultivar.getBat().getParent() + "\"");
				writers.get(tempKey).println("call \"cmd /c " + cultivar.getBat().getName() + "\"");
				writers.get(tempKey).flush();

			}

			/* writing final instructions and closing the writers */
			for (String key : writers.keySet()) {
				writers.get(key).println("@echo off");
				writers.get(key).println("pause");
				writers.get(key).println("exit");
				writers.get(key).flush();
				writers.get(key).close();
			}

		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	private void writeMasterBat() {

		File master = new File("master" + ".bat");
		try (PrintWriter writer = new PrintWriter(master);) {

			for (CultivarRun cultivar : run.getCultivars()) {
				writer.println("cd \"" + cultivar.getBat().getParent() + "\"");
				writer.println("call \"cmd /c " + cultivar.getBat().getName() + "\"");
			}
			writer.println("@echo off");
			writer.println("pause");
			writer.println("exit");

		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	private void writeBats() {
		System.out.println("Writing .bat files");
		ProgressBar bar = new ProgressBar();
		int subFolderIndex = 0;
		int subFoderTotal = run.getCultivars().size();
		bar.update(0, subFoderTotal);

		for (CultivarRun cultivar : run.getCultivars()) {
			;
			File batDir = new File(getCultivarDir(cultivar.getIndex()) + "\\" + cultivar.getName());
			batDir.mkdirs();
			File bat = new File(batDir.getAbsolutePath() + "\\" + cultivar.getName() + ".bat");
			cultivar.setBat(bat);
			try (PrintWriter writer = new PrintWriter(bat)) {

				writer.println("C:\\DSSAT46\\dscsm046 " + run.getModel().getName() + " B " + cultivar.getBatch().getAbsolutePath());
				writer.println("rename PlantGro.OUT summary.txt");
				writer.println("rename Overview.OUT overview.txt");
				writer.println("del *.OUT");
				writer.println("rename summary.txt PlantGro.OUT");
				writer.println("rename overview.txt Overview.OUT");
				writer.println("@echo off");
				writer.println("exit");

			} catch (IOException e1) {
				e1.printStackTrace();
			}

			// progress bar update
			subFolderIndex++;
			if (subFolderIndex % 100 == 0) {
				bar.update(subFolderIndex, subFoderTotal);
			}
		}
		bar.update(subFoderTotal - 1, subFoderTotal);
	}

	public String getCultivarDir(int index) {
		return index / run.getMaxFiles() + "";
	}

	private void writeBatchs() {
		System.out.println("Writing batch .v46 files");
		ProgressBar bar = new ProgressBar();
		int subFolderIndex = 0;
		int subFoderTotal = run.getCultivars().size();
		bar.update(0, subFoderTotal);

		String temp = "";
		for (CultivarRun cultivar : run.getCultivars()) {

			File dir = new File(getCultivarDir(cultivar.getIndex()) + "\\" + cultivar.getName());
			dir.mkdirs();
			File batch = new File(dir.getAbsolutePath() + "\\batch" + cultivar.getName() + ".v46");
			try (PrintWriter writer = new PrintWriter(batch);) {
				cultivar.setBatch(batch);

				writer.println("$BATCH(CROP)");
				writer.println("@FILEX                                                                                        TRTNO     RP     SQ     OP     CO");
				for (Integer id : run.getCultivarTreatments()) {
					temp = String.format("%1$-94s %2$4s %3$6s %4$6s %5$6s %6$6s", cultivar.getFileX().getAbsolutePath(), id, 1, 0, 0, 0);
					writer.println(temp);
				}

			} catch (IOException e1) {
				e1.printStackTrace();
			}

			// progress bar update
			subFolderIndex++;
			if (subFolderIndex % 100 == 0) {
				bar.update(subFolderIndex, subFoderTotal);
			}
		}
		bar.update(subFoderTotal - 1, subFoderTotal);
	}

	private void writeFileX() {
		System.out.println("Writing X files");
		ProgressBar bar = new ProgressBar();
		int subFolderIndex = 0;
		int subFoderTotal = run.getCultivars().size();
		bar.update(0, subFoderTotal);

		for (CultivarRun cultivar : run.getCultivars()) {

			File dir = new File(getCultivarDir(cultivar.getIndex()) + "\\" + cultivar.getName());
			dir.mkdirs();


			/* generating file X */
			File mergedFile = new File(dir.getAbsolutePath() + "\\" + cultivar.getName() + ".BNX");
			cultivar.setFileX(mergedFile);

			try (BufferedReader inHead = new BufferedReader(new InputStreamReader(new FileInputStream(run.getModel().getFileXHead()))); BufferedReader inTail = new BufferedReader(new InputStreamReader(new FileInputStream(run.getModel().getFileXTail()))); PrintWriter writer = new PrintWriter(new FileWriter(mergedFile, true));) {

				/* Adding FileX head */
				String aLineHead;
				while ((aLineHead = inHead.readLine()) != null) {
					writer.println(aLineHead);

				}

				/* Inserting line that calls the specific cultivar */
				writer.println(" 1 " + run.getModel().getShortName() + " " + cultivar.getName() + " DUMMY");

				/* Adding FileX tail */
				String aLineTail;
				while ((aLineTail = inTail.readLine()) != null) {
					writer.println(aLineTail);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

			// progress bar update
			subFolderIndex++;
			if (subFolderIndex % 100 == 0) {
				bar.update(subFolderIndex, subFoderTotal);
			}
		}
		bar.update(subFoderTotal - 1, subFoderTotal);
	}

	private void writeCultivars() {
		System.out.println("Writing .CUL file");
		/*
		 * ProgressBar bar = new ProgressBar(); int subFolderIndex = 0; int subFoderTotal = run.getCultivars().size();
		 * bar.update(0, subFoderTotal);
		 */

		DecimalFormat nf = new DecimalFormat("000000");
		try (BufferedReader inHead = new BufferedReader(new InputStreamReader(new FileInputStream(run.getModel().getFileCULHead()))); BufferedWriter CULWriter = new BufferedWriter(new PrintWriter(App.prop.getProperty("crop.name")+".CUL"))) {

			String aLineHead;
			while ((aLineHead = inHead.readLine()) != null) {
				CULWriter.write(aLineHead);
				CULWriter.newLine();
			}

			String temp;
			int i = 0;
			for (String combination : run.obtainCombinations()) {
				run.getCultivars().add(new CultivarRun(nf.format(i++) + "", i));// adding candiate to the count

				// write in .CUL
				temp = nf.format(i) + " " + run.getVrName() + " " + run.getEco() + " " + combination + "";
				CULWriter.write(temp);
				CULWriter.newLine();
				

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("100%");
	}

}
