package com.barefoot.bpositive.models;

public class Fitness {

	private static final String BLOOD_PRESSURE_UNIT = "mmHg";
	private static final String WEIGHT_UNIT = "KGs";
	private String bloodPressure = "-1";
	private int weight = -1;

	public String getBloodPressure() {
		return this.bloodPressure;
	}

	public String getBloodPressureWithUnit() {
		return this.bloodPressure + " " + BLOOD_PRESSURE_UNIT;
	}

	public void setBloodPressure(String bloodPressure) {
		this.bloodPressure = bloodPressure;
	}
	
	public int getWeight() {
		return this.weight;
	}

	public String getWeightWithUnit() {
		return Integer.toString(weight) + " " + WEIGHT_UNIT;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

}
