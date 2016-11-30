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

public class ModelRunGenerator {


	public ModelRunGenerator(CropModelRun run) {
		super();
		this.run = run;
	}

	CropModelRun run;
	
	public void work(){
		
	}
	


	/**
	 * This method creates different .bat files to make the process per each "run.getMaxFiles()" dummy cultivars
	 * 
	 * @param cultivars
	 */
	public void writeMasterBatPerFolder(ArrayList<CultivarRun> cultivars) {
		

		try {
			/*
			 * defining the files, in this case 5, the key will be as the names of the files that contains the
			 * executables
			 * TODO make it in a loop
			 */
			HashMap<String, File> masters = new HashMap<String, File>();
			masters.put(getCultivarDir(0), new File("master_" + getCultivarDir(0) + ".bat"));
			masters.put(getCultivarDir(run.getMaxFiles()*1), new File("master_" + getCultivarDir(run.getMaxFiles()*1) + ".bat"));
			masters.put(getCultivarDir(run.getMaxFiles()*2), new File("master_" + getCultivarDir(run.getMaxFiles()*2) + ".bat"));
			masters.put(getCultivarDir(run.getMaxFiles()*3), new File("master_" + getCultivarDir(run.getMaxFiles()*3) + ".bat"));
			masters.put(getCultivarDir(run.getMaxFiles()*4), new File("master_" + getCultivarDir(run.getMaxFiles()*4) + ".bat"));
			masters.put(getCultivarDir(run.getMaxFiles()*5), new File("master_" + getCultivarDir(run.getMaxFiles()*5) + ".bat"));
			masters.put(getCultivarDir(run.getMaxFiles()*6), new File("master_" + getCultivarDir(run.getMaxFiles()*6) + ".bat"));

			/* Creating the writers to each file */
			HashMap<String, PrintWriter> writers = new HashMap<String, PrintWriter>();
			for (String key : masters.keySet()) {
				writers.put(key, new PrintWriter(masters.get(key)));
			}

			/* writers.get(tempKey) to get the respect writer to put the call in */
			String tempKey = "";
			for (CultivarRun cultivar : cultivars) {
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

	public void writeMasterBat(ArrayList<CultivarRun> cultivars) {
		PrintWriter writer;
		try {
			File master = new File("master" + ".bat");
			writer = new PrintWriter(master);
			for (CultivarRun cultivar : cultivars) {
				writer.println("cd \"" + cultivar.getBat().getParent() + "\"");
				writer.println("call \"cmd /c " + cultivar.getBat().getName() + "\"");
				writer.println("pause");
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

	public void writeBats(ArrayList<CultivarRun> cultivars) {

		for (CultivarRun cultivar : cultivars) {
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

	public void writeBatch(ArrayList<CultivarRun> cultivars) {

		String temp = "";
		for (CultivarRun cultivar : cultivars) {
			PrintWriter writer;
			File dir = new File(getCultivarDir(cultivar.getIndex()) + "\\" + cultivar.getName());
			dir.mkdirs();
			File batch = new File(dir.getAbsolutePath() + "\\batch" + cultivar.getName() + ".v46");
			try {
				cultivar.setBatch(batch);
				writer = new PrintWriter(batch);
				writer.println("$BATCH(DRYBEAN)");
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

	public void writeFileX(ArrayList<CultivarRun> cultivars) {

		for (CultivarRun cultivar : cultivars) {

			File dir = new File(getCultivarDir(cultivar.getIndex()) + "\\" + cultivar.getName());
			dir.mkdirs();

			/* copying file A */
			File sourceA = new File("sample\\CCLA1302.BNA");
			File targetA = new File(dir.getAbsolutePath() + "\\" + cultivar.getName() + ".BNA");
			try {
				Files.copy(sourceA.toPath(), targetA.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e2) {
				e2.printStackTrace();
			}

			/* copying file T */
			File sourceT = new File("sample\\CCLA1302.BNT");
			File targetT = new File(dir.getAbsolutePath() + "\\" + cultivar.getName() + ".BNT");
			try {
				Files.copy(sourceT.toPath(), targetT.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e2) {
				e2.printStackTrace();
			}

			/* generating file X */
			File mergedFile = new File(dir.getAbsolutePath() + "\\" + cultivar.getName() + ".BNX");
			cultivar.setFileX(mergedFile);
			File head = new File("sample\\CCLA1303_head.BNX");
			File tail = new File("sample\\CCLA1303_tail.BNX");

			FileWriter fstream = null;
			PrintWriter out = null;
			try {
				fstream = new FileWriter(mergedFile, true);
				out = new PrintWriter(fstream);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			FileInputStream fisHead;
			try {
				fisHead = new FileInputStream(head);
				BufferedReader inHead = new BufferedReader(new InputStreamReader(fisHead));

				String aLineHead;
				while ((aLineHead = inHead.readLine()) != null) {
					out.println(aLineHead);

				}

				inHead.close();

				out.println(" 1 BN " + cultivar.getName() + " CALIMA");

				FileInputStream fisTail;

				fisTail = new FileInputStream(tail);
				BufferedReader inTail = new BufferedReader(new InputStreamReader(fisTail));

				String aLineTail;
				while ((aLineTail = inTail.readLine()) != null) {
					out.println(aLineTail);
				}

				inTail.close();
				out.flush();
				out.close();

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	public ArrayList<CultivarRun> writeCultivars(ArrayList<String> combinations) {
		ArrayList<CultivarRun> cultivars = new ArrayList<>();
		int i = 0;
		PrintWriter writer;
		DecimalFormat nf = new DecimalFormat("000000");
		try {
			writer = new PrintWriter("BNGRO046.CUL");
			writer.println("*DRYBEAN GENOTYPE COEFFICIENTS: CRGRO046 MODEL");
			writer.println("@VAR#  VRNAME.......... EXPNO   ECO#  CSDL PPSEN EM-FL FL-SH FL-SD SD-PM FL-LF LFMAX SLAVR SIZLF  XFRT WTPSD SFDUR SDPDV PODUR THRSH SDPRO SDLIP");
			writer.println("!                                        1     2     3     4     5     6     7     8     9    10    11    12    13    14    15    16    17    18");
			for (String combination : combinations) {
				cultivars.add(new CultivarRun(nf.format(i++) + "", i));
				writer.println(combination);
			}
			writer.flush();
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return cultivars;
	}

	public ArrayList<String> getCombinationsToPrint(ArrayList<String> combinations) {
		String temp;
		ArrayList<String> newCombinations = new ArrayList<String>();
		DecimalFormat nf = new DecimalFormat("000000");
		int i = 0;
		for (String combination : combinations) {
			temp = nf.format(i++) + " CALIMA               . ANDIND 12.17 0.050 " + combination + " 18.00  0.98  320. 133.0  1.00 0.600  15.0  3.50  10.0  78.0  .235  .030";
			newCombinations.add(temp);
		}
		return newCombinations;

	}




}
