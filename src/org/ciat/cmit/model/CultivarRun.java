package org.ciat.cmit.model;

import java.io.File;

public class CultivarRun {

	private String name;
	private File fileX;
	private File batch;
	private File bat;
	private int index;	

	public CultivarRun(String name, int index) {
		super();
		this.name = name;
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public File getFileX() {
		return fileX;
	}

	public void setFileX(File fileX) {
		this.fileX = fileX;
	}

	public File getBatch() {
		return batch;
	}

	public void setBatch(File batch) {
		this.batch = batch;
	}

	public File getBat() {
		return bat;
	}

	public void setBat(File bat) {
		this.bat = bat;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}


}
