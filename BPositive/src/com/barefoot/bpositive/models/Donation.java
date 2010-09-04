package com.barefoot.bpositive.models;

public class Donation {

	private long id;
	private String place;
	private String date;
	private String organiser;
	private String type;
	private Fitness fitness;
	private Donor donor;
	private long fitnessId;
	private long donorId;

	public Donation(long id, String date, String place, String organiser, String type, long fitnessId, long donorId) {
		this.id = id;
		this.place = place;
		this.date = date;
		this.organiser = organiser;
		this.type = type;
		this.fitnessId = fitnessId;
		this.donorId = donorId;
	}

	public long getId() {
		return this.id;
	}
	
	public String getDate() {
		return this.date;
	}

	public String getPlace() {
		return this.place;
	}

	public String getOrganiser() {
		return this.organiser;
	}

	public String getType() {
		return this.type;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public void setDate(String date) {
		this.date = date;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setOrganiser(String organiser) {
		this.organiser = organiser;
	}

	public Fitness getFitness() {
		return this.fitness;
	}

	public void setFitness(Fitness fitness) {
		this.fitness = fitness;
	}

	public Donor getDonor() {
		return donor;
	}

	public void setDonor(Donor donor) {
		this.donor = donor;
	}

	public long getFitnessId() {
		return fitnessId;
	}

	public void setFitnessId(long fitnessId) {
		this.fitnessId = fitnessId;
	}

	public long getDonorId() {
		return donorId;
	}

	public void setDonorId(long donorId) {
		this.donorId = donorId;
	}
	
	@Override
	public boolean equals(Object objectToCompare) {
		if(!(objectToCompare instanceof Donation))
			return false;
		
		Donation object = (Donation) objectToCompare;
		return this.getDate().equals(object.getDate()) &&
    		   this.getDonorId() == object.getDonorId() &&
			   this.getFitnessId() == object.getFitnessId() &&
			   this.getId() == object.getId() &&
			   this.getOrganiser().equals(object.getOrganiser()) &&
			   this.getPlace().equals(object.getPlace()) &&
			   this.getType().equals(object.getType());
	}
}
