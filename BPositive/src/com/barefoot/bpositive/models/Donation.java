package com.barefoot.bpositive.models;

public class Donation {

	private long id;
	private String place;
	private String date;
	private String organiser;
	private String type;
	private Fitness fitness;

	public Donation(long id, String date, String place, String organiser, String type, Fitness fitness) {
		this.id = id;
		this.place = place;
		this.date = date;
		this.organiser = organiser;
		this.type = type;
		this.fitness = fitness;
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
}
