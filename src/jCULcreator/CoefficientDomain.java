package jCULcreator;

public class CoefficientDomain {

	public CoefficientDomain(double minValue, double maxValue, double period) {
		super();
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.period = period;
	}

	private double minValue;
	private double maxValue;
	private double period;

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

}
