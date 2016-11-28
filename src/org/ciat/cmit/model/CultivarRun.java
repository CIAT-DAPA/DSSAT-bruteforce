package org.ciat.cmit.model;

import java.io.File;

public class CultivarRun {
	
	public CultivarRun(String name) {
		super();
		this.name = name;
	}
	private String name;
	private File fileX;
	private File batch;
	private File bat;
	
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

}
