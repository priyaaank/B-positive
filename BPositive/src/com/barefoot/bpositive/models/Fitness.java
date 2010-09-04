package com.barefoot.bpositive.models;

public class Fitness {

	private String bloodPressureUnit = "mmHg";
	private String weightUnit = "KGs";
	private String bloodPressure = "-1";
	private Donor donor;
	private int weight = -1;
	private long donorId;
	private long id;

	public Fitness(long id, String bloodPressure, String bloodPressureUnit, int weight, String weightUnit, long donorId) {
		this.id = id;
		this.bloodPressure = bloodPressure;
		this.bloodPressureUnit = bloodPressureUnit;
		this.weight= weight;
		this.weightUnit= weightUnit;
		this.donorId = donorId;
	}

	public String getBloodPressure() {
		return this.bloodPressure;
	}

	public String getBloodPressureWithUnit() {
		return this.bloodPressure + " " + bloodPressureUnit;
	}

	public void setBloodPressure(String bloodPressure) {
		this.bloodPressure = bloodPressure;
	}
	
	public int getWeight() {
		return this.weight;
	}

	public String getWeightWithUnit() {
		return Integer.toString(weight) + " " + weightUnit;
	}

	public long getDonorId() {
		return donorId;
	}

	public void setDonorId(long donorId) {
		this.donorId = donorId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBloodPressureUnit() {
		return bloodPressureUnit;
	}

	public String getWeightUnit() {
		return weightUnit;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	public Donor getDonor() {
		return donor;
	}

	public void setDonor(Donor donor) {
		this.donor = donor;
	}

	@Override 
	public boolean equals(Object objectToCompare) {
		if(!(objectToCompare instanceof Fitness)) {
			return false;
		}
		Fitness object = (Fitness)objectToCompare;
		return this.bloodPressure.equals(object.getBloodPressure()) &&
			   this.bloodPressureUnit.equals(object.getBloodPressureUnit()) &&
			   this.weight == object.weight &&
			   this.weightUnit.equals(object.weightUnit) &&
			   this.donorId == object.donorId &&
			   this.id == object.id;
	}

	
	
}
