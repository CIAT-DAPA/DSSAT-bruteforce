package org.ciat.cmit.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.ciat.cmit.model.CropModelRun;
import org.ciat.cmit.model.CultivarRun;

public class ModelRunManager {

	CropModelRun run;

	public ModelRunManager(CropModelRun run) {
		super();
		this.run = run;
	}

	public void work() {
		run.getCombinations();

		writeCultivars();

		writeFileX();

		writeBatch();

		writeBats();

		writeMasterBat();

		writeMasterBatPerFolder();

	}

	/**
	 * This method creates different .bat files to make the process per each "run.getMaxFiles()" dummy cultivars
	 * 
	 * @param cultivars
	 */
	public void writeMasterBatPerFolder() {

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

	public void writeMasterBat() {
		PrintWriter writer;
		try {
			File master = new File("master" + ".bat");
			writer = new PrintWriter(master);
			for (CultivarRun cultivar : run.getCultivars()) {
				writer.println("cd \"" + cultivar.getBat().getParent() + "\"");
				writer.println("call \"cmd /c " + cultivar.getBat().getName() + "\"");
			}
			writer.println("@echo off");
			writer.println("pause");
			writer.println("exit");
			writer.flush();
			writer.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public void writeBats() {

		for (CultivarRun cultivar : run.getCultivars()) {
			PrintWriter writer;
			File batDir = new File(getCultivarDir(cultivar.getIndex()) + "\\" + cultivar.getName());
			batDir.mkdirs();
			File bat = new File(batDir.getAbsolutePath() + "\\" + cultivar.getName() + ".bat");
			cultivar.setBat(bat);
			try {
				writer = new PrintWriter(bat);
				writer.println("C:\\DSSAT46\\dscsm046 " + run.getModel().getName() + " B " + cultivar.getBatch().getAbsolutePath());
				writer.println("@echo off");
				writer.println("exit");
				writer.flush();
				writer.close();

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public String getCultivarDir(int index) {
		return index / run.getMaxFiles() + "";
	}

	public void writeBatch() {

		String temp = "";
		for (CultivarRun cultivar : run.getCultivars()) {
			PrintWriter writer;
			File dir = new File(getCultivarDir(cultivar.getIndex()) + "\\" + cultivar.getName());
			dir.mkdirs();
			File batch = new File(dir.getAbsolutePath() + "\\batch" + cultivar.getName() + ".v46");
			try {
				cultivar.setBatch(batch);
				writer = new PrintWriter(batch);
				writer.println("$BATCH(CROP)");
				writer.println("@FILEX                                                                                        TRTNO     RP     SQ     OP     CO");
				temp = String.format("%1$-94s %2$4s %3$6s %4$6s %5$6s %6$6s", cultivar.getFileX().getAbsolutePath(), 1, 1, 0, 0, 0);
				writer.println(temp);
				writer.flush();
				writer.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}

	public void writeFileX() {

		for (CultivarRun cultivar : run.getCultivars()) {

			File dir = new File(getCultivarDir(cultivar.getIndex()) + "\\" + cultivar.getName());
			dir.mkdirs();

			/* copying file A */
			File targetA = new File(dir.getAbsolutePath() + "\\" + cultivar.getName() + ".BNA");
			try {
				Files.copy(run.getModel().getFileA().toPath(), targetA.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e2) {
				e2.printStackTrace();
			}

			/* copying file T */
			File targetT = new File(dir.getAbsolutePath() + "\\" + cultivar.getName() + ".BNT");
			try {
				Files.copy(run.getModel().getFileT().toPath(), targetT.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e2) {
				e2.printStackTrace();
			}

			/* generating file X */
			File mergedFile = new File(dir.getAbsolutePath() + "\\" + cultivar.getName() + ".BNX");
			cultivar.setFileX(mergedFile);

			FileWriter fstream = null;
			PrintWriter writer = null;
			try {
				fstream = new FileWriter(mergedFile, true);
				writer = new PrintWriter(fstream);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			try {
				/* Adding FileX head */
				FileInputStream headReader;
				headReader = new FileInputStream(run.getModel().getFileXHead());
				BufferedReader inHead = new BufferedReader(new InputStreamReader(headReader));

				String aLineHead;
				while ((aLineHead = inHead.readLine()) != null) {
					writer.println(aLineHead);

				}
				inHead.close();

				/* Inserting line that calls the specific cultivar */
				writer.println(" 1 " + run.getModel().getShortName() + " " + cultivar.getName() + " DUMMY");

				/* Adding FileX tail */
				FileInputStream tailReader;
				tailReader = new FileInputStream(run.getModel().getFileXTail());
				BufferedReader inTail = new BufferedReader(new InputStreamReader(tailReader));

				String aLineTail;
				while ((aLineTail = inTail.readLine()) != null) {
					writer.println(aLineTail);
				}

				inTail.close();
				writer.flush();
				writer.close();

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	public void writeCultivars() {
		int i = 0;
		PrintWriter writer;
		DecimalFormat nf = new DecimalFormat("000000");
		try {
			writer = new PrintWriter("cultivars.CUL");

			FileInputStream headReader;
			headReader = new FileInputStream(run.getModel().getFileCULHead());
			BufferedReader inHead = new BufferedReader(new InputStreamReader(headReader));

			String aLineHead;
			while ((aLineHead = inHead.readLine()) != null) {
				writer.println(aLineHead);

			}
			inHead.close();

			for (String combination : getCombinationsToPrint()) {
				run.getCultivars().add(new CultivarRun(nf.format(i++) + "", i));
				writer.println(combination);
			}
			writer.flush();
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public ArrayList<String> getCombinationsToPrint() {
		String temp;
		ArrayList<String> newCombinations = new ArrayList<String>();
		DecimalFormat nf = new DecimalFormat("000000");
		int i = 0;
		for (String combination : run.getCombinations()) {
			temp = nf.format(i++) + " " + run.getVrName() + " " + run.getEco() + " " + combination + "";
			newCombinations.add(temp);
		}
		return newCombinations;

	}

}
