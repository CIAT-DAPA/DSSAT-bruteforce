package org.ciat.cmit.model;

import java.io.File;

public class CropModel {



	public CropModel(String name, String shortName, File fileA, File fileT, File fileXHead, File fileXTail, File fileCULHead) {
		super();
		this.name = name;
		this.setShortName(shortName);
		this.fileA = fileA;
		this.fileT = fileT;
		this.fileXHead = fileXHead;
		this.fileXTail = fileXTail;
		this.fileCULHead = fileCULHead;
	}

	private String name;
	private String shortName;
	private File fileA;
	private File fileT;
	private File fileXHead;
	private File fileXTail;
	private File fileCULHead;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public File getFileA() {
		return fileA;
	}

	public void setFileA(File fileA) {
		this.fileA = fileA;
	}

	public File getFileT() {
		return fileT;
	}

	public void setFileT(File fileT) {
		this.fileT = fileT;
	}

	public File getFileXHead() {
		return fileXHead;
	}

	public void setFileXHead(File fileXHead) {
		this.fileXHead = fileXHead;
	}

	public File getFileXTail() {
		return fileXTail;
	}

	public void setFileXTail(File fileXTail) {
		this.fileXTail = fileXTail;
	}

	public File getFileCULHead() {
		return fileCULHead;
	}

	public void setFileCULHead(File fileCULHead) {
		this.fileCULHead = fileCULHead;
	}

	@Override
	public String toString() {
		return name;
	}




}
