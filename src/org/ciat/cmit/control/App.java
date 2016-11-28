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

import org.ciat.cmit.model.CoefficientDomain;
import org.ciat.cmit.model.CultivarRun;

public class App {
	public static String cultivarDir = "W";

	public static void main(String[] args) {
		ArrayList<String> combinations = new ArrayList<String>();

		/* palvarez test cases */
		ArrayList<CoefficientDomain> domains = new ArrayList<CoefficientDomain>();
		domains.add(new CoefficientDomain(17.0, 20.0, 0.15, new DecimalFormat("#0.00")));
		// domains.add(new CoefficientDomain(2.5, 7.0, 0.3, new DecimalFormat("##0.0")));
		// domains.add(new CoefficientDomain(9.0, 19.0, 0.3, new DecimalFormat("##0.0")));
		// domains.add(new CoefficientDomain(11.0, 25.0, 0.3, new DecimalFormat("#0.00")));

		for (CoefficientDomain domain : domains) {
			combinations = getCombinations(domain, combinations);
		}

		combinations = addPrefixAndSufix(combinations);

		ArrayList<CultivarRun> cultivars = writeCultivars(combinations);

		writeFileX(cultivars);
		
		writeBatch(cultivars);

		writeBats(cultivars);

		writeMasterBat(cultivars);
	}

	private static void writeMasterBat(ArrayList<CultivarRun> cultivars) {
		PrintWriter writer;
		try {
			File master = new File("master" + ".bat");
			writer = new PrintWriter(master);
			for (CultivarRun cultivar : cultivars) {
				writer.println("cd \"" + cultivar.getBat().getParent() + "\"");
				writer.println(cultivar.getBat().getName());
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

	private static void writeBats(ArrayList<CultivarRun> cultivars) {

		for (CultivarRun cultivar : cultivars) {
			PrintWriter writer;
			try {
				File batDir = new File(cultivarDir + "\\" + cultivar.getName());
				batDir.mkdirs();
				File bat = new File(batDir.getAbsolutePath() + "\\" + cultivar.getName() + ".bat");
				cultivar.setBat(bat);
				writer = new PrintWriter(bat);
				writer.println("C:\\DSSAT46\\dscsm046 CRGRO046 B " + cultivar.getBatch().getAbsolutePath());
				writer.println("@echo off");
				writer.println("exit");
				writer.flush();
				writer.close();

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private static void writeBatch(ArrayList<CultivarRun> cultivars) {

		String temp = "";
		for (CultivarRun cultivar : cultivars) {
			PrintWriter writer;
			try {
				File dir = new File(cultivarDir + "\\" + cultivar.getName());
				dir.mkdirs();
				File batch = new File(dir.getAbsolutePath() + "\\batch" + cultivar.getName() + ".v46");
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

	private static void writeFileX(ArrayList<CultivarRun> cultivars) {

		for (CultivarRun cultivar : cultivars) {

			File dir = new File(cultivarDir + "\\" + cultivar.getName());
			dir.mkdirs();

			File sourceA = new File("sample\\CCLA1302.BNA");
			File targetA = new File(dir.getAbsolutePath() + "\\" + cultivar.getName() + ".BNA");
			try {
				Files.copy(sourceA.toPath(), targetA.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e2) {
				e2.printStackTrace();
			}

			File sourceT = new File("sample\\CCLA1302.BNT");
			File targetT = new File(dir.getAbsolutePath() + "\\" + cultivar.getName() + ".BNT");
			try {
				Files.copy(sourceT.toPath(), targetT.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e2) {
				e2.printStackTrace();
			}

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
				out.close();

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	public static ArrayList<CultivarRun> writeCultivars(ArrayList<String> combinations) {
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
				cultivars.add(new CultivarRun(nf.format(i++) + ""));
				writer.println(combination);
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return cultivars;
	}

	@Deprecated
	private static ArrayList<String> addPrefixAndSufix(ArrayList<String> combinations) {
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

	public static ArrayList<String> getCombinations(CoefficientDomain c, ArrayList<String> combinations) {
		String temp;
		ArrayList<String> newCombinations = new ArrayList<String>();

		if (combinations.size() == 0) {
			for (double j = c.getMinValue(); j <= c.getMaxValue(); j += c.getPeriod()) { // for the first coefficient
				temp = String.format(c.getStringFormat(), c.getNumberFormat().format(j) + "");
				newCombinations.add(temp);
			}
		} else {
			for (String combination : combinations) {
				for (double j = c.getMinValue(); j <= c.getMaxValue(); j += c.getPeriod()) {
					temp = combination + " " + String.format(c.getStringFormat(), c.getNumberFormat().format(j) + "");
					newCombinations.add(temp);

				}
			}

		}
		return newCombinations;
	}

	public static void printCombinations(ArrayList<String> combinations) {

		for (String combination : combinations) {
			System.out.println(combination);
		}
	}

}
