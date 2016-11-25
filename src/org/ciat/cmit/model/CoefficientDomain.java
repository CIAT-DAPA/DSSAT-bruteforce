package org.ciat.cmit.model;

import java.text.DecimalFormat;

public class CoefficientDomain {

	private double minValue;
	private double maxValue;
	private double period;
	private DecimalFormat numberFormat;
	private String stringFormat;

	public CoefficientDomain(double minValue, double maxValue, double period) {
		super();
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.period = period;
		this.numberFormat = new DecimalFormat("#0.00"); // default number format
		this.stringFormat = "%1$5s"; 					// default string format in order to fix the length of the values
		
	}
	
	public CoefficientDomain(double minValue, double maxValue, double period, DecimalFormat numberFormat) {
		super();
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.period = period;
		this.numberFormat = numberFormat;
		this.stringFormat = "%1$5s"; 					// default string format in order to fix the length of the values
		
	}

	public CoefficientDomain(double minValue, double maxValue, double period, DecimalFormat numberFormat, String stringFormat) {
		super();
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.period = period;
		this.numberFormat = numberFormat;
		this.stringFormat = stringFormat;
	}

	public double getMinValue() {
		return minValue;
	}

	public void setMinValue(double minValue) {
		this.minValue = minValue;
	}

	public double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}

	public double getPeriod() {
		return period;
	}

	public void setPeriod(double period) {
		this.period = period;
	}

	public DecimalFormat getNumberFormat() {
		return numberFormat;
	}

	public void setNumberFormat(DecimalFormat format) {
		this.numberFormat = format;
	}

	public String getStringFormat() {
		return stringFormat;
	}

	public void setStringFormat(String stringFormat) {
		this.stringFormat = stringFormat;
	}

}
